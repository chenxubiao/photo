package cn.chenxubiao.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * Created by chenxb on 17-3-31.
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toObject(String jsonStr, Class<T> clazz) throws Exception {
        return objectMapper.readValue(jsonStr, clazz);
    }

    public static <T> Map<String, Object> toMap(String jsonStr) throws Exception {
        return (Map)objectMapper.readValue(jsonStr, Map.class);
    }

    public static <T> Map<String, T> toMap(String jsonStr, Class<T> clazz) throws Exception {
        Map<String, Map<String, Object>> map = (Map)objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {
        });
        Map<String, T> result = new HashMap();
        Iterator var4 = map.entrySet().iterator();

        while(var4.hasNext()) {
            Map.Entry<String, Map<String, Object>> entry = (Map.Entry)var4.next();
            result.put(entry.getKey(), toObject((Map)entry.getValue(), clazz));
        }

        return result;
    }

    public static <T> List<T> toList(String jsonArrayStr, Class<T> clazz) {
        List<Map<String, Object>> list = null;
        try {
            list = (List)objectMapper.readValue(jsonArrayStr, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<T> result = new ArrayList();
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            Map<String, Object> map = (Map)var4.next();
            result.add(toObject(map, clazz));
        }

        return result;
    }

    public static <T> T toObject(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    public static String toString(Object obj) {
        if(obj != null) {
            if(obj instanceof String) {
                return (String)obj;
            }

            try {
                return objectMapper.writeValueAsString(obj);
            } catch (Throwable var2) {
                var2.printStackTrace();
            }
        }

        return "";
    }
}