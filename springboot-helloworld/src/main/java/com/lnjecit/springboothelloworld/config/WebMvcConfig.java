package com.lnjecit.springboothelloworld.config;

import com.lnjecit.springboothelloworld.properties.FileUploadProperteis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author
 * @create 2018-04-16 20:14
 **/

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private FileUploadProperteis fileUploadProperteis;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(fileUploadProperteis.getStaticAccessPath()).addResourceLocations("file:" + fileUploadProperteis.getUploadFolder() + "/");
        /*
        * 说明：增加虚拟路径(在此处配置的虚拟路径，用springboot内置的tomcat时有效，用外部的tomcat也有效)
        */
        /*
        String osName = System.getProperties().getProperty("os.name");
        if (osName.toLowerCase().startsWith("windows")) {
            //Windows下
            registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/upload/");
        } else {
            //Linux或Unix下
            registry.addResourceHandler("/upload/**").addResourceLocations("file:/usr/local/upload/");
            super.addResourceHandlers(registry);
        }*/
    }

}
