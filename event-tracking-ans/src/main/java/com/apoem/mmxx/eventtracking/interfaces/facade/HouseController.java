package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.BaseStatsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.HouseStatsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.PosterStatsVo;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.SuccessResponse;
import com.apoem.mmxx.eventtracking.infrastructure.repository.HouseRepository;
import com.apoem.mmxx.eventtracking.interfaces.assembler.HouseTrendCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.VisitorCmd;
import com.apoem.mmxx.eventtracking.interfaces.dto.BaseStatsRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.HouseStatsRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.PosterStatsRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.house.HouseTrendRequestDto;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.HouseTrendVo;
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
 * <p>Name: HouseController </p>
 * <p>Description: 房源控制器 </p>
 * <p>Date: 2020/8/10 15:49 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("/api/anls/house")
@Api(tags = "房源控制器")
@Slf4j
public class HouseController {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @PostMapping("/trend")
    @ApiOperation(value = "浏览、访客、收藏、转发", notes = "周期（全部-WHOLE、昨日-DAY1、七日-DAY7）")
    public MsRestResponse<HouseTrendRequestDto, HouseTrendVo> trend(@Validated @RequestBody @ApiParam(value="JSON格式") HouseTrendRequestDto dto) {
        HouseTrendCmd cmd = new HouseTrendCmd(dto.getCity(), dto.getHouseId(), dto.getHouseType(), dto.getPeriod(), DateUtils.localDateTime(dto.getAcquireDate()));
        HouseTrendVo vo = houseRepository.trend(cmd);
        return SuccessResponse.data(dto, vo);
    }

    @PostMapping("/visitor")
    @ApiOperation(value = "访客", notes = "周期（全部-WHOLE、昨日-DAY1、七日-DAY7）")
    public MsRestResponse<HouseTrendRequestDto, List<HouseTrendVo>> visitor(@Validated @RequestBody @ApiParam(value="JSON格式") HouseTrendRequestDto dto) {
        VisitorCmd cmd = new VisitorCmd(dto.getCity(), dto.getHouseId(), dto.getHouseType(), dto.getPeriod(), DateUtils.localDateTime(dto.getAcquireDate()));
        List<HouseTrendVo> vo = houseRepository.visitor(cmd);
        return SuccessResponse.data(dto, vo).setTotal(vo.size());
    }

    @PostMapping("/checkStats")
    @ApiOperation(value = "房源访问数据", notes = "房源访问数据")
    public MsRestResponse<HouseStatsRequestDto, List<HouseStatsVo>> checkStats(@Validated @RequestBody @ApiParam(value="JSON格式") HouseStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<HouseStatsVo> vos = houseRepository.checkStats(dto.getCity(), ids, dto.getHouseType(), DateUtils.localDateTime(dto.getAcquireDate()), dto.getIsAll());
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

    @PostMapping("/houseTemplateCheckStats")
    @ApiOperation(value = "房源样式访问数据", notes = "房源样式访问数据")
    public MsRestResponse<BaseStatsRequestDto, List<BaseStatsVo>> houseTemplateCheckStats(@Validated @RequestBody @ApiParam(value="JSON格式") BaseStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<BaseStatsVo> vos = houseRepository.houseTemplateCheckStats(dto.getCity(), ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

    @PostMapping("/posterTemplateCheckStats")
    @ApiOperation(value = "海报模板访问数据", notes = "海报模板访问数据")
    public MsRestResponse<PosterStatsRequestDto, List<PosterStatsVo>> posterTemplateCheckStats(@Validated @RequestBody @ApiParam(value="JSON格式") PosterStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<PosterStatsVo> vos = houseRepository.posterTemplateCheckStats(dto.getCity(), ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

    @PostMapping("/posterCheckStats")
    @ApiOperation(value = "海报访问数据", notes = "海报访问数据")
    public MsRestResponse<PosterStatsRequestDto, List<PosterStatsVo>> posterCheckStats(@Validated @RequestBody @ApiParam(value="JSON格式") PosterStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<PosterStatsVo> vos = houseRepository.posterCheckStats(dto.getCity(), ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

}
