package cn.iocoder.yudao.module.c.Job;

import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.quartz.core.scheduler.SchedulerManager;
import cn.iocoder.yudao.framework.quartz.core.util.CronUtils;
import cn.iocoder.yudao.framework.web.config.WebProperties;
import cn.iocoder.yudao.module.c.Util.StringUtil;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.module.c.dal.mysql.PerformanceReportRequest.PerformanceReportRequestMapper;
import cn.iocoder.yudao.module.c.dal.mysql.contract.ContractMapper;
import cn.iocoder.yudao.module.c.dal.mysql.performreport.PerformReportMapper;
import cn.iocoder.yudao.module.c.enums.ReportJobMessageEnum;
import cn.iocoder.yudao.module.c.enums.dal.ReportEnum;
import cn.iocoder.yudao.module.c.service.contract.ContractService;
import cn.iocoder.yudao.module.system.api.sms.SmsSendApi;
import cn.iocoder.yudao.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.PERFORM_REPORT_JOB_KEY_ERROR;
import static cn.iocoder.yudao.module.c.enums.JobNameConstant.REPORT_END;
import static cn.iocoder.yudao.module.c.enums.JobNameConstant.REPORT_JOB;
import static cn.iocoder.yudao.module.c.enums.JobNameConstant.REPORT_NOTIFY;
import static cn.iocoder.yudao.module.c.enums.JobNameConstant.REPORT_START;
import static cn.iocoder.yudao.module.c.enums.JobNameConstant.REPORT_URGE;

/**
 * @ClassName ReportJob
 * @Description
 * @Author 15014
 * @Time 2023/3/3 14:08
 * @Version 1.0
 */
@Component(REPORT_JOB)
public class ReportJob implements JobHandler {
    @Resource
    PerformReportMapper performReportMapper;
    @Resource
    SchedulerManager schedulerManager;
@Resource
    ContractService contractService;
    @Resource
    private SmsSendApi smsSendApi;

    @Resource
    private WebProperties webProperties;

    @Resource
    private PerformanceReportRequestMapper performanceReportRequestMapper;

    public void sendMessage(SmsSendSingleToUserReqDTO reqDTO) {
        smsSendApi.sendSingleSmsToAdmin(reqDTO);
    }


