package com.apoem.mmxx.eventtracking.infrastructure.po.ro.support2;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.helpers.MessageFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class RoSupport3 {

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

    public static String gentMap(List<String> uniques, Map<String, String> keys, Map<String, String> values) {
        try {
            Optional<String> firstOptional = uniques.stream().findFirst();
            String uidFieldName = "''";
            if (firstOptional.isPresent()) {
                uidFieldName = "this." + firstOptional.get();
            }

            // key
            String idScope = StringUtils.join(
                    keys.entrySet().stream()
                            .map(o -> MessageFormatter.arrayFormat(messagePattern, new String[]{o.getValue(), o.getKey()}).getMessage())
                            .toArray(Object[]::new));

            // value
            String valueScope = StringUtils.join(
                    values.entrySet().stream()
                            .map(o -> MessageFormatter.arrayFormat(messagePattern, new String[]{o.getValue(), o.getKey()}).getMessage())
                            .toArray(Object[]::new));

            // integrate

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("uid", uidFieldName);
            paramMap.put("key", idScope);
            paramMap.put("value", valueScope);
            paramMap.put("pv", "pv");
            paramMap.put("uv", "uv");
            StringSubstitutor stringSubstitutor = new StringSubstitutor(paramMap);
            return stringSubstitutor.replace(MAP_TEMPLATE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String gentReduce(Map<String, String> values) {
        try {
            String valueScope0 = StringUtils.join(
                    values.values().stream()
                            .map(o -> MessageFormatter.arrayFormat(messagePattern2, new String[]{o}).getMessage())
                            .toArray(Object[]::new)
            );
            String valueScope1 = StringUtils.join(
                    values.values().stream()
                            .map(o -> MessageFormatter.arrayFormat(messagePattern3, new String[]{o, o, o, o, o}).getMessage())
                            .toArray(Object[]::new)
            );
            String valueScope2 = StringUtils.join(
                    values.values().stream()
                            .map(o -> MessageFormatter.arrayFormat(messagePattern4, new String[]{o, o}).getMessage())
                            .toArray(Object[]::new)
            );

            // integrate

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("pv", "pv");
            paramMap.put("uv", "uv");
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
