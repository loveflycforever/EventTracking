package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrackPointEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointDao </p>
 * <p>Description: TrackPointEntity 实体仓库 </p>
 * <p>Date: 2020/8/3 13:55 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface TrackPointDao extends MongoRepository<TrackPointEntity, Long> {

    /**
     * 通过页面和元素名称查找埋点
     *
     * @param endpoint   终端
     * @param pageName   页面
     * @param methodName 方法名称
     * @param eventType  事件类型
     * @param avenue     渠道
     * @return 埋点数据
     */
    TrackPointEntity findByEndpointAndPageNameAndMethodNameAndEventTypeAndAvenueAndMark(String endpoint,
                                                                                        String pageName,
                                                                                        String methodName,
                                                                                        String eventType,
                                                                                        String avenue,
                                                                                        Integer mark);

    TrackPointEntity findByEndpointAndPageNameAndMethodNameAndEventTypeAndAvenue(String endpoint,
                                                                                 String pageName,
                                                                                 String methodName,
                                                                                 String eventType,
                                                                                 String avenue);


    List<TrackPointEntity> findByAvenue(String avenue,
                                        PageRequest of);

    List<TrackPointEntity> findByPageName(String pageName,
                                          PageRequest of);

    List<TrackPointEntity> findByMethodName(String methodName,
                                            PageRequest of);

    List<TrackPointEntity> findByAvenueAndPageName(String avenue,
                                                   String pageName,
                                                   PageRequest of);

    List<TrackPointEntity> findByAvenueAndMethodName(String avenue,
                                                     String methodName,
                                                     PageRequest of);

    List<TrackPointEntity> findByPageNameAndMethodName(String pageName,
                                                       String methodName,
                                                       PageRequest of);

    List<TrackPointEntity> findByAvenueAndPageNameAndMethodName(String avenue,
                                                                String pageName,
                                                                String methodName,
                                                                PageRequest of);

    Integer countByAvenue(String avenue);

    Integer countByPageName(String pageName);

    Integer countByMethodName(String methodName);

    Integer countByAvenueAndPageName(String avenue,
                                     String pageName);

    Integer countByAvenueAndMethodName(String avenue,
                                       String methodName);

    Integer countByPageNameAndMethodName(String pageName,
                                         String methodName);

    Integer countByAvenueAndPageNameAndMethodName(String avenue,
                                                  String pageName,
                                                  String methodName);
}
