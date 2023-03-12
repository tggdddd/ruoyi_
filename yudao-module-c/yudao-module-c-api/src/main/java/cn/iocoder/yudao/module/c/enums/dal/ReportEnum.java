package cn.iocoder.yudao.module.c.enums.dal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName ReportEnum
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/11 20:09
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum ReportEnum {
    Create(0,"待提交"),
    SUBMIT(1,"已提交"),
    REJECT(2,"不通过"),
    COMPLETION(3,"已完成"),
    EXPIRE(4,"已超时");
    private final Integer type;
    private final String desc;

}
