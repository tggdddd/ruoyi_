package cn.iocoder.yudao.module.c.config;

import cn.iocoder.yudao.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.iocoder.yudao.module.c.dal.dataobject.PerformanceReportRequest.PerformanceReportRequestDO;
import cn.iocoder.yudao.module.c.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.c.dal.dataobject.contracttemplate.ContractTemplateDO;
import cn.iocoder.yudao.module.c.dal.dataobject.performreport.PerformReportDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @ClassName c 模块的数据权限 Configuration
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/13 11:21
 * @Version 1.0
 */

@Configuration(proxyBeanMethods = false)
public class CDataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer cDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(ContractDO.class);
            rule.addDeptColumn(ContractTemplateDO.class);
            rule.addDeptColumn(PerformanceReportRequestDO.class);
            rule.addDeptColumn(PerformReportDO.class);
            // user
            rule.addUserColumn(ContractDO.class);
            rule.addUserColumn(ContractTemplateDO.class);
            rule.addUserColumn(PerformanceReportRequestDO.class);
            rule.addUserColumn(PerformReportDO.class);
        };
    }

}
