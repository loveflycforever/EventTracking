package com.apoem.mmxx.eventtracking.domain.trackpointdisposition.repository;

import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.aggregates.TrackPoint;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ITrackPointRepository </p>
 * <p>Description: 埋点配置Repository接口 </p>
 * <p>Date: 2020/8/3 9:58 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface ITrackPointRepository {

    /**
     * 增
     *
     * @param trackPoint 请求
     * @return 结果
     */
    TrackPointVo insert(TrackPoint trackPoint);

    /**
     * 改
     *
     * @param trackPoint 请求
     * @return 结果
     */
    TrackPointVo update(TrackPoint trackPoint);

    /**
     * 删
     *
     * @param id 唯一键
     * @return 结果
     */
    TrackPointVo delete(Long id);

    /**
     * 查
     *
     * @param id 唯一键
     * @return 结果
     */
    TrackPointVo selectOne(Long id);

    String getPageName(String pageName);

    /**
     * 条件查
     * @param endpoint 条件
     * @param pageName 条件
     * @param methodName 条件
     * @param eventType 条件
     * @param avenue 条件
     * @return 结果
     */
    TrackPointVo selectWith(String endpoint, String pageName, String methodName, String eventType, String avenue);

    /**
     * 条件查
     * @param endpoint 条件
     * @param pageName 条件
     * @param methodName 条件
     * @param eventType 条件
     * @param avenue 条件
     * @return 结果
     */
    TrackPointVo selectWithFromDb(String endpoint, String pageName, String methodName, String eventType, String avenue);

    /**
     * 全查
     *
     * @param page 条件
     * @param pageSize 条件
     * @param avenue 条件
     * @param pageName 条件
     * @param methodName 条件
     * @return 结果
     */
    List<TrackPointVo> selectAll(Integer page, Integer pageSize, String avenue, String pageName, String methodName);

    /**
     * 查找
     * @param avenue 条件
     * @param pageName 条件
     * @param methodName 条件
     * @return 结果
     */
    Integer selectSize(String avenue, String pageName, String methodName);

}
