package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.dm;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractMapReduceDmDao;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.LiveGActShareEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ro.LiveGActShareRo;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
public abstract class AbstractLiveGActShareDm extends AbstractMapReduceDmDao<LiveGActShareEntity, LiveGActShareRo> {

    @Override
    public Class<LiveGActShareRo> mrResultClass() {
        return LiveGActShareRo.class;
    }

    @Override
    public Class<LiveGActShareEntity> targetClass() {
        return LiveGActShareEntity.class;
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
        return "classpath:mr/liveGActShareReduce.js";
    }

    @Override
    public String getMapFunction() {
        return "classpath:mr/liveGActShareMap.js";
    }

    @Override
    public void saveTargetData(LocalDateTime localDateTime, List<LiveGActShareEntity> crossData, List<LiveGActShareEntity> convertedTargetData) {
        convertedTargetData.forEach(o -> {
            Date current = new Date();
            o.setCreateTime(current);
            o.setUpdateTime(current);
            mongoTemplate().save(o);
        });
    }
}
