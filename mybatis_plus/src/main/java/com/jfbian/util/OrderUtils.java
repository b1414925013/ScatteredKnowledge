package com.jfbian.util;

import com.google.common.collect.Ordering;

import java.util.List;

/**
 * 排序工具类
 */
public class OrderUtils {

    public static final Ordering<Comparable> NATURAL = Ordering.natural();

    /**
     * 是否自然排序
     *
     * @param iterable
     * @return
     */
    public static boolean isNaturalOrder(Iterable iterable) {
        return NATURAL.isOrdered(iterable);
    }

    /**
     * 是否反自然排序
     *
     * @param iterable
     * @return
     */
    public static boolean isAntiNaturalOrder(Iterable iterable) {
        return NATURAL.reverse().isOrdered(iterable);
    }

    /**
     * 将输入的集合转化为自然排序
     *
     * @param list
     */
    public static List naturalList(List list) {
        return NATURAL.sortedCopy(list);
    }

    /**
     * 将输入的集合转化为反自然排序
     *
     * @param list
     */
    public static List antiNaturalList(List list) {
        return NATURAL.reverse().sortedCopy(list);
    }
}
