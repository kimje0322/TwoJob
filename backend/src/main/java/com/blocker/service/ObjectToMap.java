package com.blocker.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ObjectToMap {
	public Map ConvertObjectToMap(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();

			Map resultMap = new HashMap<>();
			for (int i = 0; i <= fields.length - 1; i++) {
				fields[i].setAccessible(true);
				resultMap.put(fields[i].getName(), fields[i].get(obj));
			}
			return resultMap;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
