package com.lnjecit.common.shiro;

import com.alibaba.fastjson.JSONObject;
import com.lnjecit.common.constants.MsgConstants;
import com.lnjecit.common.result.Result;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 拦截未登录的请求
 *
 * @author lnj
 * create 2018-05-12 17:22
 */
public class AuthorityFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        PrintWriter out = null;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            out = response.getWriter();
            out.println(JSONObject.toJSON(Result.error(MsgConstants.NOT_LOGIN)));
        } catch (Exception e) {
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
        return false;
    }

    @Bean
    public FilterRegistrationBean registration(AuthorityFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
}
