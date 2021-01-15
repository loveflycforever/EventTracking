package com.apoem.mmxx.eventtracking.domain.trackpointdisposition.service.engine;

import com.apoem.mmxx.eventtracking.infrastructure.enums.AvenueEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.EndpointEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionDefinitionEnum;
import com.apoem.mmxx.eventtracking.infrastructure.enums.ActionTypeEnum;
import com.apoem.mmxx.eventtracking.BaseConstants;
import com.apoem.mmxx.eventtracking.infrastructure.enums.EventTypeEnum;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.repository.IModuleRepository;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.aggregates.TrackPoint;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.model.vo.TrackPointVo;
import com.apoem.mmxx.eventtracking.domain.trackpointdisposition.repository.ITrackPointRepository;
import com.apoem.mmxx.eventtracking.infrastructure.convertor.TrackPointConverter;
import com.apoem.mmxx.eventtracking.interfaces.assembler.TrackPointCmd;
import com.apoem.mmxx.eventtracking.interfaces.dto.trackpoint.TrackPointListRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TrackPointManager </p>
 * <p>Description: 埋点管理器 </p>
 * <p>Date: 2020/8/3 9:58 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Slf4j
@Service
public class TrackPointManager {

    @Resource
    private ITrackPointRepository trackPointRepository;

    @Resource
    private IModuleRepository moduleRepository;

    public void checksum(TrackPointCmd cmd, boolean remove) {
        if (Objects.isNull(cmd)) {
            throw new RuntimeException("[埋点] 数据不能为空.");
        }

        if (!remove) {
            // @see ActionDefinitionEnum
            String[] actionDefinition = cmd.getActionDefinition();
            for (String ad : actionDefinition) {
                if (!ActionDefinitionEnum.isConfig(ad)) {
                    throw new RuntimeException("[actionDefinition 动作定义] 必须是 [" + ActionDefinitionEnum.string() + "] 之一.");
                }
            }

            String actionType = cmd.getActionType();
            if (!ActionTypeEnum.isConfig(actionType)) {
                throw new RuntimeException("[actionType 操作类型] 必须是 [" + ActionTypeEnum.string() + "] 之一.");
            }

            String avenue = cmd.getAvenue();
            if (!AvenueEnum.isExist(avenue)) {
                throw new RuntimeException("[avenue 渠道] 必须是 [" + AvenueEnum.string() + "] 之一.");
            }

            String eventType = cmd.getEventType();
            if (!EventTypeEnum.isExist(eventType)) {
                throw new RuntimeException("[eventType 事件类型] 必须是 [" + EventTypeEnum.string() + "] 之一.");
            }

            String endpoint = cmd.getEndpoint();
            if (!EndpointEnum.isConfig(endpoint)) {
                throw new RuntimeException("[endpoint 终端] 必须是 [" + EndpointEnum.string() + "] 之一.");
            }

            // 校验具体数值
            Set<ActionDefinitionEnum> definitionSet = Arrays.stream(actionDefinition).map(ActionDefinitionEnum::find).collect(Collectors.toSet());

            if(CollectionUtils.size(definitionSet) > 1 && definitionSet.contains(ActionDefinitionEnum.EMPTY)) {
                throw new RuntimeException("[actionDefinition 动作定义] 已定义有效值，不可配置 [" + ActionDefinitionEnum.EMPTY.getDesc() + "=" + ActionDefinitionEnum.EMPTY.getName() + "] .");
            }

            List<ActionDefinitionEnum> collect = definitionSet.stream()
                    .filter(o -> o.getKlass() != null)
                    .filter(o -> definitionSet.stream().noneMatch(def -> def.equals(o.getKlass())))
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(collect)) {
                throw new RuntimeException("[actionDefinition 动作定义] " + collect.stream().map(o -> "如果是 [" + o.getDesc() + "=" + o.getName() + "] 则必须包含 [ " + o.getKlass().getDesc() + "=" + o.getKlass().getName() + "] ").collect(Collectors.joining(BaseConstants.COMMA_SPACE)) + ".");
            }

            if (ActionTypeEnum.isTrail(actionType)) {
                ActionTypeEnum act = ActionTypeEnum.find(actionType);
                if (!act.getEventType().maybe(eventType)) {
                    throw new RuntimeException("[actionType 操作类型] 如果是 [" + act.getDesc() + "=" + act.getName() + "] 则 [eventType 事件类型] 必须是 [" + act.getEventType().getDesc() + "=" + act.getEventType().getName() + "].");
                }
            }


            Long moduleId = cmd.getModuleId();
            // 模块编号1为缺省值
            if (moduleId > 1 && moduleRepository.selectOne(moduleId) == null) {
                throw new RuntimeException("[moduleId 模块ID] must not be null, would you like to config it with value [1].");
            }

            String pageName = cmd.getPageName();
            String methodName = cmd.getMethodName();
            if (Objects.nonNull(trackPointRepository.selectWithFromDb(endpoint, pageName, methodName, eventType, avenue))) {
                throw new RuntimeException("[endpoint 终端、pageName 页面、methodName 方法、eventType 事件类型、avenue 渠道] must be unique.");
            }
        }

        if (Objects.nonNull(cmd.getId()) && Objects.isNull(trackPointRepository.selectOne(cmd.getId()))) {
            throw new RuntimeException("[id] must be useful.");
        }

    }

    public void save(TrackPointCmd cmd, boolean update) {
        TrackPoint trackPoint = TrackPointConverter.deserialize(cmd);
        if (update) {
            trackPointRepository.update(trackPoint);
        } else {
            trackPointRepository.insert(trackPoint);
        }
    }

    public void remove(TrackPointCmd cmd) {
        trackPointRepository.delete(cmd.getId());
    }

    public List<TrackPointVo> list(TrackPointListRequestDto dto) {
        Integer page = dto.getPage();
        Integer pageSize = dto.getPageSize();
        String avenue = dto.getAvenue();
        String pageName = dto.getPageName();
        String methodName = dto.getMethodName();
        return trackPointRepository.selectAll(page, pageSize, avenue, pageName, methodName);
    }

    public Integer listSize(TrackPointListRequestDto dto) {
        String avenue = dto.getAvenue();
        String pageName = dto.getPageName();
        String methodName = dto.getMethodName();
        return trackPointRepository.selectSize(avenue, pageName, methodName);
    }
}
