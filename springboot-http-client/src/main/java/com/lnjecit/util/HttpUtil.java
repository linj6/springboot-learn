package com.lnjecit.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * http 请求工具类
 */
public class HttpUtil {

    private final static Log logger = LogFactory.getLog(HttpUtil.class);

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 总最大连接数
     */
    private static final int TOTAL_MAX_CONNECTION = 500;

    /**
     * 每个路由的最大连接数
     */
    private static final int MAX_CONNECTION_PER_ROUTE = 50;

    /**
     * 单例http客户端
     */
    private static final CloseableHttpClient HTTP_CLIENT = globalHttpClient();

    private static PoolingHttpClientConnectionManager getConnectionManager() {
        return PoolingHolder.HTTP_CLIENT_CONNECTION_MANAGER;
    }

    /**
     * 获取HttpClient
     *
     * @return 获取HttpClient
     */
    private static CloseableHttpClient globalHttpClient() {
        PoolingHttpClientConnectionManager connectionManager = getConnectionManager();
        connectionManager.setMaxTotal(TOTAL_MAX_CONNECTION);
        connectionManager.setDefaultMaxPerRoute(MAX_CONNECTION_PER_ROUTE);
        connectionManager.setValidateAfterInactivity(1);
        return HttpClients.custom().setConnectionManager(connectionManager).build();
    }

    /**
     * GET 请求处理
     *
     * @param requestUrl 请求 url
     * @return 请求结果
     */
    public static String get(String requestUrl) {
        HttpGet get = new HttpGet(requestUrl);
        return executeRequest(get, Charset.forName(DEFAULT_ENCODING));
    }

    /**
     * POST 请求处理
     *
     * @param requestUrl  请求 url
     * @param data        请求参数
     * @param contentType 请求参数类型
     * @return 请求结果
     */
    public static String post(String requestUrl, Object data, ContentType contentType) {
        if (StringUtils.isBlank(requestUrl)) {
            throw new RuntimeException("requestUrl cann't be null");
        }
        if (contentType == null) {
            throw new RuntimeException("contentType cann't be null");
        }
        HttpPost post = new HttpPost(requestUrl);
        if (data != null) {
            StringEntity entity = new StringEntity(data.toString(), contentType);
            post.setEntity(entity);
        }

        logger.info("HttpClient request uri -> " + requestUrl);
        logger.info("HttpClient request body -> " + data);

        return executeRequest(post, contentType.getCharset());
    }

    /**
     * 存在文件上传的请求
     *
     * @param requestUrl 请求 url
     * @param data       请求参数
     * @return 请求结果
     */
    public static String uploadFile(String requestUrl, Object data) {
        if (StringUtils.isBlank(requestUrl)) {
            throw new RuntimeException("requestUrl cann't be null");
        }
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setCharset(Charset.forName(DEFAULT_ENCODING));
        ContentType contentType = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), DEFAULT_ENCODING);
        HttpPost post = new HttpPost(requestUrl);

        if (data != null) {
            // 上传单个文件
            if (data instanceof File) {
                FileBody fileBody = new FileBody((File) data);
                multipartEntityBuilder.addPart("file", fileBody);
            } else {
                // 文件和表单参数一起提交
                Field[] fields = data.getClass().getDeclaredFields();
                try {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object fieldValue = field.get(data);
                        if (fieldValue != null) {
                            if (File.class.equals(field.getType())) {
                                FileBody fileBody = new FileBody((File) field.get(data));
                                multipartEntityBuilder.addPart(field.getName(), fileBody);
                            } else {
                                StringBody stringBody = new StringBody(field.get(data).toString(), contentType);
                                multipartEntityBuilder.addPart(field.getName(), stringBody);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    logger.error("Upload file IllegalAccessException  ->  " + e);
                    throw new RuntimeException("Upload file IllegalAccessException");
                }
            }

            HttpEntity reqEntity = multipartEntityBuilder.build();
            post.setEntity(reqEntity);
        }

        logger.info("HttpClient request uri -> " + requestUrl);
        logger.info("HttpClient request body -> " + data);

        return executeRequest(post, Charset.forName(DEFAULT_ENCODING));
    }

    private static class PoolingHolder {
        private static final PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();
    }

    private static String executeRequest(HttpRequestBase request, Charset charset) {
        Integer code;
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
            code = response.getStatusLine().getStatusCode();
            logger.info("HttpClient response status -> " + code);
            String rec = response.getEntity() != null ? EntityUtils.toString(response.getEntity(), charset) : "";
            if (HttpStatus.SC_OK != code) {
                logger.error("HttpClient response body -> " + rec);
                throw new RuntimeException("请求异常");
            }
            logger.info("HttpClient response body -> " + rec);

            return rec;
        } catch (IOException e) {
            logger.error("Httpclient request failed", e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
