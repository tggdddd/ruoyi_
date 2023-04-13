package cn.iocoder.yudao.module.system.service.home;

import cn.iocoder.yudao.module.system.dal.mysql.home.ChickenSoupMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName chickenSoupService
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/15 9:01
 * @Version 1.0
 */
@Service
public class ChickenSoupService {
    @Resource
    ChickenSoupMapper chickenSoupMapper;

    public String getRandom(Long id){
        if (id != null) {
            return chickenSoupMapper.getContentById(id);
        }
        return chickenSoupMapper.getRandomContent();
    }
}
