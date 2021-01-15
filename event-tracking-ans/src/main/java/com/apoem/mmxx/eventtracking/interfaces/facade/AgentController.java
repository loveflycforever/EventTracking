package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.AgentCommunityRankingVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.AgentHouseRankingVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.AgentVisitTrendVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.BaseStatsVo;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.SuccessResponse;
import com.apoem.mmxx.eventtracking.infrastructure.repository.AgentRepository;
import com.apoem.mmxx.eventtracking.interfaces.assembler.AgentVisitTrendCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.AgentRankingCommunityCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.RankingHouseCmd;
import com.apoem.mmxx.eventtracking.interfaces.dto.AgentStatsRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.agent.CommunityRankingRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.agent.HouseRankingRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.agent.VisitTrendRequestDto;
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
 * <p>Name: AgentController </p>
 * <p>Description: 经纪人控制器 </p>
 * <p>Date: 2020/8/10 7:37 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("/api/anls/agent")
@Api(tags = "经纪人控制器")
@Slf4j
public class AgentController {

    private final AgentRepository agentRepository;

    @Autowired
    public AgentController(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @PostMapping("/trend/visit")
    @ApiOperation(value = "趋势", notes = "范围（全部-WHOLE、新房-NWHS、二手房-SHHS、租房-RTHS）")
    public MsRestResponse<VisitTrendRequestDto, List<AgentVisitTrendVo>> trendVisit(@Validated @RequestBody @ApiParam(value="JSON格式") VisitTrendRequestDto dto) {
        AgentVisitTrendCmd cmd = new AgentVisitTrendCmd(dto.getCity(), dto.getAgentId(), dto.getScope(), DateUtils.localDateTime(dto.getAcquireDate()));
        List<AgentVisitTrendVo> data = agentRepository.visitTrend(cmd);
        return SuccessResponse.data(dto, data).setTotal(data.size());
    }

    @PostMapping("/ranking/community")
    @ApiOperation(value = "排名-小区", notes = "范围（全部-WHOLE、新房-NWHS、二手房-SHHS、租房-RTHS），周期（昨日-DAY1、七日-DAY7、三十日-DAY30），域（访问量-PVA、访问数-UVA、收藏-COLA、转发-RPSA）")
    public MsRestResponse<CommunityRankingRequestDto, List<AgentCommunityRankingVo>> rankingCommunity(@Validated @RequestBody @ApiParam(value="JSON格式") CommunityRankingRequestDto dto) {
        AgentRankingCommunityCmd cmd = new AgentRankingCommunityCmd(dto.getCity(), dto.getAgentId(), DateUtils.localDateTime(dto.getAcquireDate()), dto.getScope(), dto.getPeriod(), dto.getField(), dto.getNextKey());
        List<AgentCommunityRankingVo> data = agentRepository.rankingCommunity(cmd);
        return SuccessResponse.data(dto, data).setTotal(data.size());
    }

    @PostMapping("/ranking/house")
    @ApiOperation(value = "排名-房源", notes = "范围（全部-WHOLE、新房-NWHS、二手房-SHHS、租房-RTHS），周期（昨日-DAY1、七日-DAY7、三十日-DAY30），域（访问量-PVA、访问数-UVA、收藏-COLA、转发-RPSA）")
    public MsRestResponse<HouseRankingRequestDto, List<AgentHouseRankingVo>> rankingHouse(@Validated @RequestBody @ApiParam(value="JSON格式") HouseRankingRequestDto dto) {
        RankingHouseCmd cmd = new RankingHouseCmd(dto.getCity(), dto.getAgentId(), dto.getCommunityId(), DateUtils.localDateTime(dto.getAcquireDate()), dto.getScope(), dto.getPeriod(), dto.getField());
        List<AgentHouseRankingVo> vos = agentRepository.rankingHouse(cmd);
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

    @PostMapping("/checkStats")
    @ApiOperation(value = "经纪人访问数据", notes = "经纪人访问数据")
    public MsRestResponse<AgentStatsRequestDto, List<BaseStatsVo>> checkStats(@Validated @RequestBody @ApiParam(value="JSON格式") AgentStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<BaseStatsVo> vos = agentRepository.checkStats(dto.getCity(), ids, dto.getHouseType(), DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }
}
