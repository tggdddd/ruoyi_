package cn.iocoder.yudao.module.c.dal.redis;

import cn.iocoder.yudao.framework.redis.core.RedisKeyDefine;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;

import java.time.Duration;

import static cn.iocoder.yudao.framework.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

/**
 * System Redis Key 枚举类
 *
 * @author 芋道源码
 */
public interface RedisKeyConstants {

    RedisKeyDefine PERFORM_REQUIRE = new RedisKeyDefine("业绩设定的缓存",
            "perform_require:%s", // 参数为 uuid
            STRING, PerformanceReportRequestDO.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);
}
