package com.apoem.mmxx.eventtracking.infrastructure.po.ro.support;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
public class RoSupport {

    private static final String MAP_TEMPLATE =
            "function map() {\n" +
                    "    var this_unique_visitor_ids = new Array();\n" +
                    "    var this_unique_visitor = 0;\n" +
                    "    var this_page_view = 1;\n" +
                    "    var this_unique_id = {};\n" +
                    "    if (this_unique_id !== null && typeof (this_unique_id) !== 'undefined' && this_unique_id !== '') {\n" +
                    "        this_unique_visitor = 1;\n" +
                    "        this_unique_visitor_ids = this_unique_visitor_ids.concat(this_unique_id)\n" +
                    "    }\n" +
                    "    emit({\n" +
                    "        {}\n" +
                    "    }, {\n" +
                    "        page_view: this_page_view,\n" +
                    "        unique_visitor: this_unique_visitor,\n" +
                    "        unique_visitor_ids: this_unique_visitor_ids,\n" +
                    "        {}\n" +
                    "    });\n" +
                    "}";

    private static final String REDUCE_TEMPLATE =
            "function reduce(key, values) {\n" +
                    "    var this_page_view = 0;\n" +
                    "    var this_unique_visitor_ids = new Array();\n" +
                    "    {}\n" +
                    "    for (var i = 0; i < values.length; i++) {\n" +
                    "        this_page_view += values[i].page_view;\n" +
                    "        this_unique_visitor_ids = this_unique_visitor_ids.concat(values[i].unique_visitor_ids);\n" +
                    "        {}\n" +
                    "    }\n" +
                    "    var temp = new Set(this_unique_visitor_ids);\n" +
                    "    this_unique_visitor_ids = Array.from(temp);\n" +
                    "    return {\n" +
                    "        page_view: this_page_view,\n" +
                    "        unique_visitor: temp.size,\n" +
                    "        unique_visitor_ids: this_unique_visitor_ids,\n" +
                    "        {}\n" +
                    "    }\n" +
                    "}";

    static String separator = ",\n\t\t";

    static String messagePattern = "{}: this.{}";

    static String messagePattern2 = "var this_{} = ''";

    static String separator2 = ";\n\t";

    static String messagePattern3 =
            "var value_{} = values[i].{};\n" +
            "\t\tif(value_{} !== '') {\n" +
            "\t\t\tthis_{} = value_{};\n" +
            "\t\t}\n";

    static String separator3 = "\n\t\t";

    static String messagePattern4 = "{}: this_{}";

    static String separator4 = ",\n\t\t";

