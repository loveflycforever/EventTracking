package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.InfoTemplateH5Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: InfoTemplateH5Dao </p>
 * <p>Description: H5资讯模板PV、UV统计 </p>
 * <p>Date: 2020/11/18 14:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Repository
public interface InfoTemplateH5Dao extends MongoRepository<InfoTemplateH5Entity, Object> {

    /**
     * 条件查询
     *
     * @param city 城市
     * @param informationId 资讯
     * @param dateDay 日期
     * @return 实体
     */
    InfoTemplateH5Entity findByCityAndInformationIdAndDateDay(String city, String informationId, Integer dateDay);
}
