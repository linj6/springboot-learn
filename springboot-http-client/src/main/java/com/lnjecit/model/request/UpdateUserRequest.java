package com.lnjecit.model.request;

import com.lnjecit.model.response.UpdateUserResponse;
import lombok.ToString;

/**
 * @author lnj
 * @description 修改用户请求参数
 * @date 2019-04-21 13:57
 **/
@ToString(callSuper = true)
public class UpdateUserRequest extends AbstractRequest<UpdateUserResponse> {

    private Long userId;
    private String username;

    public UpdateUserRequest(String url) {
        super(url);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        if (userId != null) {
            this.putQueryParameter("userId", userId);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        if (username != null) {
            this.putQueryParameter("username", username);
        }
    }

    @Override
    public Class<UpdateUserResponse> getResponseClass() {
        return UpdateUserResponse.class;
    }

}
