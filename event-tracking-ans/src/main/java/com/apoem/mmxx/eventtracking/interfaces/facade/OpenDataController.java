package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.BaseStatsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.OverviewStatsVo;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.SuccessResponse;
import com.apoem.mmxx.eventtracking.infrastructure.repository.OverviewRepository;
import com.apoem.mmxx.eventtracking.interfaces.dto.BaseStatsRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.OverallStatsRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: OpenDataController </p>
 * <p>Description: 运营开放数据控制器 </p>
 * <p>Date: 2020/8/4 9:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("/api/anls")
@Api(tags = "开放数据控制器")
@Slf4j
public class OpenDataController {

    @Autowired
    private OverviewRepository overviewRepository;

    @PostMapping("/overview/checkStats")
    @ApiOperation(value = "运营统计访问数据", notes = "运营统计访问数据")
    public MsRestResponse<OverallStatsRequestDto, OverviewStatsVo> checkStats(@Validated @RequestBody @ApiParam(value="JSON格式") OverallStatsRequestDto dto) {
        OverviewStatsVo agentVo = overviewRepository.checkStats(dto.getCity(), dto.getInputType(), DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, agentVo);
    }

    @PostMapping("/overview/adCheckStats")
    @ApiOperation(value = "广告统计访问数据", notes = "广告统计访问数据")
    public MsRestResponse<BaseStatsRequestDto, List<BaseStatsVo>> adCheckStats(@Validated @RequestBody @ApiParam(value="JSON格式") BaseStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<BaseStatsVo> vos = overviewRepository.adCheckStats(dto.getCity(), ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }


    @PostMapping("/overview/courseCheckStats")
    @ApiOperation(value = "课程统计访问数据", notes = "课程统计访问数据")
    public MsRestResponse<BaseStatsRequestDto, List<BaseStatsVo>> courseCheckStats(@Validated @RequestBody @ApiParam(value="JSON格式") BaseStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<BaseStatsVo> vos = overviewRepository.courseCheckStats(dto.getCity(), ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }
}
