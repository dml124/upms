package com.perye.common.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Array工具类
 * @Author: Perye
 * @Date: 2019-03-26
 */
public class ArrayUtil {

    /**
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean isExistenceUseList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 判断数组中某个元素是否存在(用Set)
     *
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean isExistenceUseSet(String[] arr, String targetValue) {
        Set<String> set = new HashSet<String>(Arrays.asList(arr));
        return set.contains(targetValue);
    }

    /**
     * 判断数组中某个元素是否存在(用循环)
     *
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean isExistenceUseLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

}

