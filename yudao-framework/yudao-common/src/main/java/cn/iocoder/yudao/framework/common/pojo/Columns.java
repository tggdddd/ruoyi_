package cn.iocoder.yudao.framework.common.pojo;

import cn.iocoder.yudao.framework.common.core.ColumnName;
import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.exception.ServerException;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @ClassName Columns
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/24 12:48
 * @Version 1.0
 */

/**
 * 通用返回列信息
 *
 * @param <T> 数据泛型
 */
@Data
public class Columns implements Serializable {
    /**
     * 保留 ， 未定义用途
     */
    private Integer key;
    /**
     * 列名
     */
    private String filed;
    /**
     * 列标签
     */
    private String label;

    /**
     * 是否显示：前端改动 */
    private boolean visible = true;
}
