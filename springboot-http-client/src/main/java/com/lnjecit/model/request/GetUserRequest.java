package com.lnjecit.model.request;

import com.lnjecit.model.response.GetUserListResponse;
import lombok.ToString;

/**
 * @author lnj
 * @description 获取用户请求参数
 * @date 2019-04-20 21:15
 **/
@ToString(callSuper = true)
public class GetUserRequest extends AbstractRequest<GetUserListResponse> {
    private Long userId;
    private String username;

    public GetUserRequest(String url) {
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
    public Class<GetUserListResponse> getResponseClass() {
        return GetUserListResponse.class;
    }
}
