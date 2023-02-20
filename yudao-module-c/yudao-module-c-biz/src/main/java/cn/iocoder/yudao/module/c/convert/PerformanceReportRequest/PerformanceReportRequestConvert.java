package cn.iocoder.yudao.module.c.convert.PerformanceReportRequest;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;

/**
 * 业绩定义 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface PerformanceReportRequestConvert {

    PerformanceReportRequestConvert INSTANCE = Mappers.getMapper(PerformanceReportRequestConvert.class);

    PerformanceReportRequestDO convert(PerformanceReportRequestCreateReqVO bean);

    PerformanceReportRequestDO convert(PerformanceReportRequestUpdateReqVO bean);

    PerformanceReportRequestRespVO convert(PerformanceReportRequestDO bean);

    List<PerformanceReportRequestRespVO> convertList(List<PerformanceReportRequestDO> list);

    PageResult<PerformanceReportRequestRespVO> convertPage(PageResult<PerformanceReportRequestDO> page);

    List<PerformanceReportRequestExcelVO> convertList02(List<PerformanceReportRequestDO> list);

}
