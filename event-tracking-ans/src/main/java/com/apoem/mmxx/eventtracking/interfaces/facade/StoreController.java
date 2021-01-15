package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.BaseStatsVo;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.SuccessResponse;
import com.apoem.mmxx.eventtracking.infrastructure.repository.StoreRepository;
import com.apoem.mmxx.eventtracking.infrastructure.repository.StoreStatsCmd;
import com.apoem.mmxx.eventtracking.interfaces.dto.StoreStatsRequestDto;
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
 * <p>Name: StoreController </p>
 * <p>Description: 门店控制器 </p>
 * <p>Date: 2020/8/10 15:49 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("/api/anls/store")
@Api(tags = "门店控制器")
@Slf4j
public class StoreController {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @PostMapping("/checkStats")
    @ApiOperation(value = "门店访问数据", notes = "门店访问数据")
    public MsRestResponse<StoreStatsRequestDto, List<BaseStatsVo>> checkStats(@Validated @RequestBody @ApiParam(value="JSON格式") StoreStatsRequestDto dto) {
        List<String> ids = Arrays.asList(dto.getId());
        StoreStatsCmd cmd = new StoreStatsCmd(dto.getCity(), ids, dto.getHouseType(), DateUtils.localDateTime(dto.getAcquireDate()));
        List<BaseStatsVo> agentVo = storeRepository.checkStats(cmd);
        return SuccessResponse.data(dto, agentVo);
    }
}
