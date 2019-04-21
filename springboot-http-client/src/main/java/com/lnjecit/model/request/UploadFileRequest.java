package com.lnjecit.model.request;

import com.lnjecit.model.response.UploadFileResponse;
import lombok.Data;
import lombok.ToString;

import java.io.File;

/**
 * @author lnj
 * @description 文件上传请求参数
 * @date 2019-04-21 14:44
 **/
@ToString(callSuper = true)
public class UploadFileRequest extends AbstractRequest<UploadFileResponse> {

    private UploadFileBody body;

    public UploadFileRequest(String url) {
        super(url);
    }

    public UploadFileBody getBody() {
        return body;
    }

    public void setBody(UploadFileBody body) {
        this.body = body;
    }

    @Override
    public Class<UploadFileResponse> getResponseClass() {
        return UploadFileResponse.class;
    }

    @Data
    public static class UploadFileBody {
        private File file;
    }
}
