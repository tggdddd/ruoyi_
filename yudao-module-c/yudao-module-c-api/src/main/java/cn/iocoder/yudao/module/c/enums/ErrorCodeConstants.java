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

    ErrorCode CONTRACT_ATTACH_PARAM_ERROR = new ErrorCode(1007000001,"合同附件参数{}不存在");
    ErrorCode CONTRACT_ATTACH_GRAMMAR_ERROR = new ErrorCode(1007000002,"合同附件参数语法{}错误");
    ErrorCode BPM_FORM_NOT_EXISTS = new ErrorCode(1007000002, "bpm和业务表单之间的关联不存在");

    // ========== 合同表单模板  模块 1007001000 ==========

    ErrorCode CONTRACT_TEMPLATE_NOT_EXISTS = new ErrorCode(1007001000, "合同表单模板不存在");

    // ========== 业绩定义  模块 1007002000 ==========

    ErrorCode PERFORMANCE_REPORT_REQUEST_NOT_EXISTS = new ErrorCode(1007002000, "业绩定义不存在");
}
