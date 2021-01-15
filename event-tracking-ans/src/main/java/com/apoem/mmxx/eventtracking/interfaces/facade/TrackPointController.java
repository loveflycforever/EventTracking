package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.application.service.TrackPointDispositionService;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.infrastructure.common.CommonResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.SuccessResponse;
import com.apoem.mmxx.eventtracking.interfaces.assembler.TrackPointCmd;
import com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint.TrackPointDeleteRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint.TrackPointInsertRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint.TrackPointListRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint.TrackPointUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointController </p>
 * <p>Description: 埋点配置控制器 </p>
 * <p>Date: 2020/7/31 10:07 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("/backend/trackPoint")
@Api(tags = "埋点配置控制器")
@Slf4j
public class TrackPointController {

    private final TrackPointDispositionService trackPointDispositionService;

    @Autowired
    public TrackPointController(TrackPointDispositionService trackPointDispositionService) {
        this.trackPointDispositionService = trackPointDispositionService;
    }

    @GetMapping("/list")
    @ApiOperation(value = "列表", notes = "展示埋点列表")
    public MsRestResponse<Object, List<TrackPointVo>> list(@RequestParam Integer page,
                                                           @RequestParam Integer pageSize,
                                                           @RequestParam(required = false) String avenue,
                                                           @RequestParam(required = false) String pageName,
                                                           @RequestParam(required = false) String methodName) {
        TrackPointListRequestDto dto = new TrackPointListRequestDto();
        dto.setPage(page);
        dto.setPageSize(pageSize);
        dto.setAvenue(avenue);
        dto.setPageName(pageName);
        dto.setMethodName(methodName);
        List<TrackPointVo> trackPointVos = trackPointDispositionService.listTrackPoint(dto);
        Integer max = trackPointDispositionService.listTrackPointSize(dto);
        return SuccessResponse.data(null, trackPointVos).setTotal(max);
    }

    @PostMapping("/append")
    @ApiOperation(value = "新增", notes = "添加埋点信息")
    public MsRestResponse<Object, Object> append(@Validated @RequestBody @ApiParam(value = "JSON格式") TrackPointInsertRequestDto dto) {
        TrackPointCmd cmd = TrackPointCmd.convertFrom(dto);
        trackPointDispositionService.appendTrackPoint(cmd);
        return CommonResponse.success();
    }

    @PostMapping("/modify")
    @ApiOperation(value = "修改", notes = "修改埋点信息")
    public MsRestResponse<Object, Object> modify(@Validated @RequestBody @ApiParam(value = "JSON格式") TrackPointUpdateRequestDto dto) {
        TrackPointCmd cmd = TrackPointCmd.convertFrom(dto);
        trackPointDispositionService.modifyTrackPoint(cmd);
        return CommonResponse.success();
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除埋点信息")
    public MsRestResponse<Object, Object> remove(@Validated @RequestBody @ApiParam(value = "JSON格式") TrackPointDeleteRequestDto dto) {
        TrackPointCmd cmd = TrackPointCmd.convertFrom(dto);
        trackPointDispositionService.removeTrackPoint(cmd);
        return CommonResponse.success();
    }
}
