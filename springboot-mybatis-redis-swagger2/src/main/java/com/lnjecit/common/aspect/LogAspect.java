package com.lnjecit.common.aspect;

import com.lnjecit.common.shiro.ShiroUtils;
import com.lnjecit.common.util.DateUtil;
import com.lnjecit.common.util.IpUtil;
import com.lnjecit.service.IpAddressService;
import com.lnjecit.service.system.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志切面
 *
 * @author
 * @create 2018-05-07 16:34
 **/
@Aspect
@Component
public class LogAspect {

    static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private IpAddressService ipAddressService;

    @Autowired
    private LogService logService;


    @Pointcut("@annotation(com.lnjecit.common.aspect.Log)")
    void logPointcut() {

    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 环绕增强记录日志
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("logPointcut()")
    public Object logAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取被拦截的方法
            Method targetMethod = signature.getMethod();
            // 获取redis缓存注解信息
            Log log = targetMethod.getAnnotation(Log.class);

            HttpServletRequest request = getRequest();
            String ip = IpUtil.getIpAddr(request);//ip
            String ipAddress = ipAddressService.getAddressByIp(ip);//ip所在地区
            String description = log.description();//描述
            String username = ShiroUtils.getUsername();//操作用户
            String requestUri = request.getRequestURI();//URI
            String requestUrl = request.getRequestURL().toString();//URL
            String requestMethod = request.getMethod();//请求类型
            String className = joinPoint.getTarget().getClass().getName();//类名称
            String methodName = signature.getName();//方法名称
            Long startTime = DateUtil.currentTimeStamp();//开始时间

            Object[] paramValues = joinPoint.getArgs();// 参数值
            String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();// 参数名称

            StringBuffer arguments = new StringBuffer("{");//参数
            for (int i = 0; i < paramNames.length; i++) {
                // 过滤掉与密码相关的参数
                if (!paramNames[i].toLowerCase().contains("password")) {
                    arguments.append(paramNames[i]);
                    arguments.append(":");
                    arguments.append(paramValues[i]);
                    arguments.append(" ");
                }
            }
            arguments.append("}");

            result = joinPoint.proceed();
            Long endTime = DateUtil.currentTimeStamp();//结束时间
            Long spendTime = endTime - startTime;//消耗时间

            logger.info("ip:" + ip);
            logger.info("ipAddress:" + ipAddress);
            logger.info("操作描述:" + description);
            logger.info("用户名:" + username);
            logger.info("requestUri:" + requestUri);
            logger.info("请求路径:" + requestUrl);
            logger.info("请求方式:" + requestMethod);
            logger.info("类名:" + className);
            logger.info("方法名:" + methodName);
            logger.info("参数:" + arguments.toString());
            logger.info("执行结果:" + result.toString());
            logger.info("开始时间:" + startTime);
            logger.info("结束时间:" + endTime);
            logger.info("消耗时间:" + spendTime);

            com.lnjecit.entity.system.Log logEntity = new com.lnjecit.entity.system.Log();
            logEntity.setIp(ip);
            logEntity.setIpAddress(ipAddress);
            logEntity.setDescription(description);
            logEntity.setUsername(ShiroUtils.getUsername());
            logEntity.setRequestUri(requestUri);
            logEntity.setRequestUrl(requestUrl);
            logEntity.setRequestMethod(requestMethod);
            logEntity.setClassName(className);
            logEntity.setMethodName(methodName);
            logEntity.setArguments(arguments.toString());
            logEntity.setResult(result.toString());
            logEntity.setStartTime(startTime);
            logEntity.setEndTime(endTime);
            logEntity.setSpendTime(spendTime);

            logService.insert(logEntity);
        } catch (Exception e) {
            logger.error("记录日志出错", e);
        }
        return result;
    }

}
