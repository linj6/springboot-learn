package com.lnjecit.common.shiro;


import com.lnjecit.common.util.PasswordHash;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;// AccessToken字符串

    private Long userId;

    public AccessToken() {

    }

    public AccessToken(Long userId) {
        this.userId = userId;
        try {
            this.token = PasswordHash.createHash(userId + "" + System.currentTimeMillis() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
