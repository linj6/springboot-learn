package com.lnjecit.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author lnj
 * @create 2018-05-13 16:38
 **/
public class DateUtil {

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式(yyyy年M月dd日 ah:mm:ss) 代码生成器使用
     */
    public final static String DATE_TIME_CHN_PATTERN = "yyyy年M月dd日 ah:mm:ss";

    /**
     * 年月日时分秒毫秒 文件上传文件名
     */
    public static String TIMESTAMP_TO_STRING_PATTERN = "yyyyMMddHHmmssSSS";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static Date parse(String pattern) throws ParseException {
        if (StringUtil.isNotBlank(pattern)) {
            SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
            return df.parse(pattern);
        }
        return null;
    }

    /**
     * 字符串转换为对应日期(可能会报错异常)
     *
     * @param source
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String source, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse(source);
        return date;
    }
}
