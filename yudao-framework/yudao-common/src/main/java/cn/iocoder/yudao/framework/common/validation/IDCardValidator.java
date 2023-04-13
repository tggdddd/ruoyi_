package cn.iocoder.yudao.framework.common.validation;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName IDCardValidator
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/13 10:34
 * @Version 1.0
 */
public class IDCardValidator implements ConstraintValidator<IDCard, String> {

    @Override
    public void initialize(IDCard annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果为空，默认不校验，即校验通过
        if (StrUtil.isEmpty(value)) {
            return true;
        }
        // 校验手机
        return isMatch(value);
    }
    private static final Pattern PATTERN = Pattern.compile("\\d{17}[\\d|x|X]");

    private boolean isMatch(String text) {
        return StringUtils.hasText(text)
                && PATTERN.matcher(text).matches();
    }
}