package cn.iocoder.yudao.framework.common.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName ColumnName
 * @Description 字段注解，声明显示名称
 * @Author 肖润杰
 * @Time 2023/3/24 12:53
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnName {
    /**
     * 显示名称 */
    String value();
    /** 是否可见  */
    boolean visible() default true;
}
