package cn.iocoder.yudao.module.c.controller.admin.contracttemplate;

import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.module.c.Util.ContractGenerator;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachReqVO;
import cn.iocoder.yudao.module.c.convert.contract.ContractConvert;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.c.service.UtilService;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.PostDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import cn.iocoder.yudao.module.system.service.dept.PostService;
import org.mapstruct.Mapper;
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

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;
import cn.iocoder.yudao.module.c.convert.contracttemplate.ContractTemplateConvert;
import cn.iocoder.yudao.module.c.service.contracttemplate.ContractTemplateService;

@Tag(name = "管理后台 - 合同表单模板")
@RestController
@RequestMapping("/c/ontract-template")
@Validated
public class ContractTemplateController {

    @Resource
    private ContractTemplateService ontractTemplateService;
    @Resource
    UtilService utilService;
    @Resource
    PostService postService;
    @Resource
    DeptService deptService;
    @PostMapping("/create")
    @Operation(summary = "创建合同表单模板")
    @PreAuthorize("@ss.hasPermission('c:ontract-template:create')")
    public CommonResult<Long> createContractTemplate(@Valid @RequestBody ContractTemplateCreateReqVO createReqVO) {
        utilService.attachGenerator(ContractConvert.INSTANCE.convert(createReqVO));
        return success(ontractTemplateService.createontractTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新合同表单模板")
    @PreAuthorize("@ss.hasPermission('c:ontract-template:update')")
    public CommonResult<Boolean> updateContractTemplate(@Valid @RequestBody ContractTemplateUpdateReqVO updateReqVO) {
        utilService.attachGenerator(ContractConvert.INSTANCE.convert(updateReqVO));
        ontractTemplateService.updateontractTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除合同表单模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('c:ontract-template:delete')")
    public CommonResult<Boolean> deleteContractTemplate(@RequestParam("id") Long id) {
        ontractTemplateService.deleteontractTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得合同表单模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('c:ontract-template:query')")
    public CommonResult<ContractTemplateRespVO> getontractTemplate(@RequestParam("id") Long id) {
        ContractTemplateDO contractTemplate = ontractTemplateService.getontractTemplate(id);
        return success(ContractTemplateConvert.INSTANCE.convert(contractTemplate));
    }

    @GetMapping("/list")
    @Operation(summary = "获得合同表单模板列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('c:ontract-template:query')")
    public CommonResult<List<ContractTemplateRespVO>> getContractTemplateList(@RequestParam("ids") Collection<Long> ids) {
        List<ContractTemplateDO> list = ontractTemplateService.getontractTemplateList(ids);
        return success(ContractTemplateConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得合同表单模板分页")
    @PreAuthorize("@ss.hasPermission('c:ontract-template:query')")
    public CommonResult<PageResult<ContractTemplateRespVO>> getontractTemplatePage(@Valid ContractTemplatePageReqVO pageVO) {
        PageResult<ContractTemplateDO> pageResult = ontractTemplateService.getontractTemplatePage(pageVO);
        return success(ContractTemplateConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出合同表单模板 Excel")
    @PreAuthorize("@ss.hasPermission('c:ontract-template:export')")
    @OperateLog(type = EXPORT)
    public void exportContractTemplateExcel(@Valid ContractTemplateExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ContractTemplateDO> list = ontractTemplateService.getontractTemplateList(exportReqVO);
        // 导出 Excel
        List<ContractTemplateExcelVO> datas = ContractTemplateConvert.INSTANCE.convertList02(list);
        // 获得拼接需要的数据
        Collection<Long> postIds = convertList(list, ContractTemplateDO::getPostId);
        Map<Long, PostDO> postMap = postService.getPostMap(postIds);

        Collection<Long> deptIds = convertList(list, ContractTemplateDO::getDeptId);
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
        ExcelUtils.write(response, "合同表单模板.xls", "数据", ContractTemplateExcelVO.class, datas);
    }
    @GetMapping("/getAttach")
    @Operation(summary = "获得合同附件")
    @PreAuthorize("@ss.hasPermission('c:ontract-template:query')")
    public CommonResult<String> generatorContractAttach(@RequestParam("id") Long id) {
        ContractTemplateDO contractTemplateDO = ontractTemplateService.getontractTemplate(id);
        return success(utilService.attachGenerator(ContractConvert.INSTANCE.convert(contractTemplateDO)));
    }
}
