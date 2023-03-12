package cn.iocoder.yudao.module.c.controller.admin.performreport;

import cn.iocoder.yudao.module.bpm.api.BpmProcessDefinedApi;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCreateReqVO;
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
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.module.c.convert.performreport.PerformReportConvert;
import cn.iocoder.yudao.module.c.service.performreport.PerformReportService;

@Tag(name = "管理后台 - 业绩信息")
@RestController
@RequestMapping("/c/perform-report")
@Validated
public class PerformReportController {

    @Resource
    private PerformReportService performReportService;

    @PostMapping("/create")
    @Operation(summary = "创建业绩信息")
    @PreAuthorize("@ss.hasPermission('c:perform-report:create')")
    public CommonResult<Long> createPerformReport(@Valid @RequestBody PerformReportCreateReqVO createReqVO) {
        return success(performReportService.createPerformReport(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新业绩信息")
    @PreAuthorize("@ss.hasPermission('c:perform-report:update')")
    public CommonResult<Boolean> updatePerformReport(@Valid @RequestBody PerformReportUpdateReqVO updateReqVO) {
        performReportService.updatePerformReport(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除业绩信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('c:perform-report:delete')")
    public CommonResult<Boolean> deletePerformReport(@RequestParam("id") Long id) {
        performReportService.deletePerformReport(id);
        return success(true);
    }
    @Resource
    private BpmProcessDefinedApi bpmProcessDefinedApi;
    @GetMapping("/get")
    @Operation(summary = "获得业绩信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('c:perform-report:query')")
    public CommonResult<PerformReportRespVO> getPerformReport(@RequestParam("id") Long id) {
        PerformReportDO performReport = performReportService.getPerformReport(id);
        PerformReportRespVO data = PerformReportConvert.INSTANCE.convert1(performReport);
        data.setProcessDefinedName(bpmProcessDefinedApi.getProcessDefined(data.getBpmProcessDefinitionId()).getName());
        return success(data);
    }

    @GetMapping("/list")
    @Operation(summary = "获得业绩信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('c:perform-report:query')")
    public CommonResult<List<PerformReportRespVO>> getPerformReportList(@RequestParam("ids") Collection<Long> ids) {
        List<PerformReportDO> list = performReportService.getPerformReportList(ids);
        List<PerformReportRespVO> data = PerformReportConvert.INSTANCE.convertList(list);
        for (PerformReportRespVO datum : data) {
            datum.setProcessDefinedName(bpmProcessDefinedApi.getProcessDefined(datum.getBpmProcessDefinitionId()).getName());
        }
        return success(data);
    }

    @GetMapping("/page")
    @Operation(summary = "获得业绩信息分页")
    @PreAuthorize("@ss.hasPermission('c:perform-report:query')")
    public CommonResult<PageResult<PerformReportRespVO>> getPerformReportPage(@Valid PerformReportPageReqVO pageVO) {
        PageResult<PerformReportDO> pageResult = performReportService.getPerformReportPage(pageVO);
        PageResult<PerformReportRespVO> data = PerformReportConvert.INSTANCE.convertPage(pageResult);
        for (PerformReportRespVO performReportRespVO : data.getList()) {
            performReportRespVO.setProcessDefinedName(bpmProcessDefinedApi.getProcessDefined(performReportRespVO.getBpmProcessDefinitionId()).getName());
        }
        return success(data);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出业绩信息 Excel")
    @PreAuthorize("@ss.hasPermission('c:perform-report:export')")
    @OperateLog(type = EXPORT)
    public void exportPerformReportExcel(@Valid PerformReportExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<PerformReportDO> list = performReportService.getPerformReportList(exportReqVO);
        // 导出 Excel
        List<PerformReportExcelVO> datas = PerformReportConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "业绩信息.xls", "数据", PerformReportExcelVO.class, datas);
    }



    @PostMapping("/process-instance")
    @Operation(summary = "新建业绩报告流程实例")
    @PreAuthorize("@ss.hasPermission('bpm:perform-report:create')")
    public CommonResult<String> createProcessInstance(@Valid @RequestBody ReportBpmProcessInstanceCreateReqVO createReqVO) {
        return success(performReportService.createProcessInstance(getLoginUserId(), createReqVO,createReqVO.getReportId()   ));
    }
}
