package com.lnjecit.mail.springboot.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

/**
 * @author lnj
 * @create 2018-06-29 15:58
 **/
public class MailUtil {

    // 编码
    private final static String DAFAULT_CHARSET = "UTF-8";

    public static String getHtmlTemplateText(String templatePath) throws IOException {
        return getHtmlTemplateText(templatePath, DAFAULT_CHARSET);
    }

    /**
     * 获取邮箱模板文件 返回模板内容
     *
     * @return
     * @throws IOException
     */
    public static String getHtmlTemplateText(String templatePath, String charset) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + templatePath);
        String template;
        if (StringUtils.isNotBlank(charset)) {
            template = FileUtils.readFileToString(file, charset);
        } else {
            template = FileUtils.readFileToString(file, DAFAULT_CHARSET);
        }
        return template;
    }

    public static String getTemplateText(String templatePath, Map<String, Object> map) {
        return getTemplateText(templatePath, DAFAULT_CHARSET, map);
    }

    /**
     * 获取velocity邮件模板内容
     *
     * @param map 模板中需替换的参数内容
     * @return
     */
    public static String getTemplateText(String templatePath, String charset, Map<String, Object> map) {
        String templateText;
        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        VelocityContext context = new VelocityContext(map);
        // 渲染模板
        StringWriter sw = new StringWriter();
        Template template = Velocity.getTemplate(templatePath, charset);
        template.merge(context, sw);
        try {
            templateText = sw.toString();
            sw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("模板渲染失败");
        }
        return templateText;
    }
}
