package com.lnjecit.common.base;

import com.lnjecit.common.util.IpUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * BaseController
 *
 * @author lnj
 * @create 2018-03-02 14:49
 **/
public class BaseController {

    /**
     * 获取request对象
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 获取客户端IP地址
     *
     * @return
     */
    public String getIpAddr() {
        return IpUtil.getIpAddr(getRequest());
    }


}
