package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import org.bson.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: QuerySub </p>
 * <p>Description:  </p>
 * <p>Date: 2020/12/30 14:34 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@AllArgsConstructor
public class QuerySub extends Query {

    private final Document document;

    @Override
    @SuppressWarnings("NullableProblems")
    public Document getQueryObject() {
        return this.document;
    }
}
