package cn.iocoder.yudao.module.c.controller.admin.performreport.vo;

import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCreateReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName ReportBpmProcessInstanceCreateReqVO
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/11 23:05
 * @Version 1.0
 */
@Data
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportBpmProcessInstanceCreateReqVO extends BpmProcessInstanceCreateReqVO {
    private static final long serialVersionUID = -1242493306307174690L;
    private String reportId;
}
