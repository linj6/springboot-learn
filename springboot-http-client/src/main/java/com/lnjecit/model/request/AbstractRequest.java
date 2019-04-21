package com.lnjecit.model.request;

import com.lnjecit.constants.SymbolConstants;
import com.lnjecit.enums.MethodTypeEnum;
import com.lnjecit.model.response.AbstractResponse;
import com.lnjecit.util.UrlEncoderUtil;
import org.apache.http.entity.ContentType;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractRequest<T extends AbstractResponse> {

    private MethodTypeEnum method = null;

    protected ContentType httpContentType = null;

    private String url = null;

    private final Map<String, String> queryParameters;

    public MethodTypeEnum getMethod() {
        return method;
    }

    public void setMethod(MethodTypeEnum method) {
        this.method = method;
    }

    public ContentType getHttpContentType() {
        return httpContentType;
    }

    public void setHttpContentType(ContentType httpContentType) {
        this.httpContentType = httpContentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AbstractRequest(String url) {
        this.url = url;
        this.queryParameters = new HashMap<>();
    }

    public String composeUrl(String baseUrl, Map<String, String> queries) throws UnsupportedEncodingException {
        Map<String, String> mapQueries = queries;
        StringBuilder urlBuilder = new StringBuilder("");
        urlBuilder.append(baseUrl).append(url);
        if (-1 == urlBuilder.indexOf(SymbolConstants.QUESTION_MARK)) {
            urlBuilder.append(SymbolConstants.QUESTION_MARK);
        }

        String query = concatQueryString(mapQueries);
        return urlBuilder.append(query).toString();
    }

    /**
     * 返回结果类
     *
     * @return 返回结果类
     */
    public abstract Class<T> getResponseClass();

    public static String concatQueryString(Map<String, String> parameters) throws UnsupportedEncodingException {
        if (null == parameters) {
            return null;
        } else {
            StringBuilder urlBuilder = new StringBuilder("");

            for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext(); urlBuilder.append(SymbolConstants.AND_SYMBOL)) {
                Map.Entry<String, String> entry = (Map.Entry) iter.next();
                String key = entry.getKey();
                String val = entry.getValue();
                urlBuilder.append(UrlEncoderUtil.encode(key));
                if (val != null) {
                    urlBuilder.append("=").append(UrlEncoderUtil.encode(val));
                }
            }

            int strIndex = urlBuilder.length();
            if (parameters.size() > 0) {
                urlBuilder.deleteCharAt(strIndex - 1);
            }

            return urlBuilder.toString();
        }
    }

    public Map<String, String> getQueryParameters() {
        return Collections.unmodifiableMap(this.queryParameters);
    }

    public <K> void putQueryParameter(String name, K value) {
        this.setParameter(this.queryParameters, name, value);
    }

    protected void putQueryParameter(String name, String value) {
        this.setParameter(this.queryParameters, name, value);
    }

    protected void setParameter(Map<String, String> map, String name, Object value) {
        if (null != map && null != name && null != value) {
            map.put(name, String.valueOf(value));
        }
    }

    @Override
    public String toString() {
        return "AbstractRequest(method=" + this.getMethod() + ", httpContentType=" + this.getHttpContentType() + ", url=" + this.getUrl() + ", queryParameters=" + this.getQueryParameters() + ")";
    }
}
