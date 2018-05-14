package com.lnjecit.common.shiro;

import com.lnjecit.common.constants.Constants;
import com.lnjecit.entity.system.User;
import com.lnjecit.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * 认证
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) {
        String username = (String) token.getPrincipal();
        User user = userService.getByUsername(username);
        if (null == user) {
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        user.setPassword("");
        SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER, user);
        return authenticationInfo;
    }

    @PostConstruct
    public void initCredentialsMatcher() {
        // 该句作用是重写shiro的密码验证，让shiro使用自定义密码校验
        setCredentialsMatcher(new CustomCredentialsMatcher());
    }

}
