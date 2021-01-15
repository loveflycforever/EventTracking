package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.acquisition.model.aggregates.InfoAgentH5Vo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.InfoH5Vo;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.SuccessResponse;
import com.apoem.mmxx.eventtracking.infrastructure.repository.InformationRepository;
import com.apoem.mmxx.eventtracking.interfaces.dto.InformationStatsRequestDto;
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
 * <p>Name: InformationController </p>
 * <p>Description: 资讯控制器 </p>
 * <p>Date: 2020/8/10 15:49 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("/api/anls/information")
@Api(tags = "资讯控制器")
@Slf4j
public class InformationController {

    private final InformationRepository informationRepository;

    @Autowired
    public InformationController(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    @PostMapping("/checkStats")
    @ApiOperation(value = "资讯访问数据", notes = "资讯访问数据")
    public MsRestResponse<InformationStatsRequestDto, List<InfoH5Vo>> checkStats(@Validated @RequestBody @ApiParam(value="JSON格式") InformationStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<InfoH5Vo> vos = informationRepository.checkStats(dto.getCity(), ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

    @PostMapping("/infoTemplateStats")
    @ApiOperation(value = "资讯模板访问数据", notes = "资讯模板访问数据")
    public MsRestResponse<InformationStatsRequestDto, List<InfoH5Vo>> infoTemplateStats(@Validated @RequestBody @ApiParam(value="JSON格式") InformationStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<InfoH5Vo> vos = informationRepository.infoTemplateStats(dto.getCity(), ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

    @PostMapping("/agent/checkStats")
    @ApiOperation(value = "资讯经纪人访问数据", notes = "资讯经纪人访问数据")
    public MsRestResponse<InformationStatsRequestDto, List<InfoAgentH5Vo>> agentCheckStats(@Validated @RequestBody @ApiParam(value="JSON格式") InformationStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<InfoAgentH5Vo> vos = informationRepository.infoAgentH5Stats(dto.getCity(), ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }
}
