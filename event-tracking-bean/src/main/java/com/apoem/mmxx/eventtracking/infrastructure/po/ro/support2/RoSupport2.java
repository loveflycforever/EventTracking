package com.apoem.mmxx.eventtracking.infrastructure.po.ro.support2;

import com.google.common.base.CaseFormat;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.FieldNamingStrategy;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.UvField;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support.UvFieldTrigger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: RoSupport </p>
 * <p>Description:  </p>
 * <p>Date: 2020/12/1 11:15 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class RoSupport2 {

    private static final String MAP_TEMPLATE =
            "function map() {\n" +
                    "    var this_unique_visitor_ids = new Array();\n" +
                    "    var this_unique_visitor = 0;\n" +
                    "    var this_page_view = 1;\n" +
                    "    var this_unique_id = ${uid};\n" +
                    "    if (this_unique_id !== null && typeof (this_unique_id) !== 'undefined' && this_unique_id !== '') {\n" +
                    "        this_unique_visitor = 1;\n" +
                    "        this_unique_visitor_ids = this_unique_visitor_ids.concat(this_unique_id)\n" +
                    "    }\n" +
                    "    emit({\n" +
                    "        ${key}\n" +
                    "    }, {\n" +
                    "        ${pv}: NumberLong(this_page_view),\n" +
                    "        ${uv}: NumberLong(this_unique_visitor),\n" +
                    "        ${uv}___ids: this_unique_visitor_ids,\n" +
                    "        ${value}\n" +
                    "    });\n" +
                    "}";

    private static final String REDUCE_TEMPLATE =
            "function reduce(key, values) {\n" +
                    "    var this_page_view = 0;\n" +
                    "    var this_unique_visitor_ids = new Array();\n" +
                    "    ${this_value_init}\n" +
                    "    for (var i = 0; i < values.length; i++) {\n" +
                    "        this_page_view += values[i].${pv};\n" +
                    "        this_unique_visitor_ids = this_unique_visitor_ids.concat(values[i].${uv}___ids);\n" +
                    "        ${this_value_catch}\n" +
                    "    }\n" +
                    "    var temp = new Set(this_unique_visitor_ids);\n" +
                    "    this_unique_visitor_ids = Array.from(temp);\n" +
                    "    return {\n" +
                    "        ${pv}: NumberLong(this_page_view),\n" +
                    "        ${uv}: NumberLong(temp.size),\n" +
                    "        ${uv}___ids: this_unique_visitor_ids,\n" +
                    "        ${this_value}\n" +
                    "    }\n" +
                    "}";

    static String messagePattern = "{}: this.{},\n\t\t";

    static String messagePattern2 = "var this_{} = '';\n\t";

    static String messagePattern3 =
            "var value_{} = values[i].{};\n" +
                    "\t\tif(value_{} !== '') {\n" +
                    "\t\t\tthis_{} = value_{};\n" +
                    "\t\t}\n\n\t\t";

    static String messagePattern4 = "{}: this_{},\n\t\t";

    public static <T> String gentMap(Class<?> sourceDataClass, Class<T> targetClass) {
        try {
            List<Pair<String, Integer>> uvFieldWithTrigger = getAll(sourceDataClass);
            UvFieldTrigger uvFieldTrigger = targetClass.getAnnotation(UvFieldTrigger.class);
            int uvFieldTriggerValue = uvFieldTrigger.value();

            Optional<String> firstOptional = uvFieldWithTrigger.stream()
                    .filter(o -> o.getRight() == uvFieldTriggerValue)
                    .map(Pair::getLeft)
                    .findFirst();
            String uidFieldName = "''";
            if (firstOptional.isPresent()) {
                uidFieldName = firstOptional.get();
            }

            // key
            Field[] declaredFields = targetClass.getDeclaredFields();
            List<Pair<String, String>> collect = getKeyFields(declaredFields);
            String idScope = StringUtils.join(
                    collect.stream()
                            .map(o -> MessageFormatter.arrayFormat(messagePattern, new String[]{o.getLeft(), o.getRight()}).getMessage())
                            .toArray(Object[]::new));

            // value
            Field[] valueFields = getValueFields(targetClass);

            String pvFieldName = Arrays.stream(valueFields)
                    .filter(field -> field.getAnnotation(MrValue.class).scope() == ScopeSign.PV)
                    .map(RoSupport2::fieldName)
                    .findFirst().orElseThrow(RuntimeException::new);

            String uvFieldName = Arrays.stream(valueFields)
                    .filter(field -> field.getAnnotation(MrValue.class).scope() == ScopeSign.UV)
                    .map(RoSupport2::fieldName)
                    .findFirst().orElseThrow(RuntimeException::new);

            String valueScope = StringUtils.join(
                    Arrays.stream(valueFields)
                            .filter(field -> field.getAnnotation(MrValue.class).scope() == ScopeSign.NORMAL)
                            .map(RoSupport2::fieldName)
                            .map(o -> MessageFormatter.arrayFormat(messagePattern, new String[]{o, o}).getMessage())
                            .toArray(Object[]::new));


            // integrate

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("uid", uidFieldName);
            paramMap.put("key", idScope);
            paramMap.put("value", valueScope);
            paramMap.put("pv", pvFieldName);
            paramMap.put("uv", uvFieldName);
            StringSubstitutor stringSubstitutor = new StringSubstitutor(paramMap);
            return stringSubstitutor.replace(MAP_TEMPLATE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Field[] getValueFields(Class<?> targetClass) {
        List<Field> fields = getAllFields(targetClass);
        return fields.stream().filter(o -> o.isAnnotationPresent(MrValue.class)).toArray(Field[]::new);
    }

    private static List<Pair<String, String>> getKeyFields(Field[] declaredFields) {
        return Arrays.stream(declaredFields)
                .filter(o -> o.isAnnotationPresent(MrKey.class)).map(o -> {
                    String name = fieldName(o);
                    String name1 = name;
                    if (StringUtils.isNotBlank(o.getAnnotation(MrKey.class).sourceFieldName())) {
                        name1 = o.getAnnotation(MrKey.class).sourceFieldName();
                    }

                    return Pair.of(name, name1);
                }).collect(Collectors.toList());
    }

    public static String fieldName(Field field) {
        String name = field.getName();

        FieldNamingStrategy fieldNamingStrategy = null;

        if (field.isAnnotationPresent(MrKey.class)) {
            fieldNamingStrategy = field.getAnnotation(MrKey.class).fieldNamingStrategy();
        }

        if (field.isAnnotationPresent(MrValue.class)) {
            fieldNamingStrategy = field.getAnnotation(MrValue.class).fieldNamingStrategy();
        }

        if (fieldNamingStrategy == FieldNamingStrategy.SNAKE) {
            name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
        }

        if (fieldNamingStrategy == FieldNamingStrategy.CAMEL) {
            name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
        }
        return name;
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 递归所有属性
     */
    private static List<Pair<String, Integer>> getAll(Class<?> sourceDataClass) {
        return getAll(sourceDataClass, "this");
    }

    private static List<Pair<String, Integer>> getAll(Class<?> sourceDataClass, String scope) {
        List<Pair<String, Integer>> result = new ArrayList<>();

        Field[] declaredFields = sourceDataClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = StringUtils.joinWith(".", scope, declaredField.getName());

            if (declaredField.isAnnotationPresent(UvField.class)) {
                UvField uvField = declaredField.getAnnotation(UvField.class);
                FieldNamingStrategy fieldNamingStrategy = uvField.fieldNamingStrategy();

                if (fieldNamingStrategy == FieldNamingStrategy.SNAKE) {
                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
                }

                if (fieldNamingStrategy == FieldNamingStrategy.CAMEL) {
                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
                }

                result.add(Pair.of(name, uvField.trigger()));
            }
            Class<?> type = declaredField.getType();
            if (isJavaClass(type)) {
                continue;
            }
            result.addAll(getAll(type, name));
        }
        return result;
    }

    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }

    public static <Target> String gentReduce(Class<Target> targetClass) {
        try {
            Field[] valueFields = getValueFields(targetClass);

            String pvFieldName = Arrays.stream(valueFields)
                    .filter(field -> field.getAnnotation(MrValue.class).scope() == ScopeSign.PV)
                    .map(RoSupport2::fieldName)
                    .findFirst().orElseThrow(RuntimeException::new);

            String uvFieldName = Arrays.stream(valueFields)
                    .filter(field -> field.getAnnotation(MrValue.class).scope() == ScopeSign.UV)
                    .map(RoSupport2::fieldName)
                    .findFirst().orElseThrow(RuntimeException::new);

            String valueScope0 = StringUtils.join(
                    Arrays.stream(valueFields)
                            .filter(field -> field.getAnnotation(MrValue.class).scope() == ScopeSign.NORMAL)
                            .map(RoSupport2::fieldName)
                            .map(o -> MessageFormatter.arrayFormat(messagePattern2, new String[]{o}).getMessage())
                            .toArray(Object[]::new)
            );
            String valueScope1 = StringUtils.join(
                    Arrays.stream(valueFields)
                            .filter(field -> field.getAnnotation(MrValue.class).scope() == ScopeSign.NORMAL)
                            .map(RoSupport2::fieldName)
                            .map(o -> MessageFormatter.arrayFormat(messagePattern3, new String[]{o, o, o, o, o}).getMessage())
                            .toArray(Object[]::new)
            );
            String valueScope2 = StringUtils.join(
                    Arrays.stream(valueFields)
                            .filter(field -> field.getAnnotation(MrValue.class).scope() == ScopeSign.NORMAL)
                            .map(RoSupport2::fieldName)
                            .map(o -> MessageFormatter.arrayFormat(messagePattern4, new String[]{o, o}).getMessage())
                            .toArray(Object[]::new)
            );

            // integrate

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("pv", pvFieldName);
            paramMap.put("uv", uvFieldName);
            paramMap.put("this_value_init", valueScope0);
            paramMap.put("this_value_catch", valueScope1);
            paramMap.put("this_value", valueScope2);
            StringSubstitutor stringSubstitutor = new StringSubstitutor(paramMap);
            return stringSubstitutor.replace(REDUCE_TEMPLATE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
