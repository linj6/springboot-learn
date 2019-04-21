package com.lnjecit.model.request;

import com.lnjecit.model.response.CreateUserResponse;
import lombok.Data;
import lombok.ToString;

/**
 * @author lnj
 * @description 新增用户请求参数
 * @date 2019-04-20 22:25
 **/
@ToString(callSuper = true)
public class CreateUserRequest extends AbstractRequest<CreateUserResponse> {

    private CreateUserBody body;

    public CreateUserRequest(String url) {
        super(url);
    }

    public CreateUserBody getBody() {
        return body;
    }

    public void setBody(CreateUserBody body) {
        this.body = body;
    }

    @Override
    public Class<CreateUserResponse> getResponseClass() {
        return CreateUserResponse.class;
    }

    @Data
    public static class CreateUserBody {
        private String username;
    }
}
