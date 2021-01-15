package com.apoem.mmxx.eventtracking.exception;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: SupposeImpl </p>
 * <p>Description: 推测类实现 </p>
 * <p>Date: 2020/8/11 11:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface AbstractSuppose extends ISuppose {

    default void state(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(this, message);
        }
    }

    default void state(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BusinessException(this, nullSafeGet(messageSupplier));
        }
    }

    default void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(this, message);
        }
    }

    default void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BusinessException(this, nullSafeGet(messageSupplier));
        }
    }

    default void isNull(Object object, String message) {
        if (object != null) {
            throw new BusinessException(this, message);
        }
    }

    default void isNull(Object object, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new BusinessException(this, nullSafeGet(messageSupplier));
        }
    }

    default void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(this, message);
        }
    }

    default void notNull(Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new BusinessException(this, nullSafeGet(messageSupplier));
        }
    }

    default void hasLength(String text, String message) {
        if (!StringUtils.isNotBlank(text)) {
            throw new BusinessException(this, message);
        }
    }

    default void hasLength(String text, Supplier<String> messageSupplier) {
        if (!StringUtils.isNotBlank(text)) {
            throw new BusinessException(this, nullSafeGet(messageSupplier));
        }
    }

    default void hasText(String text, String message) {
        if (!StringUtils.isNotBlank(text)) {
            throw new BusinessException(this, message);
        }
    }

    default void hasText(String text, Supplier<String> messageSupplier) {
        if (!StringUtils.isNotBlank(text)) {
            throw new BusinessException(this, nullSafeGet(messageSupplier));
        }
    }

    default void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtils.isNotEmpty(textToSearch) && StringUtils.isNotEmpty(substring) &&
                textToSearch.contains(substring)) {
            throw new BusinessException(this, message);
        }
    }

    default void doesNotContain(String textToSearch, String substring, Supplier<String> messageSupplier) {
        if (StringUtils.isNotEmpty(textToSearch) && StringUtils.isNotEmpty(substring) &&
                textToSearch.contains(substring)) {
            throw new BusinessException(this, nullSafeGet(messageSupplier));
        }
    }

    default void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BusinessException(this, message);
        }
    }

    default void notEmpty(Object[] array, Supplier<String> messageSupplier) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BusinessException(this, nullSafeGet(messageSupplier));
        }
    }

    default void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BusinessException(this, message);
                }
            }
        }
    }

    default void noNullElements(Object[] array, Supplier<String> messageSupplier) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BusinessException(this, nullSafeGet(messageSupplier));
                }
            }
        }
    }

    default void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(this, message);
        }
    }

    default void notEmpty(Collection<?> collection, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(this, nullSafeGet(messageSupplier));
        }
    }

    default void notEmpty(Map<?, ?> map, String message) {
        if (MapUtils.isEmpty(map)) {
            throw new BusinessException(this, message);
        }
    }

    default void notEmpty(Map<?, ?> map, Supplier<String> messageSupplier) {
        if (MapUtils.isEmpty(map)) {
            throw new BusinessException(this, nullSafeGet(messageSupplier));
        }
    }

    default <T, B> void apply(Junction<T, B> junction, String message) {
        try {
            junction.apply();
        } catch (Exception e) {
            throw new BusinessException(this, message);
        }
    }

    // ------ 工具方法

    default void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, message);
        }
    }

    default void isInstanceOf(Class<?> type, Object obj, Supplier<String> messageSupplier) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, nullSafeGet(messageSupplier));
        }
    }

    default void isInstanceOf(Class<?> type, Object obj) {
        isInstanceOf(type, obj, "");
    }

    default void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, message);
        }
    }

    default void isAssignable(Class<?> superType, Class<?> subType, Supplier<String> messageSupplier) {
        notNull(superType, "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, nullSafeGet(messageSupplier));
        }
    }

    default void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }


    default void instanceCheckFailed(Class<?> type, Object obj, String msg) {
        String className = (obj != null ? obj.getClass().getName() : "null");
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.isNotEmpty(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + ("Object of class [" + className + "] must be an instance of " + type);
        }
        throw new BusinessException(this, result);
    }

    default void assignableCheckFailed(Class<?> superType, Class<?> subType, String msg) {
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.isNotEmpty(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, subType);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + (subType + " is not assignable to " + superType);
        }
        throw new BusinessException(this, result);
    }

    default boolean endsWithSeparator(String msg) {
        return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
    }

    default String messageWithTypeName(String msg, Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }

    default String nullSafeGet(Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }
}