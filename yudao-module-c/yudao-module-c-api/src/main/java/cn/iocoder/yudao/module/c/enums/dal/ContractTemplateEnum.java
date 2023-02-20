package cn.iocoder.yudao.module.c.enums.dal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ContractTemplateEnum
 * @Description
 * @Author 15014
 * @Time 2023/2/18 0:07
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum ContractTemplateEnum {
    Enable(1,"启用"),
    Disable(0,"禁用");
    private final Integer code;
    private final String desc;
}