    private boolean cancelNotExist(Long id,String triggerKey){
        // 取消已取消的定时器
        if (performanceReportRequestMapper.selectById(id) == null) {
            try {
                schedulerManager.deleteTrigger(triggerKey);
                return true;
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
    private String getDetail(String processInstanceId){
        return webProperties.getAdminUi().getUrl() +
                "/process-instance/detail?id=" +
                processInstanceId;

    }
    private String getReportSubmit(String reportId,String processDefinedId){
        return webProperties.getAdminUi().getUrl() +
                "/report/process-instance/create?" +
                "reportId="+ reportId+
                "&key=" + processDefinedId;
    }
    private String getList(){
        return webProperties.getAdminUi().getUrl() +
                "/perform/perform-report";
    }
    @Override
    @Transactional
    public String execute(String param) throws Exception {
        VO vo = JsonUtils.parseObject(param, VO.class);
        switch (Objects.requireNonNull(vo).getTriggerKey().substring(0,2)){
            case REPORT_START:
                return createReport(vo);
            case REPORT_NOTIFY:
                return notifyReport(vo);
            case REPORT_URGE:
                return urgeReport(vo);
            case REPORT_END:
                return expireReport(vo);
            default:
                throw exception(PERFORM_REPORT_JOB_KEY_ERROR);
        }
    }

    private String expireReport(VO vo) {
        PerformReportDO performReportDO = performReportMapper.selectById(vo.getReportId());
        if(performReportDO != null && performReportDO.getProcessInstanceId() == null){
            // 取消已取消的定时器
            if(cancelNotExist(vo.getId(),vo.getTriggerKey())){
                return vo.getId()+"不存在，已取消定时器。"+vo.getTriggerKey();
            }
            // 设置状态
            performReportMapper.updateById(performReportDO.setStatus(ReportEnum.EXPIRE.getType()));
            // 发送通知
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("detailUrl", getList());
            templateParams.put("startTime",performReportDO.getCreateTime().toString());
            templateParams.put("endTime",performReportDO.getExpireTime().toString());
            SmsSendSingleToUserReqDTO smsSendSingleToUserReqDTO = new SmsSendSingleToUserReqDTO()
                    .setUserId(vo.getUserId())
                    .setTemplateCode(ReportJobMessageEnum.EXPIRE.getSmsTemplateCode())
                    .setTemplateParams(templateParams);
            sendMessage(smsSendSingleToUserReqDTO);
        }
        try {
            schedulerManager.deleteTrigger(vo.getTriggerKey());
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return vo.getId()+"业绩提交超时："+vo.getReportId();
    }

    private String urgeReport(VO vo) {
        PerformReportDO performReportDO = performReportMapper.selectById(vo.getReportId());
        if(performReportDO != null && performReportDO.getProcessInstanceId() == null){
            // 取消已取消的定时器
            if(cancelNotExist(vo.getId(),vo.getTriggerKey())){
                return vo.getId()+"不存在，已取消定时器。"+vo.getTriggerKey();
            }
            // 发送通知
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("detailUrl", getReportSubmit(String.valueOf(vo.getReportId()),performReportDO.getBpmProcessDefinitionId()));
            templateParams.put("startTime",performReportDO.getCreateTime().toString());
            templateParams.put("endTime",performReportDO.getExpireTime().toString());
            SmsSendSingleToUserReqDTO smsSendSingleToUserReqDTO = new SmsSendSingleToUserReqDTO()
                    .setUserId(vo.getUserId())
                    .setTemplateCode(ReportJobMessageEnum.URGE.getSmsTemplateCode())
                    .setTemplateParams(templateParams);
            sendMessage(smsSendSingleToUserReqDTO);
        }else {
            try {
                schedulerManager.deleteTrigger(vo.getTriggerKey());
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }
        return vo.getId()+"发送业绩提交提醒："+vo.getReportId();
    }

    private String notifyReport(VO vo) {
        PerformReportDO performReportDO = performReportMapper.selectById(vo.getReportId());
        if(performReportDO != null && performReportDO.getProcessInstanceId() == null){
            // 取消已取消的定时器
            if(cancelNotExist(vo.getId(),vo.getTriggerKey())){
                return vo.getId()+"不存在，已取消定时器。"+vo.getTriggerKey();
            }
            // 发送通知
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("detailUrl", getReportSubmit(String.valueOf(vo.getReportId()),performReportDO.getBpmProcessDefinitionId()));
            templateParams.put("startTime",performReportDO.getCreateTime().toString());
            templateParams.put("endTime",performReportDO.getExpireTime().toString());
            SmsSendSingleToUserReqDTO smsSendSingleToUserReqDTO = new SmsSendSingleToUserReqDTO()
                    .setUserId(vo.getUserId())
                    .setTemplateCode(ReportJobMessageEnum.NOTIFY.getSmsTemplateCode())
                    .setTemplateParams(templateParams);
            sendMessage(smsSendSingleToUserReqDTO);
        }else {
            try {
                schedulerManager.deleteTrigger(vo.getTriggerKey());
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }
        return vo.getId()+"发送业绩提交通知："+vo.getReportId();
    }

    private String createReport(VO vo){
        // 取消已取消的定时器        防止混淆
        if(cancelNotExist(vo.getId(),vo.getTriggerKey())){
            return vo.getId()+"不存在，已取消定时器。"+vo.getTriggerKey();
        }
        // 只对已生效的合同创建
        if (!contractService.checkIsStart(vo.getContractId())) {
            return  vo.getId()+"的合同未到生效时间";
        }
        PerformReportDO performReportDO = new PerformReportDO().setBpmProcessDefinitionId(vo.getProcessDefitionId())
                        .setUserId(vo.getUserId())
                                .setPostId(vo.getPostId())
                                        .setContractId(vo.getContractId())
                                                .setExpireTime(CronUtils.getNextTimeOrNull(vo.getEndTime()));
        //     新增报告
        performReportMapper.insert(performReportDO);
        // 获得结果id
        vo.setReportId(performReportDO.getId());
        try {
            //设置通知定时器
            if (StringUtil.notEmpty(vo.getNotifyTime())){
                vo.setTriggerKey(REPORT_NOTIFY+vo.getReportId());
                schedulerManager.addJob(vo.getId(),
                        REPORT_JOB,
                        JsonUtils.toJsonString(vo),
                        vo.getNotifyTime(),
                        3,
                        10,
                        vo.getTriggerKey(),vo.getTriggerKey());
            }
            // 设置提醒定时器
            if (StringUtil.notEmpty(vo.getUrgeTime())) {
                    vo.setTriggerKey(REPORT_URGE+vo.getReportId());
                    schedulerManager.addJob(vo.getId(),
                            REPORT_JOB,
                            JsonUtils.toJsonString(vo),
                            vo.getUrgeTime(),
                            3,
                            10,
                            vo.getTriggerKey(),vo.getTriggerKey());
                }
            // // 设置终止定时器
            if (StringUtil.notEmpty(vo.getEndTime())) {
                vo.setTriggerKey(REPORT_END+vo.getReportId());
                schedulerManager.addJob(vo.getId(),
                        REPORT_JOB,
                        JsonUtils.toJsonString(vo),
                        vo.getEndTime(),
                        3,
                        10,
                        vo.getTriggerKey(),vo.getTriggerKey());
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return vo.getId()+"创建业绩提交要求："+vo.getReportId();
    }
}
