/**
 * @author : 孙留平
 * @since : 2018年9月11日 下午2:44:59
 * @see:
 */
package com.administrator.platform.core.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.ognl.Ognl;

public class ArrayUtil {

    public static boolean isAvaliable(Object[] array) {
        return (null != array && array.length > 0);
    }

    /** 分割等量长度的list */
    public static List<List<Long>> splitAry(Long[] ary, int subSize) {
        int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;

        List<List<Long>> subAryList = new ArrayList<List<Long>>();

        for (int i = 0; i < count; i++) {
            int index = i * subSize;

            List<Long> list = new ArrayList<Long>();
            int j = 0;
            while (j < subSize && index < ary.length) {
                list.add(ary[index++]);
                j++;
            }

            subAryList.add(list);
        }

        return subAryList;
    }

    /**
     * 字符串转Long类型
     * 
     * @see :
     * @param :
     * @return : List<Long>
     * @param str
     * @return
     */
    public static List<Long> stringConvertArray(String str) {
        if (StringUtil.isEmpty(str)) {
            return new ArrayList<Long>();
        }

        List<Long> listLong = new ArrayList<Long>();
        if (StringUtil.isStringAvaliable(str)) {
            String[] strArray = str.split(",");
            for (int i = 0; i < strArray.length; i++) {
                if (StringUtil.isStringAvaliable(strArray[i].trim())) {
                    try {
                        listLong.add(Long.valueOf(strArray[i].trim()));
                    } catch (Exception e) {
                    }
                }
            }
        }
        return listLong;
    }

    /**
     * String 转数组
     * 
     * @see :
     * @param :
     * @return : List<String>
     * @param str
     * @return
     */
    public static List<String> stringConvertStringArray(String str) {
        if (StringUtil.isEmpty(str)) {
            return new ArrayList<String>();
        }

        List<String> listLong = new ArrayList<String>();
        if (StringUtil.isStringAvaliable(str)) {
            String[] strArray = str.split(",");
            for (int i = 0; i < strArray.length; i++) {
                if (StringUtil.isStringAvaliable(strArray[i].trim())) {
                    try {
                        listLong.add(strArray[i].trim());
                    } catch (Exception e) {
                    }
                }
            }
        }
        return listLong;
    }

    /**
     * 字符串分割,转化成set集合
     * 
     * @param str
     * @return
     */
    public static Set<Long> stringConvertSet(String str) {
        List<Long> list = stringConvertArray(str);
        return new HashSet<Long>(list);
    }

    /**
     * 塞数据
     * 
     * @see :
     * @param :
     * @return : Object[]
     * @param arr
     * @param obj
     * @return
     */
    public static Object[] push(Object[] arr, Object obj) {
        if (isAvaliable(arr) && obj != null) {
            Object[] rtn = Arrays.copyOf(arr, arr.length + 1);
            rtn[rtn.length - 1] = obj;
            return rtn;
        }
        return arr;
    }

    public static Object[] pushIncludeNull(Object[] arr, Object obj) {
        if (obj != null) {
            Object[] rtn = Arrays.copyOf(arr, arr.length + 1);
            rtn[rtn.length - 1] = obj;
            return rtn;
        }
        return arr;
    }

    /*******
     * 解析字符串：把字符串转换为Long[]类型
     * 按照逗号分隔
     * 
     * @param str
     * @return
     */
    public static Long[] analyze(String str) {
        List<Long> list = stringConvertArray(str);
        Long[] array = list.toArray(new Long[list.size()]);
        return array;
    }

    /***
     * 将某一个collection里面的对象的某一个属性取值进行拼接
     * 
     * @param collection
     * @param colnumName：属性名称
     * @param connector：连接字符
     * @return
     */
    public static String join(Collection collection, String colnumName, String connector) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        try {
            Iterator it = collection.iterator();
            int index = 0;
            while (it.hasNext()) {
                Object obj = it.next();
                sb.append(Ognl.getValue(colnumName, obj));
                if (index < collection.size() - 1) {
                    sb.append(connector);
                }
                index++;
            }
        } catch (Exception e) {
            return "";
        }
        return sb.toString();
    }

    public static String toString(Object[] a) {
        if (a == null) {
            return "";
        }
        int iMax = a.length - 1;
        if (iMax == -1) {
            return "";
        }

        StringBuilder b = new StringBuilder();
        for (int i = 0;; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax) {
                return b.toString();
            }
            b.append(", ");
        }

    }

    public static void main(String[] args) {
    }
}
