package cn.iocoder.yudao.module.c.service.performreport;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.module.c.dal.mysql.performreport.PerformReportMapper;
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
* {@link PerformReportServiceImpl} 的单元测试类
*
* @author jack
*/
@Import(PerformReportServiceImpl.class)
public class PerformReportServiceImplTest extends BaseDbUnitTest {

    @Resource
    private PerformReportServiceImpl performReportService;

    @Resource
    private PerformReportMapper performReportMapper;

    @Test
    public void testCreatePerformReport_success() {
        // 准备参数
        PerformReportCreateReqVO reqVO = randomPojo(PerformReportCreateReqVO.class);

        // 调用
        Long performReportId = performReportService.createPerformReport(reqVO);
        // 断言
        assertNotNull(performReportId);
        // 校验记录的属性是否正确
        PerformReportDO performReport = performReportMapper.selectById(performReportId);
        assertPojoEquals(reqVO, performReport);
    }

    @Test
    public void testUpdatePerformReport_success() {
        // mock 数据
        PerformReportDO dbPerformReport = randomPojo(PerformReportDO.class);
        performReportMapper.insert(dbPerformReport);// @Sql: 先插入出一条存在的数据
        // 准备参数
        PerformReportUpdateReqVO reqVO = randomPojo(PerformReportUpdateReqVO.class, o -> {
            o.setId(dbPerformReport.getId()); // 设置更新的 ID
        });

        // 调用
        performReportService.updatePerformReport(reqVO);
        // 校验是否更新正确
        PerformReportDO performReport = performReportMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, performReport);
    }

    @Test
    public void testUpdatePerformReport_notExists() {
        // 准备参数
        PerformReportUpdateReqVO reqVO = randomPojo(PerformReportUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> performReportService.updatePerformReport(reqVO), PERFORM_REPORT_NOT_EXISTS);
    }

    @Test
    public void testDeletePerformReport_success() {
        // mock 数据
        PerformReportDO dbPerformReport = randomPojo(PerformReportDO.class);
        performReportMapper.insert(dbPerformReport);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbPerformReport.getId();

        // 调用
        performReportService.deletePerformReport(id);
       // 校验数据不存在了
       assertNull(performReportMapper.selectById(id));
    }

    @Test
    public void testDeletePerformReport_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> performReportService.deletePerformReport(id), PERFORM_REPORT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPerformReportPage() {
       // mock 数据
       PerformReportDO dbPerformReport = randomPojo(PerformReportDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setContractId(null);
           o.setPostId(null);
           o.setBpmProcessInstanceExtId(null);
           o.setBpmProcessDefinitionId(null);
           o.setCreateTime(null);
           o.setProcessInstanceId(null);
       });
       performReportMapper.insert(dbPerformReport);
       // 测试 userId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setUserId(null)));
       // 测试 contractId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setContractId(null)));
       // 测试 postId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setPostId(null)));
       // 测试 bpmProcessInstanceExtId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setBpmProcessInstanceExtId(null)));
       // 测试 bpmProcessDefinitionId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setBpmProcessDefinitionId(null)));
       // 测试 createTime 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setCreateTime(null)));
       // 测试 processInstanceId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setProcessInstanceId(null)));
       // 准备参数
       PerformReportPageReqVO reqVO = new PerformReportPageReqVO();
       reqVO.setUserId(null);
       reqVO.setContractId(null);
       reqVO.setPostId(null);
       reqVO.setBpmProcessDefinitionId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setProcessInstanceId(null);

       // 调用
       PageResult<PerformReportDO> pageResult = performReportService.getPerformReportPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbPerformReport, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPerformReportList() {
       // mock 数据
       PerformReportDO dbPerformReport = randomPojo(PerformReportDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setContractId(null);
           o.setPostId(null);
           o.setBpmProcessInstanceExtId(null);
           o.setBpmProcessDefinitionId(null);
           o.setCreateTime(null);
           o.setProcessInstanceId(null);
       });
       performReportMapper.insert(dbPerformReport);
       // 测试 userId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setUserId(null)));
       // 测试 contractId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setContractId(null)));
       // 测试 postId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setPostId(null)));
       // 测试 bpmProcessInstanceExtId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setBpmProcessInstanceExtId(null)));
       // 测试 bpmProcessDefinitionId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setBpmProcessDefinitionId(null)));
       // 测试 createTime 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setCreateTime(null)));
       // 测试 processInstanceId 不匹配
       performReportMapper.insert(cloneIgnoreId(dbPerformReport, o -> o.setProcessInstanceId(null)));
       // 准备参数
       PerformReportExportReqVO reqVO = new PerformReportExportReqVO();
       reqVO.setUserId(null);
       reqVO.setContractId(null);
       reqVO.setPostId(null);
       reqVO.setBpmProcessDefinitionId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setProcessInstanceId(null);

       // 调用
       List<PerformReportDO> list = performReportService.getPerformReportList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbPerformReport, list.get(0));
    }

}
