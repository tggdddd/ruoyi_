package cn.iocoder.yudao.module.c.enums.dal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ContractStatus
 * @Description
 * @Author 15014
 * @Time 2023/2/24 17:53
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum ContractStatus {
    // 合同状态 0未签订 1签订 2到期 3终止 枚举
    UNSIGNED(0),
    SIGNED(1),
    EXPIRE(2),
    END(3);
    private final Integer code;
}

