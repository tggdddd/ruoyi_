package cn.iocoder.yudao.module.c.service.contract;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.c.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.c.dal.mysql.contract.ContractMapper;
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
* {@link ContractServiceImpl} 的单元测试类
*
* @author jack
*/
@Import(ContractServiceImpl.class)
public class ContractServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ContractServiceImpl ontractService;

    @Resource
    private ContractMapper ontractMapper;

    @Test
    public void testCreateontract_success() {
        // 准备参数
        ContractCreateReqVO reqVO = randomPojo(ContractCreateReqVO.class);

        // 调用
        Long ontractId = ontractService.createontract(reqVO);
        // 断言
        assertNotNull(ontractId);
        // 校验记录的属性是否正确
        ContractDO ontract = ontractMapper.selectById(ontractId);
        assertPojoEquals(reqVO, ontract);
    }

    @Test
    public void testUpdateontract_success() {
        // mock 数据
        ContractDO dbontract = randomPojo(ContractDO.class);
        ontractMapper.insert(dbontract);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ContractUpdateReqVO reqVO = randomPojo(ContractUpdateReqVO.class, o -> {
            o.setId(dbontract.getId()); // 设置更新的 ID
        });

        // 调用
        ontractService.updateontract(reqVO);
        // 校验是否更新正确
        ContractDO ontract = ontractMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, ontract);
    }

    @Test
    public void testUpdateontract_notExists() {
        // 准备参数
        ContractUpdateReqVO reqVO = randomPojo(ContractUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> ontractService.updateontract(reqVO), ONTRACT_NOT_EXISTS);
    }

    @Test
    public void testDeleteontract_success() {
        // mock 数据
        ContractDO dbontract = randomPojo(ContractDO.class);
        ontractMapper.insert(dbontract);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbontract.getId();

        // 调用
        ontractService.deleteontract(id);
       // 校验数据不存在了
       assertNull(ontractMapper.selectById(id));
    }

    @Test
    public void testDeleteontract_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> ontractService.deleteontract(id), ONTRACT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetontractPage() {
       // mock 数据
       ContractDO dbontract = randomPojo(ContractDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setName(null);
           o.setIdentityCard(null);
           o.setSalary(null);
           o.setAttach(null);
           o.setPerformanceRequirements(null);
           o.setDefaultClause(null);
           o.setStatus(null);
           o.setFirstParty(null);
           o.setSignedTime(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setCreateTime(null);
       });
       ontractMapper.insert(dbontract);
       // 测试 userId 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setUserId(null)));
       // 测试 name 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setName(null)));
       // 测试 identityCard 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setIdentityCard(null)));
       // 测试 salary 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setSalary(null)));
       // 测试 attach 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setAttach(null)));
       // 测试 performanceRequirements 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setPerformanceRequirements(null)));
       // 测试 defaultClause 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setDefaultClause(null)));
       // 测试 status 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setStatus(null)));
       // 测试 firstParty 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setFirstParty(null)));
       // 测试 signedTime 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setSignedTime(null)));
       // 测试 startTime 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setEndTime(null)));
       // 测试 createTime 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setCreateTime(null)));
       // 准备参数
       ContractPageReqVO reqVO = new ContractPageReqVO();
       reqVO.setUserId(null);
       reqVO.setName(null);
       reqVO.setIdentityCard(null);
       reqVO.setSalary(null);
       reqVO.setAttach(null);
       reqVO.setPerformanceRequirements(null);
       reqVO.setDefaultClause(null);
       reqVO.setStatus(null);
       reqVO.setFirstParty(null);
       reqVO.setSignedTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ContractDO> pageResult = ontractService.getontractPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbontract, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetontractList() {
       // mock 数据
       ContractDO dbontract = randomPojo(ContractDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setName(null);
           o.setIdentityCard(null);
           o.setSalary(null);
           o.setAttach(null);
           o.setPerformanceRequirements(null);
           o.setDefaultClause(null);
           o.setStatus(null);
           o.setFirstParty(null);
           o.setSignedTime(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setCreateTime(null);
       });
       ontractMapper.insert(dbontract);
       // 测试 userId 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setUserId(null)));
       // 测试 name 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setName(null)));
       // 测试 identityCard 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setIdentityCard(null)));
       // 测试 salary 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setSalary(null)));
       // 测试 attach 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setAttach(null)));
       // 测试 performanceRequirements 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setPerformanceRequirements(null)));
       // 测试 defaultClause 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setDefaultClause(null)));
       // 测试 status 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setStatus(null)));
       // 测试 firstParty 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setFirstParty(null)));
       // 测试 signedTime 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setSignedTime(null)));
       // 测试 startTime 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setEndTime(null)));
       // 测试 createTime 不匹配
       ontractMapper.insert(cloneIgnoreId(dbontract, o -> o.setCreateTime(null)));
       // 准备参数
       ContractExportReqVO reqVO = new ContractExportReqVO();
       reqVO.setUserId(null);
       reqVO.setName(null);
       reqVO.setIdentityCard(null);
       reqVO.setSalary(null);
       reqVO.setAttach(null);
       reqVO.setPerformanceRequirements(null);
       reqVO.setDefaultClause(null);
       reqVO.setStatus(null);
       reqVO.setFirstParty(null);
       reqVO.setSignedTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<ContractDO> list = ontractService.getontractList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbontract, list.get(0));
    }

}