    public static <T> String gentMap(Class<?> sourceDataClass, Class<T> mrResultClass) {
        try {

            List<Pair<String, Integer>> uvFieldWithTrigger = getAll(sourceDataClass);
            UvFieldTrigger uvFieldTrigger = mrResultClass.getAnnotation(UvFieldTrigger.class);
            int uvFieldTriggerValue = uvFieldTrigger.value();

            Optional<String> firstOptional = uvFieldWithTrigger.stream()
                    .filter(o -> o.getRight() == uvFieldTriggerValue)
                    .map(Pair::getLeft)
                    .findFirst();
            String uvFieldName = "''";
            if (firstOptional.isPresent()) {
                uvFieldName = firstOptional.get();
            }



            Field id = mrResultClass.getDeclaredField("id");
            Class<?> idType = id.getType();
            MrSignedKey idTypeAnnotation = idType.getAnnotation(MrSignedKey.class);
            FieldNamingStrategy idFieldNamingStrategy = idTypeAnnotation.fieldNamingStrategy();
            Field[] idFields = idType.getDeclaredFields();

            String idScope = StringUtils.joinWith(separator, Arrays.stream(idFields).map(o -> {
                String name = o.getName();

                if (idFieldNamingStrategy == FieldNamingStrategy.SNAKE) {
                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
                }

                if (idFieldNamingStrategy == FieldNamingStrategy.CAMEL) {
                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
                }

                String name1 = name;
                if (o.isAnnotationPresent(SourceFieldName.class)) {
                    name1 = o.getAnnotation(SourceFieldName.class).value();
                }

                return MessageFormatter.arrayFormat(messagePattern, new String[]{name, name1}).getMessage();
            }).toArray(Object[]::new));

            // value

            Field value = mrResultClass.getDeclaredField("value");
            Class<?> valueType = value.getType();
            MrSignedValue valueTypeAnnotation = valueType.getAnnotation(MrSignedValue.class);
            FieldNamingStrategy valueFieldNamingStrategy = valueTypeAnnotation.fieldNamingStrategy();
            Field[] valueFields = valueType.getDeclaredFields();

            String valueScope = StringUtils.joinWith(separator,
                    Arrays.stream(valueFields)
                            .filter(o -> !o.isAnnotationPresent(Pv.class) && !o.isAnnotationPresent(Uv.class))
                            .map(o -> {
                                String name = o.getName();

                                if (valueFieldNamingStrategy == FieldNamingStrategy.SNAKE) {
                                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
                                }

                                if (valueFieldNamingStrategy == FieldNamingStrategy.CAMEL) {
                                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
                                }
                                return name;
                            })
                            .map(o -> MessageFormatter.arrayFormat(messagePattern, new String[]{o, o}).getMessage())
                            .toArray(Object[]::new)
            );

            // integrate

            return MessageFormatter.arrayFormat(MAP_TEMPLATE, new String[]{uvFieldName, idScope, valueScope}).getMessage();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
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

    public static <RO> String gentReduce(Class<RO> mrResultClass) {
        try {
            Field value = mrResultClass.getDeclaredField("value");
            Class<?> valueType = value.getType();
            MrSignedValue valueTypeAnnotation = valueType.getAnnotation(MrSignedValue.class);
            FieldNamingStrategy valueFieldNamingStrategy = valueTypeAnnotation.fieldNamingStrategy();
            Field[] valueFields = valueType.getDeclaredFields();

            String valueScope0 = StringUtils.joinWith(separator2,
                    Arrays.stream(valueFields)
                            .filter(o -> !o.isAnnotationPresent(Pv.class) && !o.isAnnotationPresent(Uv.class))
                            .map(o -> {
                                String name = o.getName();

                                if (valueFieldNamingStrategy == FieldNamingStrategy.SNAKE) {
                                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
                                }

                                if (valueFieldNamingStrategy == FieldNamingStrategy.CAMEL) {
                                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
                                }
                                return name;
                            })
                            .map(o -> MessageFormatter.arrayFormat(messagePattern2, new String[]{o}).getMessage())
                            .toArray(Object[]::new)
            );

            String valueScope1 = StringUtils.joinWith(separator3,
                    Arrays.stream(valueFields)
                            .filter(o -> !o.isAnnotationPresent(Pv.class) && !o.isAnnotationPresent(Uv.class))
                            .map(o -> {
                                String name = o.getName();

                                if (valueFieldNamingStrategy == FieldNamingStrategy.SNAKE) {
                                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
                                }

                                if (valueFieldNamingStrategy == FieldNamingStrategy.CAMEL) {
                                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
                                }
                                return name;
                            })
                            .map(o -> MessageFormatter.arrayFormat(messagePattern3, new String[]{o, o, o, o, o}).getMessage())
                            .toArray(Object[]::new)
            );

            String valueScope2 = StringUtils.joinWith(separator4,
                    Arrays.stream(valueFields)
                            .filter(o -> !o.isAnnotationPresent(Pv.class) && !o.isAnnotationPresent(Uv.class))
                            .map(o -> {
                                String name = o.getName();

                                if (valueFieldNamingStrategy == FieldNamingStrategy.SNAKE) {
                                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
                                }

                                if (valueFieldNamingStrategy == FieldNamingStrategy.CAMEL) {
                                    name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
                                }
                                return name;
                            })
                            .map(o -> MessageFormatter.arrayFormat(messagePattern4, new String[]{o, o}).getMessage())
                            .toArray(Object[]::new)
            );
            // integrate

            return MessageFormatter.arrayFormat(REDUCE_TEMPLATE, new String[]{valueScope0, valueScope1, valueScope2}).getMessage();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
