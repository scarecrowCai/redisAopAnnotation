package com.scarecrow.cai.annotation;

import java.lang.reflect.Field;

public abstract class BaseAspect {

	protected Object getParamsValue(String params, Object[] args) {
		if (!params.contains(".")) {
			return args[Integer.valueOf(params)];
		}
		String[] indexAndKey = params.split("[.]");
		return getParamsValue(args[Integer.valueOf(indexAndKey[0])], params.substring(params.indexOf(".") + 1));
	}

	private Object getParamsValue(Object obj, String key) {
		if (null == obj || key.equals("")) {
			return null;
		}
		String[] keys = key.split("[.]");
		for (int i = 0, n = keys.length; i < n; i++) {
			obj = getObjectValue(obj, keys[i]);
		}
		return obj;
	}

	private Object getObjectValue(Object obj, String key) {
		if (null == obj || key.equals("")) {
			return null;
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			if (!name.equals(key)) {
				continue;
			}
			try {
				boolean access = field.isAccessible();
				if (!access) {
					field.setAccessible(true);
				}
				return field.get(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
