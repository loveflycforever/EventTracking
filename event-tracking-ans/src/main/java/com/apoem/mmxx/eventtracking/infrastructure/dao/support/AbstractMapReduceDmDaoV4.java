package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.SpringContextHolder;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support2.RoSupport3;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.support2.Rooobject;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AbstractMapReduceDmDao </p>
 * <p>Description: 抽象mr dm </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Slf4j
public class AbstractMapReduceDmDaoV4 {

    public MongoTemplate mongoTemplate() {
        return SpringContextHolder.getBean(MongoTemplate.class);
    }

    public void executeRemoveHistory(Dictate dictate, LocalDateTime localDateTime) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", Integer.parseInt(dateDayFormat(localDateTime)));
        StringSubstitutor stringSubstitutor = new StringSubstitutor(paramMap);
        String condition = stringSubstitutor.replace(dictate.getTargetQuery());

        QuerySub query = new QuerySub(Document.parse(condition));
        DeleteResult remove = mongoTemplate().remove(query, dictate.getTargetCollection());
    }

    public MapReduceResults<Rooobject> mr(Dictate dictate) {
        String mapFunction = getMapFunction(dictate);
        String reduceFunction = getReduceFunction(dictate);
        QuerySub query = new QuerySub(Document.parse(dictate.getSourceQuery()));

        String temporaryCollectionName = dictate.getTemporaryCollection();
        String sourceCollectionName = dictate.getSourceCollection();

        return mongoTemplate().mapReduce(
                query,
                sourceCollectionName,
                mapFunction,
                reduceFunction,
//                new MapReduceOptions().outputCollection(temporaryCollectionName).finalizeFunction(finalizeFunction),
                new MapReduceOptions().outputCollection(temporaryCollectionName),
                Rooobject.class
        );
    }

    protected String getReduceFunction(Dictate dictate) {
        String valueFieldNames = dictate.getValueFieldNames();
        Map<String, String> valueMap = new Gson().fromJson(valueFieldNames, new TypeToken<Map<String, String>>() {}.getType());
        return RoSupport3.gentReduce(valueMap);
    }

    private String getMapFunction(Dictate dictate) {
        Map<String, String> keyMap = new Gson().fromJson(dictate.getKeyFieldNames(), new TypeToken<Map<String, String>>() {}.getType());
        Map<String, String> valueMap = new Gson().fromJson(dictate.getValueFieldNames(), new TypeToken<Map<String, String>>() {}.getType());
        List<String> uniqueFieldName = new Gson().fromJson(dictate.getSourceUniqueFieldName(), new TypeToken<List<String>>() {}.getType());
        return RoSupport3.gentMap(uniqueFieldName, keyMap, valueMap);
    }

    public String dateDayFormat(LocalDateTime localDateTime) {
        return DateUtils.literalYyyyMmDd(localDateTime);
    }

    public void insert(LocalDateTime localDateTime, MapReduceResults<Rooobject> rooobjects, Dictate dictate) {
        String targetCollectionName = dictate.getTargetCollection();

        Iterator<Rooobject> iterator = rooobjects.iterator();
        List<Map<String, Object>> maps = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .filter(Objects::nonNull)
                .map(ro -> {

                    Map<String, Object> map = new HashMap<>();

                    //

                    String keyFieldNames = dictate.getKeyFieldNames();
                    String valueFieldNames = dictate.getValueFieldNames();

                    Map<String, String> keyMap = new Gson().fromJson(keyFieldNames, new TypeToken<Map<String, String>>() {}.getType());
                    Map<String, String> valueMap = new Gson().fromJson(valueFieldNames, new TypeToken<Map<String, String>>() {}.getType());

                    keyMap.values().forEach(o -> map.put(o, ro.getId().get(o)));
                    valueMap.values().forEach(o -> map.put(o, ro.getValue().get(o)));

                    //

                    map.put("pv", ro.getValue().get("pv"));
                    map.put("uv", ro.getValue().get("uv"));

                    //

                    map.put("date_day", Integer.parseInt(dateDayFormat(localDateTime)));
                    map.put("period_type", dictate.getPeriod());
                    Date current = new Date();
                    map.put("create_time", current);
                    map.put("update_time", current);
                    map.put("mark", 0);

                    return map;
                })
                .collect(Collectors.toList());
        mongoTemplate().insert(maps, targetCollectionName);
    }

}
