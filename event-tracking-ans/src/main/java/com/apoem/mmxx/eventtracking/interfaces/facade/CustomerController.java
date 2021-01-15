package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.*;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.SuccessResponse;
import com.apoem.mmxx.eventtracking.infrastructure.repository.*;
import com.apoem.mmxx.eventtracking.interfaces.assembler.*;
import com.apoem.mmxx.eventtracking.interfaces.dto.CustomerStatsRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.customer.*;
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
 * <p>Name: CustomerController </p>
 * <p>Description: 客源控制器 </p>
 * <p>Date: 2020/8/10 11:19 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("/api/anls/customer")
@Api(tags = "客源控制器")
@Slf4j
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/trail")
    @ApiOperation(value = "轨迹", notes = "访客轨迹，范围（全部-WHOLE、浏览-VIS、收藏-COL、分享-RPS）")
    public MsRestResponse<TrailRequestDto, List<CustomerTrailVo>> trail(@Validated @RequestBody @ApiParam(value="JSON格式") TrailRequestDto dto) {
        CustomerTrailCmd cmd = new CustomerTrailCmd(dto.getCity(), dto.getCustomerId(), dto.getAgentId(), dto.getScope(), dto.getNextKey());
        List<CustomerTrailVo> vos = customerRepository.trail(cmd);
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

    @PostMapping("/trail/house")
    @ApiOperation(value = "关注房源", notes = "访客关注房源统计")
    public MsRestResponse<HouseTrailRequestDto, CustomerHouseTrailVo> houseTrail(@Validated @RequestBody @ApiParam(value="JSON格式") HouseTrailRequestDto dto) {
        CustomerHouseTrailCmd cmd = new CustomerHouseTrailCmd(dto.getCity(), dto.getCustomerId(), dto.getHouseId(), dto.getHouseType(), dto.getNextKey());
        CustomerHouseTrailVo agentVo = customerRepository.houseTrail(cmd);
        return SuccessResponse.data(dto, agentVo);
    }

    @PostMapping("/brief")
    @ApiOperation(value = "浏览统计", notes = "访客浏览最多统计")
    public MsRestResponse<BriefRequestDto, CustomerBriefVo> brief(@Validated @RequestBody @ApiParam(value="JSON格式") BriefRequestDto dto) {
        CustomerBriefCmd cmd = new CustomerBriefCmd(dto.getCity(), dto.getCustomerId(), dto.getAgentId(), DateUtils.localDateTime(dto.getAcquireDate()));
        CustomerBriefVo vo = customerRepository.brief(cmd);
        return SuccessResponse.data(dto, vo);
    }

    @PostMapping("/glance/range")
    @ApiOperation(value = "点击统计", notes = "访客点击统计")
    public MsRestResponse<RangeGlanceRequestDto, CustomerRangeGlanceVo> rangeGlance(@Validated @RequestBody @ApiParam(value="JSON格式") RangeGlanceRequestDto dto) {
        CustomerRangeGlanceCmd cmd = new CustomerRangeGlanceCmd(dto.getCity(), dto.getCustomerId(), dto.getAgentId(), dto.getField(), DateUtils.localDateTime(dto.getAcquireDate()), dto.getScope());
        CustomerRangeGlanceVo vo = customerRepository.rangeGlance(cmd);
        return SuccessResponse.data(dto, vo);
    }

    @PostMapping("/ranking/community")
    @ApiOperation(value = "关注小区", notes = "访客关注小区统计")
    public MsRestResponse<CustomerCommunityRequestDto, List<CustomerCommunityRankingVo>> communityRanking(@Validated @RequestBody @ApiParam(value="JSON格式") CustomerCommunityRequestDto dto) {
        CustomerCommunityRankingCmd cmd = new CustomerCommunityRankingCmd(dto.getCity(), dto.getCustomerId(), dto.getAgentId(), DateUtils.localDateTime(dto.getAcquireDate()), dto.getScope(), dto.getNextKey());
        List<CustomerCommunityRankingVo> vos = customerRepository.communityRanking(cmd);
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

    @PostMapping("/checkStats")
    @ApiOperation(value = "客源访问数据", notes = "客源访问数据")
    public MsRestResponse<CustomerStatsRequestDto, List<CustomerStatsVo>> checkStats(@Validated @RequestBody @ApiParam(value="JSON格式") CustomerStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<CustomerStatsVo> vos = customerRepository.checkStats(dto.getCity(), ids, dto.getHouseType(), DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }
}
