package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.PosterTemplateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PosterTemplateStatsDao </p>
 * <p>Description: H5海报模板PV、UV统计  </p>
 * <p>Date: 2020/11/17 16:04 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Repository
public interface PosterTemplateStatsDao extends MongoRepository<PosterTemplateEntity, Object> {

    /**
     * 条件查询
     *
     * @param city 城市
     * @param posterId 营销海报
     * @param dateDay 日期
     * @return 实体
     */
    PosterTemplateEntity findByCityAndPosterIdAndDateDay(String city, String posterId, Integer dateDay);
}
