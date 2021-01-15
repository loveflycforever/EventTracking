package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.InfoAgentH5Vo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.InfoH5Vo;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.*;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: InformationStatisticsReportRespository </p>
 * <p>Description: 资讯报表 </p>
 * <p>Date: 2020/7/15 13:16 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public class InformationRepository {

    @Autowired
    private InfoH5Dao infoH5Dao;

    @Autowired
    private InfoTemplateH5Dao infoTemplateH5Dao;

    @Autowired
    private InfoAgentH5Dao infoAgentH5Dao;

    public List<InfoH5Vo> checkStats(String city, List<String> idList, LocalDateTime localDateTime) {
        Integer integer = DateUtils.numericalYyyyMmDd(localDateTime);

        List<InfoH5Vo> result = new ArrayList<>(idList.size());

        for(String id: idList){
            String infoId;
            String agentId;
            if(id.contains("_")){
                infoId = id.substring(0, id.indexOf("_"));
                agentId = id.substring(infoId.length() + 1);
            }else {
                infoId = id;
                agentId = "";
            }
//            InfoTemplateEntity infoTemplateEntity = infoTemplateStatsDao.findByCityAndInformationIdAndAgentIdAndDateDay(city, infoId, agentId, integer);
            InfoH5Entity infoH5 = infoH5Dao.findByCityAndInformationIdAndAgentIdAndDateDay(city, infoId, agentId, integer);
            InfoH5Vo vo = new InfoH5Vo();
            vo.setId(id);
            vo.setPvAmount(0L);
            vo.setUvAmount(0L);
//            if(infoTemplateEntity != null){
//                vo.setPvAmount(infoTemplateEntity.getPageView());
//                vo.setUvAmount(infoTemplateEntity.getUniqueVisitor());
//            } else {
//                vo.setPvAmount(0L);
//                vo.setUvAmount(0L);
//            }
            if(infoH5 != null){
                vo.setH5PvAmount(infoH5.getPageView());
                vo.setH5UvAmount(infoH5.getUniqueVisitor());
            } else {
                vo.setH5PvAmount(0L);
                vo.setH5UvAmount(0L);
            }
            result.add(vo);
        }

        return result;
    }

    public List<InfoH5Vo> infoTemplateStats(String city, List<String> idList, LocalDateTime localDateTime) {
        Integer integer = DateUtils.numericalYyyyMmDd(localDateTime);

//        List<BaseStatsVo> temp = new ArrayList<>(idList.size());
//        Lists.partition(idList, 100).forEach(l -> {
//            List<InformationStatsEntity> entities = informationTrendDao.findByCityAndInformationIdInAndDateDay(city, l, integer);
//
//            temp.addAll(BaseStatsVoConverter.deserialize3(entities));
//        });

        List<InfoH5Vo> result = new ArrayList<>(idList.size());

        for(String id: idList){
            InfoTemplateH5Entity infoTemplateH5 = infoTemplateH5Dao.findByCityAndInformationIdAndDateDay(city, id, integer);
            InfoH5Vo vo = new InfoH5Vo();
            vo.setId(id);
            vo.setPvAmount(0L);
            vo.setUvAmount(0L);
            if(infoTemplateH5 != null){
                vo.setH5PvAmount(infoTemplateH5.getPageView());
                vo.setH5UvAmount(infoTemplateH5.getUniqueVisitor());
            } else {
                vo.setH5PvAmount(0L);
                vo.setH5UvAmount(0L);
            }
            result.add(vo);
        }

        return result;
    }

    public List<InfoAgentH5Vo> infoAgentH5Stats(String city, List<String> idList, LocalDateTime localDateTime) {
        Integer date = DateUtils.numericalYyyyMmDd(localDateTime);

        List<InfoAgentH5Vo> result = new ArrayList<>(idList.size());

        for(String id: idList){
            InfoAgentH5Entity infoAgentH5Entity = infoAgentH5Dao.findByCityAndAgentIdAndDateDay(city, id, date);
            InfoAgentH5Vo vo = new InfoAgentH5Vo();
            vo.setId(id);
            if(infoAgentH5Entity != null){
                vo.setH5PvAmount(infoAgentH5Entity.getPageView());
                vo.setH5UvAmount(infoAgentH5Entity.getUniqueVisitor());
            } else {
                vo.setH5PvAmount(0L);
                vo.setH5UvAmount(0L);
            }
            result.add(vo);
        }

        return result;
    }
}
