package com.lnjecit.fileupload.util;

import com.lnjecit.fileupload.config.FileUploadProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * 文件上传工具类
 */
@Component
public class FileUploadUtil {

    protected static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);//日志对象
    private static FileUploadProperties fileUploadProperties;
    private static final String FILE_SEPERATOR = "/";
    private static final String COMMA = ".";
    private static final String RELATIVE_PATH = "relativePath";//图片保存的相对路径
    private static final String ACCESS_PATH = "accessPath";//图片访问路径，用于前台图片回显

    @Autowired
    public FileUploadUtil(FileUploadProperties fileUploadProperties) {
        FileUploadUtil.fileUploadProperties = fileUploadProperties;
    }

    /**
     * 文件上传
     *
     * @param mmf    要上传的文件集合
     * @param path   文件上传目录
     * @param prefix 文件名称前缀
     * @return
     */
    public static Result upload(Map<String, MultipartFile> mmf, String path, String prefix) {
        if (mmf == null || mmf.isEmpty()) {
            return Result.error("请上传文件");
        }
        List<Map<String, String>> list = new ArrayList<>();
        Set<String> keys = mmf.keySet();
        try {
            for (String key : keys) {
                MultipartFile file = mmf.get(key);
                Map<String, String> data = upload(file, path, prefix);
                list.add(data);
            }
            return Result.success(MapUtil.getMap("list", list));
        } catch (Exception e) {
            logger.error("upload", e);
            return Result.error(e.getMessage());
        }
    }

    public static Map<String, String> upload(MultipartFile file, String path, String prefix) {
        try {
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("请上传文件");
            }
            String fileName = DateUtil.DateToString(new Date(), DateUtil.TIMESTAMP_TO_STRING_PATTERN);
            // 文件扩展名
            String suffix = getFileExtention(file);
            String newFileName = prefix + fileName + COMMA + suffix;
            //文件保存路径
            String urlPath = path + FILE_SEPERATOR + DateUtil.DateToString(new Date(), DateUtil.DATE_TO_STRING_PATTERN) + FILE_SEPERATOR + newFileName;
            //根目录
            String fileUploadPath = getFileUploadPath();
            String fileDirectory = fileUploadPath + FILE_SEPERATOR + path + FILE_SEPERATOR + DateUtil.DateToString(new Date(), DateUtil.DATE_TO_STRING_PATTERN) + FILE_SEPERATOR;
            String filePath = fileUploadPath + FILE_SEPERATOR + urlPath;
            File f = new File(fileDirectory);
            if (!f.exists() && !f.isDirectory()) {
                f.mkdirs();
            }
            // 转存文件
            file.transferTo(new File(filePath));
            //返回图片保存的相对路径和图片回显路径
            Map<String, String> data = new HashMap<>();
            data.put(RELATIVE_PATH, urlPath);
            data.put(ACCESS_PATH, getFileAccessUrl() + urlPath);
            return data;
        } catch (Exception e) {
            logger.error("upload", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    public static List<Map<String, String>> upload(List<MultipartFile> fileList, String path, String prefix) {
        if (fileList == null || fileList.isEmpty()) {
            throw new RuntimeException("请上传文件");
        }
        List<Map<String, String>> list = new ArrayList<>();
        try {
            for (MultipartFile file : fileList) {
                Map<String, String> data = upload(file, path, prefix);
                list.add(data);
            }
            return list;
        } catch (Exception e) {
            logger.error("upload", e);
            throw new RuntimeException("上传文件失败");
        }
    }


    /****************从配置文件中都读取文件的访问路径************************************/
    public static String getFileAccessUrl() {
        return fileUploadProperties.getAcccessPrefix();
    }

    /****************文件上传根目录************************************/
    public static String getFileUploadPath() {
        return fileUploadProperties.getUploadPath();
    }

    /**
     * 获取文件扩展名
     *
     * @param file 文件
     * @return
     */
    public static String getFileExtention(MultipartFile file) {
        String extension = null;
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return extension;
    }

    /**
     * 根据文件扩展名判断文件是否图片格式
     *
     * @param extension 文件扩展名
     * @return
     */
    public static boolean isImage(String extension) {
        String[] imageExtension = new String[]{"jpeg", "jpg", "gif", "bmp", "png"};

        for (String e : imageExtension) if (extension.toLowerCase().equals(e)) return true;

        return false;
    }

    /**
     * 校验文件是否是图片格式
     *
     * @param file
     * @return
     */
    public static boolean checkFileIsImage(MultipartFile file) {
        if (file == null) {
            return false;
        }
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(COMMA) + 1);
        return FileUploadUtil.isImage(extension);
    }
}
