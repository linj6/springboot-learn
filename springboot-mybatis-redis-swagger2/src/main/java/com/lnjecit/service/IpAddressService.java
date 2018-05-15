package com.lnjecit.service;

import com.lnjecit.common.util.StringUtil;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.InetAddress;

/**
 * ip地址服务
 *
 * @author
 * @create 2018-05-14 22:19
 **/
@Configuration
@Service
public class IpAddressService {

    private static Logger logger = LoggerFactory.getLogger(IpAddressService.class);

    @Value("${GeoLite2CityPath}")
    private String addressDatabasePath;

    private DatabaseReader reader;

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        try {
            String path = env.getProperty("geolite2.city.db.path");
            if (StringUtil.isNotBlank(path)) {
                addressDatabasePath = path;
            }
//            File database = new File(addressDatabasePath);
            File database = ResourceUtils.getFile("classpath:" + addressDatabasePath);
            reader = new DatabaseReader.Builder(database).build();
        } catch (Exception e) {
            logger.error("IP地址服务初始化异常:" + e.getMessage(), e);
        }
    }

    public String getAddressByIp(String ipAddress) {
        try {
            CityResponse response = reader.city(InetAddress.getByName(ipAddress));
            return response.getMostSpecificSubdivision().getNames().get("zh-CN");
        } catch (Exception e) {
            logger.error("根据IP[{}]获取省份失败:{}", ipAddress, e.getMessage());
            return null;
        }
    }
}
