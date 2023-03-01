package cn.iocoder.yudao.module.bpm.framework.flowable.core.behavior.script.impl;

import cn.iocoder.yudao.framework.common.util.collection.SetUtils;
import cn.iocoder.yudao.module.bpm.enums.definition.BpmTaskRuleScriptEnum;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.behavior.script.BpmTaskAssignScript;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.bpm.enums.ErrorCodeConstants.USER_IS_NOT_EXISTS;

/**
 * @ClassName BpmTaskAssignByUserId
 * @Description
 * @Author 15014
 * @Time 2023/2/25 12:23
 * @Version 1.0
 */
@Component
public class BpmTaskAssignByUserId implements BpmTaskAssignScript {

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {
        Long userId = (Long) execution.getVariable("userId");
        if(userId == null || adminUserApi.getUser(userId) == null){
            throw exception(USER_IS_NOT_EXISTS);
        }
        return SetUtils.asSet(userId);
    }

    @Override
    public BpmTaskRuleScriptEnum getEnum() {
        return BpmTaskRuleScriptEnum.USER_ID;
    }

}
