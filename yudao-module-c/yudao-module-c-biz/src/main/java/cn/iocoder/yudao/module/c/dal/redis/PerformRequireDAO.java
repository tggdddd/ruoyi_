package cn.iocoder.yudao.module.c.dal.redis;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.iocoder.yudao.module.c.dal.redis.RedisKeyConstants.PERFORM_REQUIRE;

/**
 * {@link PerformReportDO} 的 RedisDAO
 *
 * @author 芋道源码
 */
@Repository
public class PerformRequireDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public PerformReportDO get(Long id) {
        String redisKey = formatKey(id);
        return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(redisKey), PerformReportDO.class);
    }

    public void set(PerformReportDO performReportDO) {
        String redisKey = formatKey(performReportDO.getId());
        stringRedisTemplate.opsForValue().set(redisKey, JsonUtils.toJsonString(performReportDO));
    }

    public void delete(Long id) {
        String redisKey = formatKey(id);
        stringRedisTemplate.delete(redisKey);
    }

    public void deleteList(Collection<String> accessTokens) {
        List<String> redisKeys = CollectionUtils.convertList(accessTokens, PerformRequireDAO::formatKey);
        stringRedisTemplate.delete(redisKeys);
    }

    private static String formatKey(Object id) {
        return String.format(PERFORM_REQUIRE.getKeyTemplate(), id);
    }

}
