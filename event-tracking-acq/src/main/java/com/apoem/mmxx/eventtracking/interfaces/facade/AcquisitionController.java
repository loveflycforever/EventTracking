package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.application.service.AcquisitionHandleService;
import com.apoem.mmxx.eventtracking.infrastructure.common.CommonResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.CurrentRequestHolder;
import com.apoem.mmxx.eventtracking.interfaces.assembler.BusinessAcquisitionCmd;
import com.apoem.mmxx.eventtracking.interfaces.assembler.ConsumerAcquisitionCmd;
import com.apoem.mmxx.eventtracking.interfaces.dto.acq.BusinessAcquisitionRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.acq.ConsumerAcquisitionRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.acq.WebPageAcquisitionRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AcquisitionController </p>
 * <p>Description: 埋点上报数据控制器 </p>
 * <p>Date: 2020/7/14 12:27 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("/api/acq")
@Api(tags = "埋点上报数据控制器")
@Slf4j
public class AcquisitionController {

    private final AcquisitionHandleService acquisitionHandleService;

    @Autowired
    public AcquisitionController(AcquisitionHandleService acquisitionHandleService) {
        this.acquisitionHandleService = acquisitionHandleService;
    }

    @PostMapping("/business")
    @ApiOperation(value = "B端埋点信息上报", notes = "通过接口上报商务端埋点信息")
    public Object acquisition(@Validated @RequestBody BusinessAcquisitionRequestDto dto) {
        log.info("{} --- {}, param content {}", Thread.currentThread().getStackTrace()[1], CurrentRequestHolder.getSerialNumber(), dto.toString());
        acquisitionHandleService.processBusinessEndpoint(BusinessAcquisitionCmd.convertFrom(dto));
        return CommonResponse.success();
    }

    @PostMapping({"/customer", "/consumer"})
    @ApiOperation(value = "C端埋点信息上报", notes = "通过接口上报用户端埋点信息")
    public Object acquisition(@Validated @RequestBody @ApiParam(value="JSON格式") ConsumerAcquisitionRequestDto dto) {
        log.info("{} --- {}, param content {}", Thread.currentThread().getStackTrace()[1], CurrentRequestHolder.getSerialNumber(), dto.toString());
        acquisitionHandleService.processConsumerEndpoint(ConsumerAcquisitionCmd.convertFrom(dto));
        return CommonResponse.success();
    }

    @PostMapping({"/webPage"})
    @ApiOperation(value = "网页端埋点信息上报", notes = "通过接口上报用户端埋点信息")
    public Object webPage(@Validated @RequestBody @ApiParam(value="JSON格式") WebPageAcquisitionRequestDto dto) {
        log.info("{} --- {}, param content {}", Thread.currentThread().getStackTrace()[1], CurrentRequestHolder.getSerialNumber(), dto.toString());
        acquisitionHandleService.processWebPageEndpoint(ConsumerAcquisitionCmd.convertFrom(dto));
        return CommonResponse.success();
    }

    @GetMapping("/testService")
    @ApiOperation(value = "测试服务")
    public Object testService() {
        return CommonResponse.success();
    }
}
