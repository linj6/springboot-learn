package com.lnjecit.fileupload.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件上传相关属性
 */
@Component
@ConfigurationProperties(prefix = "file")
public class FileUploadProperties {

    private String uploadPath;
    private String acccessPrefix;

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getAcccessPrefix() {
        return acccessPrefix;
    }

    public void setAcccessPrefix(String acccessPrefix) {
        this.acccessPrefix = acccessPrefix;
    }
}
