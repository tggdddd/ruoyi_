package cn.iocoder.yudao.module.c.controller.admin.contract;

import cn.iocoder.yudao.module.c.Util.ContractGenerator;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;
import cn.iocoder.yudao.module.c.service.UtilService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.c.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.c.convert.contract.ContractConvert;
import cn.iocoder.yudao.module.c.service.contract.ContractService;

@Tag(name = "管理后台 - 合同表单")
@RestController
@RequestMapping("/c/ontract")
@Validated
public class ContractController {

    @Resource
    private ContractService ontractService;
    @Resource
    UtilService utilService;
    @PostMapping("/create")
    @Operation(summary = "创建合同表单")
    @PreAuthorize("@ss.hasPermission('c:ontract:create')")
    public CommonResult<Long> createontract(@Valid @RequestBody ContractCreateReqVO createReqVO) {
        return success(ontractService.createontract(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新合同表单")
    @PreAuthorize("@ss.hasPermission('c:ontract:update')")
    public CommonResult<Boolean> updateontract(@Valid @RequestBody ContractUpdateReqVO updateReqVO) {
        ontractService.updateontract(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除合同表单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('c:ontract:delete')")
    public CommonResult<Boolean> deleteontract(@RequestParam("id") Long id) {
        ontractService.deleteontract(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得合同表单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('c:ontract:query')")
    public CommonResult<ContractRespVO> getontract(@RequestParam("id") Long id) {
        ContractDO ontract = ontractService.getontract(id);
        return success(ContractConvert.INSTANCE.convert(ontract));
    }

    @GetMapping("/list")
    @Operation(summary = "获得合同表单列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('c:ontract:query')")
    public CommonResult<List<ContractRespVO>> getontractList(@RequestParam("ids") Collection<Long> ids) {
        List<ContractDO> list = ontractService.getontractList(ids);
        return success(ContractConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得合同表单分页")
    @PreAuthorize("@ss.hasPermission('c:ontract:query')")
    public CommonResult<PageResult<ContractRespVO>> getontractPage(@Valid ContractPageReqVO pageVO) {
        PageResult<ContractDO> pageResult = ontractService.getontractPage(pageVO);
        return success(ContractConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出合同表单 Excel")
    @PreAuthorize("@ss.hasPermission('c:ontract:export')")
    @OperateLog(type = EXPORT)
    public void exportontractExcel(@Valid ContractExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ContractDO> list = ontractService.getontractList(exportReqVO);
        // 导出 Excel
        List<ContractExcelVO> datas = ContractConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "合同表单.xls", "数据", ContractExcelVO.class, datas);
    }
    @GetMapping("/getAttach")
    @Operation(summary = "获得合同附件")
    @PreAuthorize("@ss.hasPermission('c:ontract:query')")
    public CommonResult<String> generatorContractAttach(@RequestParam("id") Long id) {
        ContractDO contractDO = ontractService.getontract(id);
        return success(utilService.attachGenerator(ContractConvert.INSTANCE.convert0(contractDO)));
    }
}
