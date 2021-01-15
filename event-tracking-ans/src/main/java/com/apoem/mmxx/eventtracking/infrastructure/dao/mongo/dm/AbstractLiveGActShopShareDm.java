package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActShopShareEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.LiveGActShopShareRo;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AbstractLiveGActShopShareDm </p>
 * <p>Description: LiveG活动门店维度分享抽象dm </p>
 * <p>Date: 2020/11/27 16:41 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Slf4j
public abstract class AbstractLiveGActShopShareDm extends AbstractMapReduceDmDao<LiveGActShopShareEntity, LiveGActShopShareRo> {

    @Override
    public Class<LiveGActShopShareRo> mrResultClass() {
        return LiveGActShopShareRo.class;
    }

    @Override
    public Class<LiveGActShopShareEntity> targetClass() {
        return LiveGActShopShareEntity.class;
    }

    @Override
    public boolean removeTemporary() {
        return true;
    }

    @Override
    protected boolean hasCrossData() {
        return false;
    }

    @Override
    public boolean removeHistory() {
        return true;
    }

    @Override
    public String getReduceFunction() {
        return "classpath:mr/liveGActShopShareReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/liveGActShopShareMap.js";
    }

    @Override
    public void saveTargetData(LocalDateTime localDateTime, List<LiveGActShopShareEntity> crossData, List<LiveGActShopShareEntity> convertedTargetData) {
        convertedTargetData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            mongoTemplate().save(o);
        });
    }
}
