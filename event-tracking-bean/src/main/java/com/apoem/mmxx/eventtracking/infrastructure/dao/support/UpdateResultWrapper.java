package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: UpdateResultWrapper </p>
 * <p>Description: 返回值包装器 </p>
 * <p>Date: 2020/7/23 10:13 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class UpdateResultWrapper extends UpdateResult {

    private UpdateResult upsert;

    public UpdateResultWrapper(UpdateResult upsert) {
        this.upsert = upsert;
    }

    @Override
    public boolean wasAcknowledged() {
        return this.upsert.wasAcknowledged();
    }

    @Override
    public long getMatchedCount() {
        return this.upsert.getMatchedCount();
    }

    @Override
    public boolean isModifiedCountAvailable() {
        return this.upsert.isModifiedCountAvailable();
    }

    @Override
    public long getModifiedCount() {
        return this.upsert.getModifiedCount();
    }

    @Override
    public BsonValue getUpsertedId() {
        return this.upsert.getUpsertedId();
    }

    @Override
    public String toString() {

        return "\n" +
                ">>>>>>>>>>>>>>>>>>>>>>>>> 初始化流水号数据 >>>>>>>>>>>>>>>>>>>>>>>>>\n" +
                "wasAcknowledged - " +
                upsert.wasAcknowledged() +
                "\n" +
                "getMatchedCount - " +
                upsert.getMatchedCount() +
                "\n" +
                "getModifiedCount - " +
                upsert.getModifiedCount() +
                "\n" +
                "getUpsertedId - " +
                upsert.getUpsertedId() +
                "\n" +
                "isModifiedCountAvailable - " +
                upsert.isModifiedCountAvailable() +
                "\n" +
                "<<<<<<<<<<<<<<<<<<<<<<<<< 初始化流水号数据 <<<<<<<<<<<<<<<<<<<<<<<<<";
    }
}
