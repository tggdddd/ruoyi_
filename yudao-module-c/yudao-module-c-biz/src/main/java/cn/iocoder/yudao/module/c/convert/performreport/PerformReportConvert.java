package cn.iocoder.yudao.module.c.convert.performreport;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.c.Job.VO;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestCreateReqVO;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;

import javax.validation.Valid;

/**
 * 业绩信息 Convert
 *
 * @author jack
 */
@Mapper
public interface PerformReportConvert {

    PerformReportConvert INSTANCE = Mappers.getMapper(PerformReportConvert.class);

    PerformReportDO convert(PerformReportCreateReqVO bean);

    PerformReportDO convert(PerformReportUpdateReqVO bean);

    @Valid PerformReportCreateReqVO convert(PerformReportDO bean);

    List<PerformReportRespVO> convertList(List<PerformReportDO> list);

    PageResult<PerformReportRespVO> convertPage(PageResult<PerformReportDO> page);

    List<PerformReportExcelVO> convertList02(List<PerformReportDO> list);
    @Mapping(source = "processDefitionId", target = "bpmProcessDefinitionId")
    PerformReportDO convet(PerformanceReportRequestCreateReqVO createReqVO);
    @Mapping(source = "processDefitionId", target = "bpmProcessDefinitionId")
    PerformReportDO covert(PerformanceReportRequestUpdateReqVO updateReqVO);

    PerformReportRespVO convert1(PerformReportDO performReport);


}
