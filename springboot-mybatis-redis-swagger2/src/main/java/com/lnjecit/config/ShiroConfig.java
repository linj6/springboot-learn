package com.lnjecit.config;

import com.lnjecit.common.shiro.AuthorityFilter;
import com.lnjecit.common.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 *
 * @author lnj
 * @create 2018-05-11 20:23
 */
@DependsOn("springContextUtils")
@Configuration
public class ShiroConfig {

    // session过期时间 30分钟
    private static final long SESSION_TIMEOUT = 60 * 30 * 1000;

    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session过期时间为30分钟(单位：毫秒)，默认为30分钟
        sessionManager.setGlobalSessionTimeout(SESSION_TIMEOUT);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager(SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * Shiro Realm 继承自AuthorizingRealm的自定义Realm,即指定Shiro验证用户登录的类为自定义的
     */
    @Bean("userRealm")
    @DependsOn({"lifecycleBeanPostProcessor"})
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", new AuthorityFilter());
        shiroFilter.setFilters(filterMap);

        shiroFilter.setLoginUrl("/index.html");

        //设置不验证的url
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/index", "anon");
        filterChainDefinitionMap.put("/index.html", "anon");
        // swagger
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        // 登录
        filterChainDefinitionMap.put("/login", "anon");
        // 退出登录
        filterChainDefinitionMap.put("/logout", "anon");
        // 是否登录
        filterChainDefinitionMap.put("/notLogin", "anon");
        filterChainDefinitionMap.put("/isLogin", "anon");
        // 未授权
        filterChainDefinitionMap.put("/notAuthority", "anon");
        // 静态资源文件
        filterChainDefinitionMap.put("/static/**", "anon");
        // ueditro富文本编辑器初始化
        filterChainDefinitionMap.put("/ueditor/exec", "anon");
        // 错误处理
        filterChainDefinitionMap.put("/error/**", "anon");

        // filterChainDefinitionMap.put("/goodsCategory/**", "authc");
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilter;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager(sessionManager()));
        return authorizationAttributeSourceAdvisor;
    }

}
