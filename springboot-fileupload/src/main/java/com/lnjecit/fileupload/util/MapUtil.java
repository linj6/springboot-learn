package com.lnjecit.fileupload.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/*******
 * 支持所有Map转换
 * @author Administrator
 *
 */
public class MapUtil {

	public static Map<String, Object> getMap(String key, Object value) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(key, value);
		return map;
	}
}
