package com.yangfei.functioneasyexcel.util;

import java.nio.charset.Charset;
import java.util.List;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

/**
 * 从本地获取JSON数据的工具类
 */
public class LocalJsonUtil {

    /**
     * 从指定路径获取JSON并转换为List
     * @param path json文件路径
     * @param elementType List元素类型
     */
    public static  <T> List<T> getListFromJson(String path, Class<T> elementType) {
        ClassPathResource resource = new ClassPathResource(path);
        String jsonStr = IoUtil.read(resource.getStream(), Charset.forName("UTF-8"));
        JSONArray jsonArray = new JSONArray(jsonStr);
        return JSONUtil.toList(jsonArray, elementType);
    }
}
