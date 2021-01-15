package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.EventStatsVo;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.TrailStatsVo;
import com.apoem.mmxx.eventtracking.infrastructure.common.CommonResponse;
import com.apoem.mmxx.eventtracking.infrastructure.repository.PageRepository;
import com.apoem.mmxx.eventtracking.domain.analysis.model.vo.PageStatsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: PageController </p>
 * <p>Description: 页面控制器 </p>
 * <p>Date: 2020/8/10 15:49 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("/api/anls/house")
@Api(tags = "页面控制器")
@Slf4j
public class PageController {

    private final PageRepository pageRepository;

    @Autowired
    public PageController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @GetMapping("/scanPage")
    @ApiOperation(value = "检查页面")
    public Object scanPage(String date) {
        LocalDateTime dateTime = DateUtils.localDateTime2(date);
        List<PageStatsVo> zzzz = pageRepository.scanPage(dateTime);

        List<String> collect = zzzz.stream().map(o -> StringUtils.joinWith(", ", o.getCity(),
                o.getPageName(),
                o.getPageNameCn(),
                o.getPageView(),
                o.getUniqueVisitor())).collect(Collectors.toList());

        return CommonResponse.success().setData(collect).setTotal(collect.size());
    }

    @GetMapping("/scanEvent")
    @ApiOperation(value = "检查事件")
    public Object scanEvent(String date) {
        LocalDateTime dateTime = DateUtils.localDateTime2(date);
        List<EventStatsVo> zzzz = pageRepository.scanEvent(dateTime);

        List<String> collect = zzzz.stream().map(o -> StringUtils.joinWith(", ",
                o.getPageName(),
                o.getPageNameCn(),
                o.getMethodName(),
                o.getMethodNameCn(),
                o.getEventType(),
                o.getCity(),
                o.getPageView(),
                o.getUniqueVisitor())).collect(Collectors.toList());

        return CommonResponse.success().setData(collect).setTotal(collect.size());
    }

    @GetMapping("/scanTrail")
    @ApiOperation(value = "检查轨迹")
    public Object scanTrail(String date) {
        LocalDateTime dateTime = DateUtils.localDateTime2(date);
        List<TrailStatsVo> zzzz = pageRepository.scanTrail(dateTime);

        List<String> collect = zzzz.stream().map(o -> StringUtils.joinWith(", ",
                o.getCity(),
                o.getCustomerOpenId(),
                o.getCustomerUniqueId(),
                o.getAgentId(),
                o.getOpTime(),
                o.getOriginOpTime(),
                o.getOdsId(),
                o.getActionType(),
                o.getViewTimes(),
                o.getDuration(),
                o.getActionDefinition(),
                o.getHouseId(),
                o.getHouseType(),
                o.getPageName(),
                o.getPageNameCn(),
                o.getMethodName(),
                o.getMethodNameCn(),
                o.getEventType())).collect(Collectors.toList());

        return CommonResponse.success().setData(collect).setTotal(collect.size());
    }
}
