package com.lnjecit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lnj
 * @description 前缀配置
 * @date 2019-04-21 17:41
 **/
@ConfigurationProperties(prefix = "demo.service")
public class PrefixServiceConfig {
    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
