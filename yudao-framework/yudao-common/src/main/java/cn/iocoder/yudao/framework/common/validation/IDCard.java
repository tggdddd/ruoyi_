package cn.iocoder.yudao.framework.common.validation;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * @ClassName Validation
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/13 10:33
 * @Version 1.0
 */
@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = IDCardValidator.class // 设置校验器
)
public @interface  IDCard {
    String message() default "身份证号码格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
