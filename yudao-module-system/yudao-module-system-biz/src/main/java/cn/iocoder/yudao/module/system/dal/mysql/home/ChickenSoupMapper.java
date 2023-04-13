package cn.iocoder.yudao.module.system.dal.mysql.home;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.UserPostDO;
import cn.iocoder.yudao.module.system.dal.dataobject.home.ChickenSoupDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName chickenSoupMapper
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/15 9:02
 * @Version 1.0
 */
@Mapper
public interface ChickenSoupMapper  {

    public String getRandomContent();
    public String getContentById(Long id);
}
