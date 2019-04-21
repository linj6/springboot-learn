package com.lnjecit.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncoderUtil {
    public static final String URL_ENCODING = "UTF-8";

    public UrlEncoderUtil() {
    }

    public static String encode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, URL_ENCODING);
    }
}
