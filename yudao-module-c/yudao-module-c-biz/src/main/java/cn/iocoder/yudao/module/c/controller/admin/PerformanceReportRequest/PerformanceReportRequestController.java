package cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestCreateReqVO;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestExcelVO;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestExportReqVO;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestPageReqVO;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestRespVO;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestUpdateReqVO;
import cn.iocoder.yudao.module.c.convert.PerformanceReportRequest.PerformanceReportRequestConvert;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import cn.iocoder.yudao.module.c.service.PerformanceReportRequest.PerformanceReportRequestService;
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

@Tag(name = "管理后台 - 业绩定义")
@RestController
@RequestMapping("/c/performance-report-request")
@Validated
public class PerformanceReportRequestController {

    @Resource
    DeptService deptService;
    @Resource
    private PerformanceReportRequestService performanceReportRequestService;
    @Resource
    private PostService postService;

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
        // post是 dept 误

        // 获得拼接需要的数据
        Collection<Long> postIds = convertList(list, PerformanceReportRequestDO::getPostId);
        Map<Long, PostDO> postMap = postService.getPostMap(postIds);

        Collection<Long> deptIds = convertList(list, PerformanceReportRequestDO::getDeptId);
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
        ExcelUtils.write(response, "业绩定义.xls", "数据", PerformanceReportRequestExcelVO.class, datas);
    }

}
