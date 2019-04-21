package com.lnjecit.util;

import com.lnjecit.enums.MethodTypeEnum;
import com.lnjecit.model.request.CreateUserRequest;
import com.lnjecit.model.request.GetUserRequest;
import com.lnjecit.model.request.UpdateUserRequest;
import com.lnjecit.model.request.UploadFileRequest;
import com.lnjecit.model.response.AbstractResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @author lnj
 * @description HttpUtil 测试用例
 * @date 2019-04-20 21:08
 **/
@Slf4j
public class HttpUtilTest {

    private final static String baseUrl = "http://localhost:8080";

    /**
     * get 请求测试
     *
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    @Test
    public void get() throws UnsupportedEncodingException {
        GetUserRequest request = new GetUserRequest("/user/getUserList");
        request.setUserId(1L);
        request.setUsername("李白");
        String result = HttpUtil.get(request.composeUrl(baseUrl, request.getQueryParameters()));
        AbstractResponse response = JacksonUtil.readValue(result, request.getResponseClass());
        log.info(response.toString());
    }

    /**
     * post 请求提交表单数据测试
     */
    @Test
    public void postFormData() throws UnsupportedEncodingException {
        UpdateUserRequest request = new UpdateUserRequest("/user/updateUser");
        request.setMethod(MethodTypeEnum.POST);
        request.setHttpContentType(ContentType.APPLICATION_FORM_URLENCODED);
        request.setUserId(1L);
        request.setUsername("李太白");

        String result = HttpUtil.post(request.composeUrl(baseUrl, request.getQueryParameters()), null, request.getHttpContentType());

        AbstractResponse response = JacksonUtil.readValue(result, request.getResponseClass());
        log.info(response.toString());
    }

    /**
     * post 请求提交 json 类型数据测试
     */
    @Test
    public void postJsonObject() throws UnsupportedEncodingException {
        CreateUserRequest request = new CreateUserRequest("/user/createUser");
        request.setMethod(MethodTypeEnum.POST);
        request.setHttpContentType(ContentType.APPLICATION_JSON);

        CreateUserRequest.CreateUserBody body = new CreateUserRequest.CreateUserBody();
        body.setUsername("白居易");
        request.setBody(body);

        String result = HttpUtil.post(request.composeUrl(baseUrl, request.getQueryParameters()), JacksonUtil.toJson(request.getBody()), request.getHttpContentType());

        AbstractResponse response = JacksonUtil.readValue(result, request.getResponseClass());
        log.info(response.toString());
    }

    /**
     * post 请求上传文件测试
     */
    @Test
    public void uploadFile() throws UnsupportedEncodingException {
        UploadFileRequest request = new UploadFileRequest("/user/uploadFile");
        request.setMethod(MethodTypeEnum.POST);
        request.setHttpContentType(ContentType.MULTIPART_FORM_DATA);

        UploadFileRequest.UploadFileBody body = new UploadFileRequest.UploadFileBody();
        body.setFile(new File("D:\\workspace\\idea\\individual\\springboot-learn\\springboot-http-client\\src\\main\\resources\\static\\images.jpg"));
        request.setBody(body);

        String result = HttpUtil.uploadFile(request.composeUrl(baseUrl, request.getQueryParameters()), request.getBody());

        AbstractResponse response = JacksonUtil.readValue(result, request.getResponseClass());
        log.info(response.toString());
    }
}
