package com.lnjecit.common.shiro;

import com.lnjecit.common.constants.Constants;
import com.lnjecit.entity.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 */
public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }


    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static User getLoginUser() {
        return (User) ShiroUtils.getSessionAttribute(Constants.SESSION_USER);
    }

    public static Long getUserId() {
        User user = getLoginUser();
        return null == user ? null : user.getId();
    }

    public static String getLastLoginIp() {
        User user = getLoginUser();
        return null == user ? null : user.getLastLoginIp();
    }

}
