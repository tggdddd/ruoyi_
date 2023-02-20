package cn.iocoder.yudao.module.c.controller.admin.Util;

import cn.iocoder.yudao.module.c.Util.ContractGenerator;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachReqVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.c.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.c.convert.contract.ContractConvert;
import cn.iocoder.yudao.module.c.service.contract.ContractService;

@Tag(name = "管理后台 - 合同生成")
@RestController
@RequestMapping("/c/util")
@Validated
public class ContractGeneratorController {

    @Resource
    private ContractService contractService;

    @GetMapping("/getAttach")
    @Operation(summary = "获得合同附件")
    @PreAuthorize("@ss.hasPermission('c:ontract:query')")
    public CommonResult<String> generatorContractAttach(@Valid AttachReqVO attachReqVO) {
        return success(ContractGenerator.generator(attachReqVO));
    }

}
