package cn.iocoder.yudao.module.c.service.PerformanceReportRequest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.c.controller.admin.PerformanceReportRequest.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import cn.iocoder.yudao.module.c.dal.mysql.PerformanceReportRequest.PerformanceReportRequestMapper;
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
* {@link PerformanceReportRequestServiceImpl} 的单元测试类
*
* @author 芋道源码
*/
@Import(PerformanceReportRequestServiceImpl.class)
public class PerformanceReportRequestServiceImplTest extends BaseDbUnitTest {

    @Resource
    private PerformanceReportRequestServiceImpl performanceReportRequestService;

    @Resource
    private PerformanceReportRequestMapper performanceReportRequestMapper;

    @Test
    public void testCreatePerformanceReportRequest_success() {
        // 准备参数
        PerformanceReportRequestCreateReqVO reqVO = randomPojo(PerformanceReportRequestCreateReqVO.class);

        // 调用
        Long performanceReportRequestId = performanceReportRequestService.createPerformanceReportRequest(reqVO);
        // 断言
        assertNotNull(performanceReportRequestId);
        // 校验记录的属性是否正确
        PerformanceReportRequestDO performanceReportRequest = performanceReportRequestMapper.selectById(performanceReportRequestId);
        assertPojoEquals(reqVO, performanceReportRequest);
    }

    @Test
    public void testUpdatePerformanceReportRequest_success() {
        // mock 数据
        PerformanceReportRequestDO dbPerformanceReportRequest = randomPojo(PerformanceReportRequestDO.class);
        performanceReportRequestMapper.insert(dbPerformanceReportRequest);// @Sql: 先插入出一条存在的数据
        // 准备参数
        PerformanceReportRequestUpdateReqVO reqVO = randomPojo(PerformanceReportRequestUpdateReqVO.class, o -> {
            o.setId(dbPerformanceReportRequest.getId()); // 设置更新的 ID
        });

        // 调用
        performanceReportRequestService.updatePerformanceReportRequest(reqVO);
        // 校验是否更新正确
        PerformanceReportRequestDO performanceReportRequest = performanceReportRequestMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, performanceReportRequest);
    }

    @Test
    public void testUpdatePerformanceReportRequest_notExists() {
        // 准备参数
        PerformanceReportRequestUpdateReqVO reqVO = randomPojo(PerformanceReportRequestUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> performanceReportRequestService.updatePerformanceReportRequest(reqVO), PERFORMANCE_REPORT_REQUEST_NOT_EXISTS);
    }

    @Test
    public void testDeletePerformanceReportRequest_success() {
        // mock 数据
        PerformanceReportRequestDO dbPerformanceReportRequest = randomPojo(PerformanceReportRequestDO.class);
        performanceReportRequestMapper.insert(dbPerformanceReportRequest);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbPerformanceReportRequest.getId();

        // 调用
        performanceReportRequestService.deletePerformanceReportRequest(id);
       // 校验数据不存在了
       assertNull(performanceReportRequestMapper.selectById(id));
    }

    @Test
    public void testDeletePerformanceReportRequest_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> performanceReportRequestService.deletePerformanceReportRequest(id), PERFORMANCE_REPORT_REQUEST_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPerformanceReportRequestPage() {
       // mock 数据
       PerformanceReportRequestDO dbPerformanceReportRequest = randomPojo(PerformanceReportRequestDO.class, o -> { // 等会查询到
           o.setContractId(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setNotifyTime(null);
           o.setCreateTime(null);
           o.setProcessDefitionId(null);
           o.setUrgeTime(null);
           o.setUserId(null);
       });
       performanceReportRequestMapper.insert(dbPerformanceReportRequest);
       // 测试 contractId 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setContractId(null)));
       // 测试 startTime 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setEndTime(null)));
       // 测试 notifyTime 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setNotifyTime(null)));
       // 测试 createTime 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setCreateTime(null)));
       // 测试 processDefitionId 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setProcessDefitionId(null)));
       // 测试 urgeTime 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setUrgeTime(null)));
       // 测试 userId 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setUserId(null)));
     // 准备参数
       PerformanceReportRequestPageReqVO reqVO = new PerformanceReportRequestPageReqVO();
       reqVO.setContractId(null);

       reqVO.setProcessDefitionId(null);
      reqVO.setUserId(null);

       // 调用
       PageResult<PerformanceReportRequestDO> pageResult = performanceReportRequestService.getPerformanceReportRequestPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbPerformanceReportRequest, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetPerformanceReportRequestList() {
       // mock 数据
       PerformanceReportRequestDO dbPerformanceReportRequest = randomPojo(PerformanceReportRequestDO.class, o -> { // 等会查询到
           o.setContractId(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setNotifyTime(null);
           o.setCreateTime(null);
           o.setProcessDefitionId(null);
           o.setUrgeTime(null);
           o.setUserId(null);
       });
       performanceReportRequestMapper.insert(dbPerformanceReportRequest);
       // 测试 contractId 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setContractId(null)));
       // 测试 startTime 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setEndTime(null)));
       // 测试 notifyTime 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setNotifyTime(null)));
       // 测试 createTime 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setCreateTime(null)));
       // 测试 processDefitionId 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setProcessDefitionId(null)));
       // 测试 urgeTime 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setUrgeTime(null)));
       // 测试 userId 不匹配
       performanceReportRequestMapper.insert(cloneIgnoreId(dbPerformanceReportRequest, o -> o.setUserId(null)));
       // 准备参数
       PerformanceReportRequestExportReqVO reqVO = new PerformanceReportRequestExportReqVO();
       reqVO.setContractId(null);
       reqVO.setUserId(null);

       // 调用
       List<PerformanceReportRequestDO> list = performanceReportRequestService.getPerformanceReportRequestList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbPerformanceReportRequest, list.get(0));
    }

}
