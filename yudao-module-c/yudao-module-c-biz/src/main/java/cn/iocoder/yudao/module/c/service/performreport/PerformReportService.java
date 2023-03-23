package cn.iocoder.yudao.module.c.service.performreport;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCreateReqVO;
import cn.iocoder.yudao.module.c.controller.admin.performreport.vo.*;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.apache.commons.collections4.trie.analyzer.StringKeyAnalyzer;

/**
 * 业绩信息 Service 接口
 *
 * @author jack
 */
public interface PerformReportService {

    /**
     * 创建业绩信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPerformReport(@Valid PerformReportCreateReqVO createReqVO);

    /**
     * 更新业绩信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePerformReport(@Valid PerformReportUpdateReqVO updateReqVO);

    /**
     * 删除业绩信息
     *
     * @param id 编号
     */
    void deletePerformReport(Long id);

    /**
     * 获得业绩信息
     *
     * @param id 编号
     * @return 业绩信息
     */
    PerformReportDO getPerformReport(Long id);

    /**
     * 获得业绩信息列表
     *
     * @param ids 编号
     * @return 业绩信息列表
     */
    List<PerformReportDO> getPerformReportList(Collection<Long> ids);

    /**
     * 获得业绩信息分页
     *
     * @param pageReqVO 分页查询
     * @return 业绩信息分页
     */
    PageResult<PerformReportDO> getPerformReportPage(PerformReportPageReqVO pageReqVO);

    /**
     * 获得业绩信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 业绩信息列表
     */
    List<PerformReportDO> getPerformReportList(PerformReportExportReqVO exportReqVO);

    /* 获得未完成的业绩信息
    * 只计算有没有提交  由processInstance是否为空判断
    * */

    List<PerformReportDO> getUnSubmit();

    /**创建业绩报告
     * @param reportId 存在业绩要求表单的id
     * @param createReqVO 表单信息
     * @param userId 业绩的用户
     * */
    String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqVO createReqVO,String reportId);
    /**获得统计信息
     * @param userId
     * */

    List<StatisticsRespVO>  getStatistics(Long userId);
    /**获得所有业绩报告的统计信息
     * */
    List<StatisticsRespVO> getStatistics();
}
