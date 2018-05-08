package com.lnjecit.elasticsearch.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * ElasticSearch配置
 *
 * @author
 * @create 2018-05-07 20:54
 **/
@Configuration
public class ElasticSearchConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchConfig.class);

    /**
     * ElasticSearch集群主机地址
     */
    @Value("${elasticsearch.host}")
    private String host;
    /**
     * 端口
     */
    @Value("${elasticsearch.port}")
    private String port;
    /**
     * 集群名称
     */
    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    /**
     * 连接池
     */
    @Value("${elasticsearch.poolSize}")
    private String poolSize;

    @Bean
    public TransportClient init() {
        LOGGER.info("Start initialization");
        TransportClient transportClient = null;
        try {
            // 自定义配置信息
            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName)
                    .build();

            transportClient = new PreBuiltTransportClient(settings);
            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(host), Integer.valueOf(port));
            transportClient.addTransportAddresses(transportAddress);
        } catch (Exception e) {
            LOGGER.error("elasticsearch TransportClient create error", e);
        }
        return transportClient;
    }

}
