package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.LiveGActivityAgentVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.LiveGActivityParticipantsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.LiveGActivityVo;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.SuccessResponse;
import com.apoem.mmxx.eventtracking.infrastructure.repository.LiveGRepository;
import com.apoem.mmxx.eventtracking.interfaces.dto.LiveGStatsRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.LiveGTestStatsRequestDto;
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
 * <p>Name: LiveGController </p>
 * <p>Description: LiveG活动控制器 </p>
 * <p>Date: 2020/11/27 9:00 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@RestController
@RequestMapping("/api/anls/liveg")
@Api(tags = "liveG控制器")
@Slf4j
public class LiveGController {

    private LiveGRepository liveGRepository;

    @Autowired
    public void setLiveGRepository(LiveGRepository liveGRepository) {
        this.liveGRepository = liveGRepository;
    }

    @PostMapping("/activity/participants")
    @ApiOperation(value = "活动参与人数统计数据", notes = "活动参与人数统计数据")
    public MsRestResponse<LiveGStatsRequestDto, List<LiveGActivityParticipantsVo>> activityParticipants(@Validated @RequestBody @ApiParam(value="JSON格式") LiveGStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<LiveGActivityParticipantsVo> vos = liveGRepository.liveGActivityParticipants(ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(0);
    }

    @PostMapping("/activity")
    @ApiOperation(value = "活动统计数据", notes = "活动统计数据")
    public MsRestResponse<LiveGTestStatsRequestDto, List<LiveGActivityVo>> activity(@Validated @RequestBody @ApiParam(value="JSON格式") LiveGTestStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<LiveGActivityVo> vos;
        if("ALL".equals(dto.getScope())){
            vos = liveGRepository.liveGAct(ids, DateUtils.localDateTime(dto.getAcquireDate()));
        }else {
            vos = liveGRepository.liveGActNormal(ids, DateUtils.localDateTime(dto.getAcquireDate()));
        }
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

    @PostMapping("/activity/shop")
    @ApiOperation(value = "活动门店统计数据", notes = "活动门店统计数据")
    public MsRestResponse<LiveGStatsRequestDto, List<LiveGActivityVo>> activityShop(@Validated @RequestBody @ApiParam(value="JSON格式") LiveGStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<LiveGActivityVo> vos = liveGRepository.liveGActShop(ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }

    @PostMapping("/activity/agent")
    @ApiOperation(value = "活动经纪人统计数据", notes = "活动经纪人统计数据")
    public MsRestResponse<LiveGStatsRequestDto, List<LiveGActivityAgentVo>> activityAgent(@Validated @RequestBody @ApiParam(value="JSON格式") LiveGStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        List<LiveGActivityAgentVo> vos = liveGRepository.liveGActAgent(ids, DateUtils.localDateTime(dto.getAcquireDate()));
        return SuccessResponse.data(dto, vos).setTotal(vos.size());
    }
}
