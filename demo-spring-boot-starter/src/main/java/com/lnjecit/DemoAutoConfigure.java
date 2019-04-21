package com.lnjecit;

import com.lnjecit.config.PrefixServiceConfig;
import com.lnjecit.service.PrefixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lnj
 * @description 自动配置
 * ConditionalOnClass注解：当classpath下发现该类的情况下进行自动配置
 * EnableConfigurationProperties注解：使 ConfigurationProperties注解起作用
 * @date 2019-04-21 17:42
 **/
@Configuration
@ConditionalOnClass(PrefixService.class)
@EnableConfigurationProperties(PrefixServiceConfig.class)
public class DemoAutoConfigure {

    @Autowired
    private PrefixServiceConfig prefixServiceConfig;

    /**
     * ConditionalOnMissingBean 注解：当Spring Context中不存在该Bean时,
     * ConditionalOnProperty 注解：当 demo.service.enabled=true 时
     * 创建 PrefixService 实例
     *
     * @return {@link PrefixService}
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "demo.service", value = "enabled", havingValue = "true")
    public PrefixService prefixService() {
        return new PrefixService(prefixServiceConfig.getPrefix());
    }
}
