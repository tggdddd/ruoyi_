package cn.iocoder.yudao.module.c.controller.admin.Util;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachReqVO;
import cn.iocoder.yudao.module.c.service.UtilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 合同生成")
@RestController
@RequestMapping("/c/util")
@Validated
public class ContractGeneratorController {
    @Resource
    UtilService utilService;
    @PostMapping("/getAttach")
    @Operation(summary = "获得合同附件")
    public CommonResult<String> generatorContractAttach(@Valid @RequestBody AttachReqVO attachReqVO) {

        return success(utilService.attachGenerator(attachReqVO));
    }

}
