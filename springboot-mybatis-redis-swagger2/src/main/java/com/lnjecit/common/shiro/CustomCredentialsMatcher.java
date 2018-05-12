package com.lnjecit.common.shiro;

import com.lnjecit.common.constants.MsgConstants;
import com.lnjecit.common.exception.ServiceException;
import com.lnjecit.common.util.PasswordHash;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义密码校验
 *
 * @author lnj
 * @create 2017-12-02 20:02
 **/
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Object tokenCredentials = String.valueOf(token.getPassword());
        Object accountCredentials = getCredentials(info);
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        try {
            return PasswordHash.validatePassword(tokenCredentials.toString(), accountCredentials.toString());
        } catch (Exception e) {
            throw new ServiceException(MsgConstants.USERNAME_OR_PASSWORD_ERROR);
        }
    }
}
