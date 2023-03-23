package cn.iocoder.yudao.module.c.controller.admin.performreport;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.bpm.api.BpmProcessDefinedApi;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportCreateReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportExcelVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportExportReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportPageReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportRespVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.PerformReportUpdateReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.ReportBpmProcessInstanceCreateReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.StatisticsRespVO;
import cn.iocoder.yudao.module.c.convert.performreport.PerformReportConvert;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.module.c.service.performreport.PerformReportService;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.PostDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import cn.iocoder.yudao.module.system.service.dept.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 业绩信息")
@RestController
@RequestMapping("/c/perform-report")
@Validated
public class PerformReportController {

    @Resource
    DeptService deptService;
    @Resource
    private PerformReportService performReportService;
    @Resource
    private PostService postService;
    @Resource
    private BpmProcessDefinedApi bpmProcessDefinedApi;

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
        // 获得拼接需要的数据
        Collection<Long> postIds = convertList(list, PerformReportDO::getPostId);
        Map<Long, PostDO> postMap = postService.getPostMap(postIds);

        Collection<Long> deptIds = convertList(list, PerformReportDO::getDeptId);
        Map<Long, DeptDO> deptMap = deptService.getDeptMap(deptIds);

        datas.forEach(data -> {
            // 设置岗位
            MapUtils.findAndThen(postMap, data.getPostId(), deptDO -> {
                data.setPostName(deptDO.getName());
            });
            // 设置部门
            MapUtils.findAndThen(deptMap, data.getDeptId(), deptDO -> {
                data.setPostName(deptDO.getName());
            });
        });
        ExcelUtils.write(response, "业绩信息.xls", "数据", PerformReportExcelVO.class, datas);
    }


    @PostMapping("/process-instance")
    @Operation(summary = "新建业绩报告流程实例")
    @PreAuthorize("@ss.hasAnyPermissions('c:perform-report:update','c:perform-report:create')")
    public CommonResult<String> createProcessInstance(@Valid @RequestBody ReportBpmProcessInstanceCreateReqVO createReqVO) {
        return success(performReportService.createProcessInstance(getLoginUserId(), createReqVO, createReqVO.getReportId()));
    }

    @GetMapping("/getStatistics")
    @Operation(summary = "获得业绩报告的统计数据")
    public CommonResult<List<StatisticsRespVO>> getReportStatistics() {
        return success(performReportService.getStatistics(getLoginUserId()));
    }

    @GetMapping("/getAllStatistics")
    @Operation(summary = "获得所有业绩报告的统计数据")
    public CommonResult<List<StatisticsRespVO>> getAllReportStatistics() {
        return success(performReportService.getStatistics());
    }

}
