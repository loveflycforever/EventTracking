package com.apoem.mmxx.eventtracking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BeanCopierUtils </p>
 * <p>Description: 值拷贝工具类</p>
 * <p>Date: 2020/7/16 15:00 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class BeanCopierUtils {

    private BeanCopierUtils() {
    }

    /**
     * 拷贝器实例缓存
     */
    private static final Cache BEAN_COPIER_CACHE = new Cache(1000);

    /**
     * 值拷贝
     *
     * @param sourceObject 源对象
     * @param targetKlass  目标类
     * @param <S>          源对象类型
     * @param <T>          目标对象类型
     * @return 目标对象实例
     */
    public static <S, T> T copy(S sourceObject, Class<T> targetKlass) {
        Key key = new Key(sourceObject.getClass(), targetKlass);
        T targetObject;
        try {
            targetObject = targetKlass.newInstance();
            BEAN_COPIER_CACHE.getIfAbsent(key, o -> generateCopier(sourceObject, targetKlass))
                    .copy(sourceObject, targetObject, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return targetObject;
    }

    /**
     * 值拷贝
     *
     * @param sourceObject 源对象
     * @param targetObject 目标对象
     * @param <S>          源对象类型
     * @param <T>          目标对象类型
     */
    public static <S, T> void copy(S sourceObject, T targetObject) {
        Key key = new Key(sourceObject.getClass(), targetObject.getClass());
        BEAN_COPIER_CACHE.getIfAbsent(key, o -> generateCopier(sourceObject, targetObject))
                .copy(sourceObject, targetObject, null);
    }

    /**
     * 非空字段获取
     *
     * @param object 对象
     * @return 字段名数据
     */
    public static String[] nonNullField(Object object) {
        BeanMap beanMap = BeanMap.create(object);
        Set<?> keySet = beanMap.keySet();
        return (String[]) keySet.stream().filter(o -> Objects.nonNull(beanMap.get(o))).distinct().toArray(String[]::new);
    }

    public static HashMap<String, Object> reflectFieldAndValue(Object obj) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (obj == null) {
                return map;
            }

            Class<?> clazz = obj.getClass();
            List<Field> fieldList = new ArrayList<>();
            while (clazz != null) {
                fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
                clazz = clazz.getSuperclass();
            }

            for (Field field : fieldList) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value != null) {
                    map.put(field.getName(), value);
                }
            }

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * 拷贝器生成
     *
     * @param sourceObjectKlass 原对象类型
     * @param targetObjectKlass 目标对象类型
     * @param <S>               原类泛型
     * @param <T>               目标类泛型
     * @return 拷贝器
     */
    private static <S, T> BeanCopier generateCopier(Class<S> sourceObjectKlass, Class<T> targetObjectKlass) {
        Key key = new Key(sourceObjectKlass, targetObjectKlass);
        BeanCopier beanCopier = BeanCopier.create(sourceObjectKlass, targetObjectKlass, false);
        BEAN_COPIER_CACHE.put(key, beanCopier);
        return beanCopier;
    }

    private static <S, T> BeanCopier generateCopier(S sourceObject, T targetObject) {
        return generateCopier(sourceObject.getClass(), targetObject.getClass());
    }

    private static <S, T> BeanCopier generateCopier(S sourceObject, Class<T> targetKlass) {
        return generateCopier(sourceObject.getClass(), targetKlass);
    }

    /**
     * 缓存
     */
    private static class Cache {

        private final Map<Key, BeanCopier> map;

        public Cache(int capacity) {
            map = new HashMap<>(capacity);
        }

        public BeanCopier getIfAbsent(Key key, Function<Key, BeanCopier> mappingFunction) {
            Objects.requireNonNull(mappingFunction);
            BeanCopier v;
            if ((v = map.get(key)) == null) {
                BeanCopier newValue;
                if ((newValue = mappingFunction.apply(key)) != null) {
                    map.put(key, newValue);
                    return newValue;
                }
            }
            return v;
        }

        public void put(Key key, BeanCopier beanCopier) {
            map.put(key, beanCopier);
        }
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    private static final class Key {
        private final Class<?> source;
        private final Class<?> target;
    }
}
