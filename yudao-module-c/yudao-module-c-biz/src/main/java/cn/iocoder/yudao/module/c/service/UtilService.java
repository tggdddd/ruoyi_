package cn.iocoder.yudao.module.c.service;

import cn.iocoder.yudao.module.c.Util.ContractGenerator;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachBean;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachReqVO;
import cn.iocoder.yudao.module.c.convert.AttachConvert;
import cn.iocoder.yudao.module.system.api.dept.PostApi;
import liquibase.pro.packaged.P;
import liquibase.pro.packaged.R;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UtilService
 * @Description
 * @Author 15014
 * @Time 2023/2/20 16:02
 * @Version 1.0
 */
@Service
public class UtilService {
    @Resource
    PostApi postApi;
    public String attachGenerator(AttachReqVO attachReqVO){
        AttachBean attachBean = AttachConvert.INSTANCE.convert(attachReqVO);
        if(attachReqVO.getPostId()!=null){
            attachBean.setPost(postApi.getPostName(attachReqVO.getPostId()));
        }
        return ContractGenerator.generator(attachBean);
    }
}
