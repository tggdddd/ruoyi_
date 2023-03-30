package cn.iocoder.yudao.module.c.service;

import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.c.Util.ContractGenerator;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AppInfoRespVO;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachBean;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachReqVO;
import cn.iocoder.yudao.module.c.convert.AttachConvert;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.module.c.dal.mysql.contract.ContractMapper;
import cn.iocoder.yudao.module.c.dal.mysql.performreport.PerformReportMapper;
import cn.iocoder.yudao.module.c.enums.ContractStatusConstant;
import cn.iocoder.yudao.module.c.enums.dal.ReportEnum;
import cn.iocoder.yudao.module.system.api.dept.PostApi;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import liquibase.pro.packaged.P;
import liquibase.pro.packaged.R;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UtilService
 * @Description
 * @Author 15014
 * @Time 2023/2/20 16:02
 * @Version 1.0
 */
@Service
public class UtilService {
    @Resource
    PostApi postApi;
    @Resource
    ContractMapper contractMapper;
    @Resource
    PerformReportMapper performReportMapper;

    @Resource
    BpmProcessInstanceApi bpmProcessInstanceApi;


    public String attachGenerator(AttachReqVO attachReqVO){
        AttachBean attachBean = AttachConvert.INSTANCE.convert(attachReqVO);
        if(attachReqVO.getPostId()!=null){
            attachBean.setPost(postApi.getPostName(attachReqVO.getPostId()));
        }
        return ContractGenerator.generator(attachBean);
    }

    /** 获得首页用户显示的信息
     * @param loginUserId 用户id
     * @return 信息*/
    public AppInfoRespVO getAppInfo(Long loginUserId) {
        AppInfoRespVO appInfoRespVO = new AppInfoRespVO();
        Long signed = contractMapper.selectCount(new LambdaQueryWrapper<ContractDO>()
                .eq(ContractDO::getUserId, loginUserId)
                .ne(ContractDO::getStatus, ContractStatusConstant.UNSIGNED)
                .eq(ContractDO::getResult, BpmProcessInstanceResultEnum.APPROVE.getResult())
        );
        Long todoTaskCount = bpmProcessInstanceApi.getTodoTaskCount(loginUserId);

        Long commited = performReportMapper.selectCount(new LambdaQueryWrapper<PerformReportDO>()
                .eq(PerformReportDO::getUserId, loginUserId)
                .eq(PerformReportDO::getStatus, ReportEnum.SUBMIT));
        appInfoRespVO.setSigned(signed);
        appInfoRespVO.setTodo(todoTaskCount);
        appInfoRespVO.setCommited(commited);
        return appInfoRespVO;
    }

}
