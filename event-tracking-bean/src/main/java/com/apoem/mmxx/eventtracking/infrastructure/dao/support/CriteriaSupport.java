package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import com.google.common.base.CaseFormat;
import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Map;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: CriteriaSupport </p>
 * <p>Description: 条件辅助 </p>
 * <p>Date: 2020/8/20 15:55 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class CriteriaSupport<T> {

    private Criteria where;

    public CriteriaSupport<T> whereIs(T t) {
        Map<String, Object> setMap = BeanCopierUtils.reflectFieldAndValue(t);

        if (MapUtils.isNotEmpty(setMap)) {
            where = new Criteria().andOperator(setMap.entrySet().stream()
                    .map(entry -> Criteria.where(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entry.getKey())).is(entry.getValue()))
                    .toArray(Criteria[]::new));
        }

        return this;
    }

    public Criteria get() {
        return this.where;
    }
}
