package cn.iocoder.yudao.module.c.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * c 错误码枚举类
 * <p>
 * c 模块，使用 1-007-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 合同表单  模块 1007000000 ==========

    ErrorCode CONTRACT_NOT_EXISTS = new ErrorCode(1007000000, "合同表单不存在");

    ErrorCode CONTRACT_ATTACH_PARAM_ERROR = new ErrorCode(1007000001,"合同附件参数：{} 不存在,\n您是否想用：{}");
    ErrorCode CONTRACT_ATTACH_GRAMMAR_ERROR = new ErrorCode(1007000002,"合同附件参数语法{}错误");

    // ========== 合同表单模板  模块 1007001000 ==========

    ErrorCode CONTRACT_TEMPLATE_NOT_EXISTS = new ErrorCode(1007001000, "合同表单模板不存在");

    // ========== 业绩定义  模块 1007002000 ==========

    ErrorCode PERFORMANCE_REPORT_REQUEST_NOT_EXISTS = new ErrorCode(1007002000, "业绩定义不存在");
    ErrorCode PERFORM_REPORT_NOT_EXISTS = new ErrorCode(1007002001, "业绩信息不存在");

    // 定时任务
    ErrorCode PERFORM_REPORT_JOB_KEY_ERROR = new ErrorCode(1007003001, "定时任务触发器key错误");

    ErrorCode PERFORM_REPORT_TIME_TOO_SHORT = new ErrorCode(1007003002, "触发时间过于短暂，您是否设置错了");
    ErrorCode PERFORM_REPORT_REPEAT_SUBMIT = new ErrorCode(1007003003, "请不要重复提交！");

}
