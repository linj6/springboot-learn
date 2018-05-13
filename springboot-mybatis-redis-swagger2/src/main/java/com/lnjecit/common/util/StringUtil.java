package com.lnjecit.common.util;


import com.lnjecit.common.constants.Constants;

/**
 * 字符串工具类
 *
 * @author lnj
 * @create 2018-02-04 13:46
 **/
public class StringUtil {


    /**
     * 判断字符串是否为null 或长度为0
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (null == str || str.length() == 0) ? true : false;
    }

    /**
     * 如果字符串为null，或空字符串，或全为空白字符串，返回true，否则返回false
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (isEmpty(str)) {
            return true;
        }
        return str.trim().length() == 0 ? true : false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line){
        if(isBlank(line)){
            return Constants.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        char[] charArr = line.toCharArray();
        for (char ch : charArr) {
            if (Character.isUpperCase(ch)) {
                builder.append("_" + Character.toLowerCase(ch));
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * 将字符串转换为驼峰命名格式
     *
     * @param before          要转换的字符串
     * @param firstChar2Upper 首字母是否大写
     * @return
     */
    public static String transferToCamel(String before, boolean firstChar2Upper) {
        //不带"_"的字符串,则直接首字母大写后返回
        if (!before.contains("_"))
            return firstChar2Upper ? initCapitalize(before) : before;
        String[] strs = before.split("_");
        StringBuffer after = null;
        if (firstChar2Upper) {
            after = new StringBuffer(initCapitalize(strs[0]));
        } else {
            after = new StringBuffer(strs[0]);
        }
        for (int i = 1; i < strs.length; i++) {
            after.append(initCapitalize(strs[i]));
        }
        return after.toString();
    }

    /**
     * 字符串首字母大写
     *
     * @param str
     * @return
     */
    public static String initCapitalize(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 字符串首字母小写
     *
     * @param str
     * @return
     */
    public static String firstCharToLower(String str) {
        char[] chars = new char[1];
        chars[0] = str.charAt(0);
        String tempStr = new String(chars);
        if (chars[0] >= 'A' && chars[0] <= 'Z') {//当为字母时，则转换为小写
            return str.replaceFirst(tempStr, new String(tempStr).toLowerCase());
        }
        return str;
    }
}
