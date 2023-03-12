package cn.iocoder.yudao.module.bpm.enums.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName BpmModelCategoryEnum
 * @Description
 * @Author 15014
 * @Time 2023/3/2 9:02
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum BpmModelCategoryEnum {
    CONTRACT("3", "合同"),
    PERFORM("2", "业绩"),
    CONTRACT_TEMPLATE("1", "合同模板")
    ;

    private final String type;
    private final String desc;
}
