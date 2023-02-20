package cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest;

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

import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import cn.iocoder.yudao.module.c.convert.PerformanceReportRequest.PerformanceReportRequestConvert;
import cn.iocoder.yudao.module.c.service.PerformanceReportRequest.PerformanceReportRequestService;

@Tag(name = "管理后台 - 业绩定义")
@RestController
@RequestMapping("/c/performance-report-request")
@Validated
public class PerformanceReportRequestController {

    @Resource
    private PerformanceReportRequestService performanceReportRequestService;

    @PostMapping("/create")
    @Operation(summary = "创建业绩定义")
    @PreAuthorize("@ss.hasPermission('c:performance-report-request:create')")
    public CommonResult<Long> createPerformanceReportRequest(@Valid @RequestBody PerformanceReportRequestCreateReqVO createReqVO) {
        return success(performanceReportRequestService.createPerformanceReportRequest(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新业绩定义")
    @PreAuthorize("@ss.hasPermission('c:performance-report-request:update')")
    public CommonResult<Boolean> updatePerformanceReportRequest(@Valid @RequestBody PerformanceReportRequestUpdateReqVO updateReqVO) {
        performanceReportRequestService.updatePerformanceReportRequest(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除业绩定义")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('c:performance-report-request:delete')")
    public CommonResult<Boolean> deletePerformanceReportRequest(@RequestParam("id") Long id) {
        performanceReportRequestService.deletePerformanceReportRequest(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得业绩定义")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('c:performance-report-request:query')")
    public CommonResult<PerformanceReportRequestRespVO> getPerformanceReportRequest(@RequestParam("id") Long id) {
        PerformanceReportRequestDO performanceReportRequest = performanceReportRequestService.getPerformanceReportRequest(id);
        return success(PerformanceReportRequestConvert.INSTANCE.convert(performanceReportRequest));
    }

    @GetMapping("/list")
    @Operation(summary = "获得业绩定义列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('c:performance-report-request:query')")
    public CommonResult<List<PerformanceReportRequestRespVO>> getPerformanceReportRequestList(@RequestParam("ids") Collection<Long> ids) {
        List<PerformanceReportRequestDO> list = performanceReportRequestService.getPerformanceReportRequestList(ids);
        return success(PerformanceReportRequestConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得业绩定义分页")
    @PreAuthorize("@ss.hasPermission('c:performance-report-request:query')")
    public CommonResult<PageResult<PerformanceReportRequestRespVO>> getPerformanceReportRequestPage(@Valid PerformanceReportRequestPageReqVO pageVO) {
        PageResult<PerformanceReportRequestDO> pageResult = performanceReportRequestService.getPerformanceReportRequestPage(pageVO);
        return success(PerformanceReportRequestConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出业绩定义 Excel")
    @PreAuthorize("@ss.hasPermission('c:performance-report-request:export')")
    @OperateLog(type = EXPORT)
    public void exportPerformanceReportRequestExcel(@Valid PerformanceReportRequestExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<PerformanceReportRequestDO> list = performanceReportRequestService.getPerformanceReportRequestList(exportReqVO);
        // 导出 Excel
        List<PerformanceReportRequestExcelVO> datas = PerformanceReportRequestConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "业绩定义.xls", "数据", PerformanceReportRequestExcelVO.class, datas);
    }

}
