package cn.iocoder.yudao.module.c.convert;

import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.PerformanceReportRequestCreateReqVO;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachBean;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachReqVO;
import cn.iocoder.yudao.module.c.convert.PerformanceReportRequest.PerformanceReportRequestConvert;
import liquibase.pro.packaged.M;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.validation.Valid;

/**
 * @ClassName AttachConvert
 * @Description
 * @Author 15014
 * @Time 2023/2/20 16:07
 * @Version 1.0
 */
@Mapper
public interface AttachConvert {
    AttachConvert INSTANCE = Mappers.getMapper(AttachConvert.class);

    AttachBean convert(AttachReqVO attachReqVO);

}
