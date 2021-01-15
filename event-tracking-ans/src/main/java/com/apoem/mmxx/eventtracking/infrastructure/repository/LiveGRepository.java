package com.apoem.mmxx.eventtracking.infrastructure.repository;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.LiveGActivityAgentVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.LiveGActivityParticipantsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.LiveGActivityVo;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.*;
import com.apoem.mmxx.eventtracking.infrastructure.po.dm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LiveGRepository {

    @Autowired
    private LiveGActParticipantsDao liveGActParticipantsDao;

    @Autowired
    private LiveGActRegisterDao liveGActRegisterDao;

    @Autowired
    private LiveGActOccurredDao liveGActOccurredDao;

    @Autowired
    private LiveGActShareDao liveGActShareDao;

    @Autowired
    private LiveGActNormalRegisterDao liveGActNormalRegisterDao;

    @Autowired
    private LiveGActNormalOccurredDao liveGActNormalOccurredDao;

    @Autowired
    private LiveGActNormalShareDao liveGActNormalShareDao;

    @Autowired
    private LiveGActShopRegisterDao liveGActShopRegisterDao;

    @Autowired
    private LiveGActShopOccurredDao liveGActShopOccurredDao;

    @Autowired
    private LiveGActShopShareDao liveGActShopShareDao;

    @Autowired
    private LiveGActAgentViewDao liveGActAgentViewDao;

    @Autowired
    private LiveGActAgentShareDao liveGActAgentShareDao;

    public List<LiveGActivityParticipantsVo> liveGActivityParticipants(List<String> idList, LocalDateTime localDateTime){
        Integer day = DateUtils.numericalYyyyMmDd(localDateTime);

        List<LiveGActivityParticipantsVo> result = new ArrayList<>(idList.size());

        for(String id: idList){
            LiveGActParticipantsEntity h5 = liveGActParticipantsDao.findByGameIdAndOccurredAndDateDay(id, "H5", day);
            LiveGActParticipantsEntity app = liveGActParticipantsDao.findByGameIdAndOccurredAndDateDay(id, "APP", day);
            LiveGActivityParticipantsVo vo = new LiveGActivityParticipantsVo();
            vo.setId(id);
            long l = 0L;
            if(h5 != null){
                l += h5.getUniqueVisitor();
            }
            if(app != null){
                l += app.getUniqueVisitor();
            }
            vo.setParticipantsAmount(l);
            result.add(vo);
        }

        return result;
    }

    public List<LiveGActivityVo> liveGAct(List<String> idList, LocalDateTime localDateTime){
        Integer day = DateUtils.numericalYyyyMmDd(localDateTime);

        List<LiveGActivityVo> result = new ArrayList<>(idList.size());

        for(String id: idList){
            LiveGActRegisterEntity registerEntity = liveGActRegisterDao.findByGameIdAndDateDay(id, day);
            LiveGActOccurredEntity app = liveGActOccurredDao.findByGameIdAndOccurredAndDateDay(id, "APP", day);
            LiveGActOccurredEntity h5 = liveGActOccurredDao.findByGameIdAndOccurredAndDateDay(id, "H5", day);
            LiveGActShareEntity appShare = liveGActShareDao.findByGameIdAndOccurredAndDateDay(id, "APP", day);
            LiveGActShareEntity h5Share = liveGActShareDao.findByGameIdAndOccurredAndDateDay(id, "H5", day);
            LiveGActivityVo vo = new LiveGActivityVo();
            vo.setId(id);
            if(registerEntity != null){
                vo.setRegisterUvAmount(registerEntity.getUniqueVisitor());
            } else {
                vo.setRegisterUvAmount(0L);
            }
            if(app != null){
                vo.setAppPvAmount(app.getPageView());
                vo.setAppUvAmount(app.getUniqueVisitor());
            } else {
                vo.setAppPvAmount(0L);
                vo.setAppUvAmount(0L);
            }
            if(h5 != null){
                vo.setNotAppPvAmount(h5.getPageView());
                vo.setNotAppUvAmount(h5.getUniqueVisitor());
            } else {
                vo.setNotAppPvAmount(0L);
                vo.setNotAppUvAmount(0L);
            }
            if(appShare != null){
                vo.setAppShareAmount(appShare.getPageView());
            } else {
                vo.setAppShareAmount(0L);
            }
            if(h5Share != null){
                vo.setNotAppShareAmount(h5Share.getPageView());
            } else {
                vo.setNotAppShareAmount(0L);
            }
            result.add(vo);
        }

        return result;
    }

    public List<LiveGActivityVo> liveGActNormal(List<String> idList, LocalDateTime localDateTime){
        Integer day = DateUtils.numericalYyyyMmDd(localDateTime);

        List<LiveGActivityVo> result = new ArrayList<>(idList.size());

        for(String id: idList){
            LiveGActNormalRegisterEntity registerEntity = liveGActNormalRegisterDao.findByGameIdAndTestAndDateDay(id, "YES", day);
            LiveGActNormalOccurredEntity app = liveGActNormalOccurredDao.findByGameIdAndTestAndOccurredAndDateDay(id, "YES", "APP", day);
            LiveGActNormalOccurredEntity h5 = liveGActNormalOccurredDao.findByGameIdAndTestAndOccurredAndDateDay(id, "YES", "H5", day);
            LiveGActNormalShareEntity appShare = liveGActNormalShareDao.findByGameIdAndTestAndOccurredAndDateDay(id, "YES", "APP", day);
            LiveGActNormalShareEntity h5Share = liveGActNormalShareDao.findByGameIdAndTestAndOccurredAndDateDay(id, "YES", "H5", day);
            LiveGActivityVo vo = new LiveGActivityVo();
            vo.setId(id);
            if(registerEntity != null){
                vo.setRegisterUvAmount(registerEntity.getUniqueVisitor());
            } else {
                vo.setRegisterUvAmount(0L);
            }
            if(app != null){
                vo.setAppPvAmount(app.getPageView());
                vo.setAppUvAmount(app.getUniqueVisitor());
            } else {
                vo.setAppPvAmount(0L);
                vo.setAppUvAmount(0L);
            }
            if(h5 != null){
                vo.setNotAppPvAmount(h5.getPageView());
                vo.setNotAppUvAmount(h5.getUniqueVisitor());
            } else {
                vo.setNotAppPvAmount(0L);
                vo.setNotAppUvAmount(0L);
            }
            if(appShare != null){
                vo.setAppShareAmount(appShare.getPageView());
            } else {
                vo.setAppShareAmount(0L);
            }
            if(h5Share != null){
                vo.setNotAppShareAmount(h5Share.getPageView());
            } else {
                vo.setNotAppShareAmount(0L);
            }
            result.add(vo);
        }

        return result;
    }

    public List<LiveGActivityVo> liveGActShop(List<String> idList, LocalDateTime localDateTime){
        Integer day = DateUtils.numericalYyyyMmDd(localDateTime);

        List<LiveGActivityVo> result = new ArrayList<>(idList.size());

        for(String id: idList){
            String gameId;
            String stroeId;
            if(id.contains("_")){
                gameId = id.substring(0, id.indexOf("_"));
                stroeId = id.substring(gameId.length() + 1);
            }else {
                gameId = id;
                stroeId = "";
            }
            LiveGActShopRegisterEntity registerEntity = liveGActShopRegisterDao.findByGameIdAndStoreIdAndDateDay(gameId, stroeId, day);
            LiveGActShopOccurredEntity app = liveGActShopOccurredDao.findByGameIdAndStoreIdAndOccurredAndDateDay(gameId, stroeId, "APP", day);
            LiveGActShopOccurredEntity h5 = liveGActShopOccurredDao.findByGameIdAndStoreIdAndOccurredAndDateDay(gameId, stroeId, "H5", day);
            LiveGActShopShareEntity appShare = liveGActShopShareDao.findByGameIdAndStoreIdAndOccurredAndDateDay(gameId, stroeId, "APP", day);
            LiveGActShopShareEntity h5Share = liveGActShopShareDao.findByGameIdAndStoreIdAndOccurredAndDateDay(gameId, stroeId, "H5", day);
            LiveGActivityVo vo = new LiveGActivityVo();
            vo.setId(id);
            if(registerEntity != null){
                vo.setRegisterUvAmount(registerEntity.getUniqueVisitor());
            } else {
                vo.setRegisterUvAmount(0L);
            }
            if(app != null){
                vo.setAppPvAmount(app.getPageView());
                vo.setAppUvAmount(app.getUniqueVisitor());
            } else {
                vo.setAppPvAmount(0L);
                vo.setAppUvAmount(0L);
            }
            if(h5 != null){
                vo.setNotAppPvAmount(h5.getPageView());
                vo.setNotAppUvAmount(h5.getUniqueVisitor());
            } else {
                vo.setNotAppPvAmount(0L);
                vo.setNotAppUvAmount(0L);
            }
            if(appShare != null){
                vo.setAppShareAmount(appShare.getPageView());
            } else {
                vo.setAppShareAmount(0L);
            }
            if(h5Share != null){
                vo.setNotAppShareAmount(h5Share.getPageView());
            } else {
                vo.setNotAppShareAmount(0L);
            }
            result.add(vo);
        }

        return result;
    }

    public List<LiveGActivityAgentVo> liveGActAgent(List<String> idList, LocalDateTime localDateTime){
        Integer day = DateUtils.numericalYyyyMmDd(localDateTime);

        List<LiveGActivityAgentVo> result = new ArrayList<>(idList.size());

        for(String id: idList){
            String gameId;
            String agentId;
            if(id.contains("_")){
                gameId = id.substring(0, id.indexOf("_"));
                agentId = id.substring(gameId.length() + 1);
            }else {
                gameId = id;
                agentId = "";
            }
            LiveGActAgentViewEntity viewEntity = liveGActAgentViewDao.findByGameIdAndAgentIdAndDateDay(gameId, agentId, day);
            LiveGActAgentShareEntity appShareEntity = liveGActAgentShareDao.findByGameIdAndAgentIdAndOccurredAndDateDay(gameId, agentId, "APP", day);
            LiveGActAgentShareEntity h5ShareEntity = liveGActAgentShareDao.findByGameIdAndAgentIdAndOccurredAndDateDay(gameId, agentId, "H5", day);
            LiveGActivityAgentVo vo = new LiveGActivityAgentVo();
            vo.setId(id);
            if(viewEntity != null){
                vo.setActAgentAllPvAmount(viewEntity.getViewCount());
            }else {
                vo.setActAgentAllPvAmount(0L);
            }
            Long pv = 0L;
            if(appShareEntity != null){
                pv += appShareEntity.getShareCount();
            }
            if (h5ShareEntity != null){
                pv += h5ShareEntity.getShareCount();
            }
            vo.setActAgentShareAmount(pv);
            result.add(vo);
        }

        return result;
    }

}
