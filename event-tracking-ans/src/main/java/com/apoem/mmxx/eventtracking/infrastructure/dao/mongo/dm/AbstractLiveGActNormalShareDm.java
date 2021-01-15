package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActNormalShareEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.LiveGActNormalShareRo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public abstract class AbstractLiveGActNormalShareDm extends AbstractMapReduceDmDao<LiveGActNormalShareEntity, LiveGActNormalShareRo> {

    @Override
    public Class<LiveGActNormalShareRo> mrResultClass() {
        return LiveGActNormalShareRo.class;
    }

    @Override
    public Class<LiveGActNormalShareEntity> targetClass() {
        return LiveGActNormalShareEntity.class;
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
        return "classpath:mr/liveGActNormalShareReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/liveGActNormalShareMap.js";
    }

    @Override
    public void saveTargetData(LocalDateTime localDateTime, List<LiveGActNormalShareEntity> crossData, List<LiveGActNormalShareEntity> convertedTargetData) {
        convertedTargetData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            mongoTemplate().save(o);
        });
    }
}
