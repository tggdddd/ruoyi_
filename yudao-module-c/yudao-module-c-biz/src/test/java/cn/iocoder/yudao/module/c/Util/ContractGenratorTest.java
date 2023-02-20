package cn.iocoder.yudao.module.c.Util;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachBean;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachReqVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @ClassName ContractGenratorTest
 * @Description
 * @Author 15014
 * @Time 2023/2/18 23:57
 * @Version 1.0
 */
class ContractGenratorTest {

    @Test
    public void testGenerator() {
        assertThrows(NullPointerException.class, () -> ContractGenerator.generator(null));
        AttachBean attachReqVO = new AttachBean();
        assertThrows(NullPointerException.class, () -> ContractGenerator.generator(attachReqVO));
        attachReqVO.setAttach("hello {姓名}");
        assertEquals("hello <font color='red'>姓名未赋值</font>",ContractGenerator.generator(attachReqVO));
        attachReqVO.setName("张三");
        // 正确输入
        assertEquals("hello 张三", ContractGenerator.generator(attachReqVO));
        attachReqVO.setAttach("{{ff}");
        assertThrows(ServiceException.class, () -> ContractGenerator.generator(attachReqVO));
        attachReqVO.setAttach("{{f}f}");
        assertThrows(ServiceException.class, () -> ContractGenerator.generator(attachReqVO));
        attachReqVO.setAttach("{f}f}");
        assertThrows(ServiceException.class, () -> ContractGenerator.generator(attachReqVO));

    }
}
