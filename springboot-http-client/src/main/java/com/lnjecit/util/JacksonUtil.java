package com.lnjecit.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class JacksonUtil {

    private final static Log logger = LogFactory.getLog(JacksonUtil.class);

    public final static ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     *
     * @param jsonStr   json字符串
     * @param valueType 对象类型
     * @return JavaBean对象
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, valueType);
        } catch (IOException e) {
            logger.error("json字符串转对象出现异常", e);
            throw new RuntimeException("json字符串转对象出现异常");
        }
    }

    /**
     * json字符串转java对象
     *
     * @param jsonStr      json字符串
     * @param valueTypeRef 对象类型
     * @return 对象
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, valueTypeRef);
        } catch (IOException e) {
            logger.error("json字符串转对象出现异常", e);
            throw new RuntimeException("json字符串转对象出现异常");
        }
    }

    /**
     * 把JavaBean转换为json字符串
     *
     * @param object java 对象
     * @return json字符串
     */
    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("对象转json字符串出现异常", e);
            throw new RuntimeException("对象转json字符串出现异常");
        }
    }
}
