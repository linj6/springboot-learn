package com.lnjecit.generator.util;

import com.lnjecit.common.util.DateUtil;
import com.lnjecit.common.util.StringUtil;
import com.lnjecit.generator.entity.*;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成工具类
 *
 * @author lnj
 * @create 2018-05-13 11:35
 **/
public class GeneratorUtil {

    private static final String GENERATOR_PROPERTIES = "velocity/generator.properties";

    private static final String VELOCITY_TEMPLATE_DIR = "velocity/template/";
    private static final String ENTITY_TEMPLATE = "Entity.java.vm";
    private static final String MAPPER_TEMPLATE = "Mapper.xml.vm";
    private static final String DAO_TEMPLATE = "Dao.java.vm";
    private static final String SERVICE_TEMPLATE = "Service.java.vm";
    private static final String SERVICE_IMPL_TEMPLATE = "ServiceImpl.java.vm";
    private static final String CONTROLLER_TEMPLATE = "Controller.java.vm";

    /**
     * 获取velocity模板
     *
     * @return
     */
    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        //添加需要生成的模版
        templates.add(VELOCITY_TEMPLATE_DIR + ENTITY_TEMPLATE);
        templates.add(VELOCITY_TEMPLATE_DIR + MAPPER_TEMPLATE);
        templates.add(VELOCITY_TEMPLATE_DIR + DAO_TEMPLATE);
        templates.add(VELOCITY_TEMPLATE_DIR + SERVICE_TEMPLATE);
        templates.add(VELOCITY_TEMPLATE_DIR + SERVICE_IMPL_TEMPLATE);
        templates.add(VELOCITY_TEMPLATE_DIR + CONTROLLER_TEMPLATE);
        return templates;
    }

    public static void generatorCode(Table table, List<Column> columnList, GeneratorParam params, ZipOutputStream zip) {
        // 配置信息
        Properties generatorProperties = PropsUtil.loadProps(GENERATOR_PROPERTIES);

        // 表名转换成Java类名
        String className = tableNameToClassName(table.getTableName(), generatorProperties.getProperty("tablePrefix"));
        className = StringUtil.initCapitalize(className);

        ClazzInfo clazzInfo = new ClazzInfo();
        clazzInfo.setClassName(className);
        clazzInfo.setEntityName(StringUtil.firstCharToLower(className));

        List<FieldInfo> fieldInfoList = new ArrayList<>();
        // 列信息
        for (Column column : columnList) {
            FieldInfo fieldInfo = new FieldInfo();
            // 列名转换，java属性名及对应方法名
            String fieldName = StringUtil.transferToCamel(column.getColumnName(), false);
            fieldInfo.setFieldName(fieldName);
            fieldInfo.setFieldType(generatorProperties.getProperty(column.getDataType()));
            fieldInfo.setFieldComment(column.getColumnComment());
            fieldInfoList.add(fieldInfo);
        }
        table.setColumnList(columnList);

        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        // 封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", table.getTableName());
        map.put("className", clazzInfo.getClassName());
        map.put("entityName", clazzInfo.getEntityName());
        map.put("fieldList", fieldInfoList);
        map.put("package", generatorProperties.getProperty("package"));
        map.put("moduleName", params.getModuleName());
        map.put("hasBigDecimal", hasBigDecimal(columnList));
        map.put("hasDate", hasDate(columnList));
        map.put("authorName", generatorProperties.getProperty("authorName"));
        map.put("datetime", DateUtil.format(new Date(), DateUtil.DATE_TIME_PATTERN));
        map.put("resultMap", getResultMap(table.getTableName(), columnList));
        map.put("baseColumnList", getBaseColumnList(columnList));
        map.put("insertSql", getInsertSql(table.getTableName(), getBaseColumnList(columnList), columnList));
        map.put("updateSql", getUpdateSql(table.getTableName(), columnList));
        VelocityContext context = new VelocityContext(map);

        // 获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                // 添加到zip
                if (1 == params.getWithPackage().intValue()) {
                    zip.putNextEntry(new ZipEntry(getFileName(template, clazzInfo.getClassName(), params.getModuleName(), generatorProperties.getProperty("package"))));
                } else {
                    zip.putNextEntry(new ZipEntry(getFileName(template, clazzInfo.getClassName(), params.getModuleName(), null)));
                }
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }

    public static String tableNameToClassName(String tableName, String tablePrefix) {
        if (StringUtil.isNotBlank(tableName)) {
            return tableName.replace(tablePrefix, "");
        }
        throw new NullPointerException("tableName can not be null");
    }

    /**
     * 获取文件名
     *
     * @param template  模板路径
     * @param className 类名
     * @return
     */
    public static String getFileName(String template, String className, String packagePath) {
        String prefix = packagePath + className;
        if (template.contains(ENTITY_TEMPLATE)) {
            return prefix + ".java";
        }
        if (template.contains(MAPPER_TEMPLATE)) {
            return prefix + "Mapper.xml";
        }
        if (template.contains(DAO_TEMPLATE)) {
            return prefix + "Dao.java";
        }
        if (template.contains(SERVICE_TEMPLATE)) {
            return prefix + "Service.java";
        }
        if (template.contains(SERVICE_IMPL_TEMPLATE)) {
            return prefix + "ServiceImpl.java";
        }
        if (template.contains(CONTROLLER_TEMPLATE)) {
            return prefix + "Controller.java";
        }
        return null;
    }

    /**
     * 获取文件名
     *
     * @param template    模板路径
     * @param className   类名
     * @param moduleName  模块名
     * @param packageName 包名
     * @return
     */
    public static String getFileName(String template, String className, String moduleName, String packageName) {
        String packagePath = "java" + File.separator;
        if (StringUtil.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }
        return getFileName(template, className, packagePath);
    }

    /**
     * 数据库表中是否有decimal类型的列
     *
     * @param columnList
     * @return
     */
    private static boolean hasBigDecimal(List<Column> columnList) {
        for (Column column : columnList) {
            if ("decimal".equals(column.getDataType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 数据库表中是否有日期类型的列
     *
     * @param columnList
     * @return
     */
    private static boolean hasDate(List<Column> columnList) {
        String[] dateTypeArr = {"date", "datetime", "timestamp"};
        List<String> dateTypeList = Arrays.asList(dateTypeArr);
        for (Column column : columnList) {
            if (dateTypeList.contains(column.getDataType())) {
                return true;
            }
        }
        return false;
    }

    private static String getBaseColumnList(List<Column> columnList) {
        StringBuffer buffer = new StringBuffer();
        String columnName;
        for (Column column : columnList) {
            columnName = column.getColumnName();
            buffer.append(columnName + ",");
        }
        return buffer.toString().substring(0, buffer.length() - 1);
    }

    /**
     * Mapping文件生成resultMap
     *
     * @param tableName  表名
     * @param columnList 表的列集合
     * @return
     */
    private static String getResultMap(String tableName, List<Column> columnList) {
        String resultColumn = "\t\t<result column=\"";
        StringBuffer buffer = new StringBuffer();
        String columnName;
        for (Column column : columnList) {
            columnName = column.getColumnName();
            if (!"id".equals(columnName)) {
                buffer.append(resultColumn + columnName + "\" property=\"" + StringUtil.transferToCamel(columnName, false) + "\"/>\r");
            }
        }
        return buffer.toString().substring(0, buffer.length() - 1);
    }

    /**
     * Mapping文件生成update sq
     *
     * @param tableName  表名
     * @param columnList 表的列集合
     * @return
     */
    private static String getUpdateSql(String tableName, List<Column> columnList) {
        List<String> columnNames = new ArrayList<>();
        List<String> columnTypes = new ArrayList<>();
        for (Column column : columnList) {
            columnNames.add(column.getColumnName());
            columnTypes.add(column.getDataType());
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("UPDATE " + tableName + "\r\n");
        buffer.append("\t\t<set>\r\n");
        for (int i = 0; i < columnNames.size(); i++) {
            if ("datetime".equalsIgnoreCase(columnTypes.get(i))) {
                buffer.append("\t\t\t<if test=\"" + StringUtil.transferToCamel(columnNames.get(i), false) + " != null\">\r\n");
            } else {
                buffer.append("\t\t\t<if test=\"" + StringUtil.transferToCamel(columnNames.get(i), false) + " != null and " + StringUtil.transferToCamel(columnNames.get(i), false) + " != ''\">\r\n");
            }
            buffer.append("\t\t\t\t" + columnNames.get(i) + "=#{" + StringUtil.transferToCamel(columnNames.get(i), false) + "},\r\n");
            buffer.append("\t\t\t</if>\r\n");
        }
        buffer.append("\t\t</set>\r\n");
        buffer.append("\t\tWHERE id = #{id}\r");
        return buffer.toString().substring(0, buffer.length() - 1);
    }

    /**
     * 新增实体sql语句
     *
     * @param tableName      表名
     * @param baseColumnList
     * @return
     */
    private static String getInsertSql(String tableName, String baseColumnList, List<Column> columnList) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("INSERT INTO " + tableName + "(" + baseColumnList + ")\r\n");
        buffer.append("\t\tVALUES (" + getInsertValues(tableName, columnList) + ")\r");
        return buffer.toString().substring(0, buffer.length() - 1);
    }

    private static String getInsertValues(String tableName, List<Column> columnList) {
        StringBuffer buffer = new StringBuffer();
        String columnName;
        for (Column column : columnList) {
            columnName = column.getColumnName();
            buffer.append("#{" + StringUtil.transferToCamel(columnName, false) + "},");
        }
        return buffer.toString().substring(0, buffer.length() - 1);
    }

}
