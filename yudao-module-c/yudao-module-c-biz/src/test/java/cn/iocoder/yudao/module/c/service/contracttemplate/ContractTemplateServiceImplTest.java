package cn.iocoder.yudao.module.c.service.contracttemplate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.c.controller.admin.contracttemplate.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;
import cn.iocoder.yudao.module.c.dal.mysql.contracttemplate.ContractTemplateMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.buildBetweenTime;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link ContractTemplateServiceImpl} 的单元测试类
*
* @author jack
*/
@Import(ContractTemplateServiceImpl.class)
public class ContractTemplateServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ContractTemplateServiceImpl ontractTemplateService;

    @Resource
    private ContractTemplateMapper ontractTemplateMapper;

    @Test
    public void testCreateontractTemplate_success() {
        // 准备参数
        ContractTemplateCreateReqVO reqVO = randomPojo(ContractTemplateCreateReqVO.class);

        // 调用
        Long ontractTemplateId = ontractTemplateService.createontractTemplate(reqVO);
        // 断言
        assertNotNull(ontractTemplateId);
        // 校验记录的属性是否正确
        ContractTemplateDO ontractTemplate = ontractTemplateMapper.selectById(ontractTemplateId);
        assertPojoEquals(reqVO, ontractTemplate);
    }

    @Test
    public void testUpdateontractTemplate_success() {
        // mock 数据
        ContractTemplateDO dbontractTemplate = randomPojo(ContractTemplateDO.class);
        ontractTemplateMapper.insert(dbontractTemplate);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ContractTemplateUpdateReqVO reqVO = randomPojo(ContractTemplateUpdateReqVO.class, o -> {
            o.setId(dbontractTemplate.getId()); // 设置更新的 ID
        });

        // 调用
        ontractTemplateService.updateontractTemplate(reqVO);
        // 校验是否更新正确
        ContractTemplateDO ontractTemplate = ontractTemplateMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, ontractTemplate);
    }

    @Test
    public void testUpdateontractTemplate_notExists() {
        // 准备参数
        ContractTemplateUpdateReqVO reqVO = randomPojo(ContractTemplateUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> ontractTemplateService.updateontractTemplate(reqVO), CONTRACT_TEMPLATE_NOT_EXISTS);
    }

    @Test
    public void testDeleteontractTemplate_success() {
        // mock 数据
        ContractTemplateDO dbontractTemplate = randomPojo(ContractTemplateDO.class);
        ontractTemplateMapper.insert(dbontractTemplate);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbontractTemplate.getId();

        // 调用
        ontractTemplateService.deleteontractTemplate(id);
       // 校验数据不存在了
       assertNull(ontractTemplateMapper.selectById(id));
    }

    @Test
    public void testDeleteontractTemplate_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> ontractTemplateService.deleteontractTemplate(id), CONTRACT_TEMPLATE_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetontractTemplatePage() {
       // mock 数据
       ContractTemplateDO dbontractTemplate = randomPojo(ContractTemplateDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setIdentityCard(null);
           o.setSalary(null);
           o.setPerformanceRequirements(null);
           o.setDefaultClause(null);
           o.setFirstParty(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setCreateTime(null);
       });
       ontractTemplateMapper.insert(dbontractTemplate);
       // 测试 name 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setName(null)));
       // 测试 identityCard 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setIdentityCard(null)));
       // 测试 salary 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setSalary(null)));
       // 测试 performanceRequirements 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setPerformanceRequirements(null)));
       // 测试 defaultClause 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setDefaultClause(null)));
       // 测试 firstParty 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setFirstParty(null)));
       // 测试 startTime 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setEndTime(null)));
       // 测试 createTime 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setCreateTime(null)));
       // 准备参数
       ContractTemplatePageReqVO reqVO = new ContractTemplatePageReqVO();
       reqVO.setName(null);
       reqVO.setIdentityCard(null);
       reqVO.setSalary(null);
       reqVO.setPerformanceRequirements(null);
       reqVO.setDefaultClause(null);
       reqVO.setFirstParty(null);
       reqVO.setSignedTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ContractTemplateDO> pageResult = ontractTemplateService.getontractTemplatePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbontractTemplate, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetontractTemplateList() {
       // mock 数据
       ContractTemplateDO dbontractTemplate = randomPojo(ContractTemplateDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setIdentityCard(null);
           o.setSalary(null);
           o.setPerformanceRequirements(null);
           o.setDefaultClause(null);
           o.setFirstParty(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setCreateTime(null);
       });
       ontractTemplateMapper.insert(dbontractTemplate);
       // 测试 name 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setName(null)));
       // 测试 identityCard 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setIdentityCard(null)));
       // 测试 salary 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setSalary(null)));
       // 测试 performanceRequirements 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setPerformanceRequirements(null)));
       // 测试 defaultClause 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setDefaultClause(null)));
       // 测试 firstParty 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setFirstParty(null)));
       // 测试 startTime 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setEndTime(null)));
       // 测试 createTime 不匹配
       ontractTemplateMapper.insert(cloneIgnoreId(dbontractTemplate, o -> o.setCreateTime(null)));
       // 准备参数
       ContractTemplateExportReqVO reqVO = new ContractTemplateExportReqVO();
       reqVO.setName(null);
       reqVO.setIdentityCard(null);
       reqVO.setSalary(null);
       reqVO.setPerformanceRequirements(null);
       reqVO.setDefaultClause(null);
       reqVO.setFirstParty(null);
       reqVO.setSignedTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<ContractTemplateDO> list = ontractTemplateService.getontractTemplateList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbontractTemplate, list.get(0));
    }

}
