package com.apoem.mmxx.eventtracking.interfaces.facade;

import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import com.apoem.mmxx.eventtracking.application.service.ModuleDeclarationService;
import com.apoem.mmxx.eventtracking.domain.moduledeclaration.model.vo.ModuleVo;
import com.apoem.mmxx.eventtracking.infrastructure.common.CommonResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.MsRestResponse;
import com.apoem.mmxx.eventtracking.infrastructure.common.SuccessResponse;
import com.apoem.mmxx.eventtracking.interfaces.assembler.ModuleCmd;
import com.apoem.mmxx.eventtracking.interfaces.dto.module.ModuleDeleteRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.module.ModuleInsertRequestDto;
import com.apoem.mmxx.eventtracking.interfaces.dto.module.ModuleUpdateRequestDto;
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
 * <p>Name: ModuleController </p>
 * <p>Description: 模块配置控制器 </p>
 * <p>Date: 2020/7/31 10:07 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@RestController
@RequestMapping("/backend/module")
@Api(tags = "模块配置控制器")
@Slf4j
public class ModuleController {

    private final ModuleDeclarationService moduleDeclarationService;

    @Autowired
    public ModuleController(ModuleDeclarationService moduleDeclarationService) {
        this.moduleDeclarationService = moduleDeclarationService;
    }

    @GetMapping("/list")
    @ApiOperation(value = "列表", notes = "展示模块列表")
    public MsRestResponse<Object, List<ModuleVo>> list() {
        List<ModuleVo> moduleVos = moduleDeclarationService.listModule();
        return SuccessResponse.data(null, moduleVos).setTotal(moduleVos.size());
    }


    @PostMapping("/append")
    @ApiOperation(value = "新增", notes = "添加模块信息")
    public MsRestResponse<Object, Object> append(@Validated @RequestBody @ApiParam(value="JSON格式") ModuleInsertRequestDto dto) {
        ModuleCmd cmd = BeanCopierUtils.copy(dto, ModuleCmd.class);
        moduleDeclarationService.appendModule(cmd);
        return CommonResponse.success();
    }

    @PostMapping("/modify")
    @ApiOperation(value = "修改", notes = "修改模块信息")
    public MsRestResponse<Object, Object> modify(@Validated @RequestBody @ApiParam(value="JSON格式") ModuleUpdateRequestDto dto) {
        ModuleCmd cmd = BeanCopierUtils.copy(dto, ModuleCmd.class);
        moduleDeclarationService.modifyModule(cmd);
        return CommonResponse.success();
    }

    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除模块信息")
    public MsRestResponse<Object, Object> remove(@Validated @RequestBody @ApiParam(value="JSON格式") ModuleDeleteRequestDto dto) {
        ModuleCmd cmd = BeanCopierUtils.copy(dto, ModuleCmd.class);
        moduleDeclarationService.removeModule(cmd);
        return CommonResponse.success();
    }
}
