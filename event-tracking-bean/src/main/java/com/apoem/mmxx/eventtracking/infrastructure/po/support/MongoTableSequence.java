package com.apoem.mmxx.eventtracking.infrastructure.po.support;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MongoTableSequence </p>
 * <p>Description: Mongo自增ID </p>
 * <p>Date: 2020/7/29 14:14 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@Document(collection = "tb_mongo_table_sequence")
public class MongoTableSequence {

    /**
     * 表名作为主键
     */
    @Id
    private String name;

    /**
     * 自增字段
     */
    private Long sequence;

}