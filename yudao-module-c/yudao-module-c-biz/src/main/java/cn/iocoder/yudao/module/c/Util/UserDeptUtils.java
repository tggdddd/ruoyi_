package cn.iocoder.yudao.module.c.Util;

import cn.iocoder.yudao.module.bpm.framework.ApplicationContextUtils;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;

import java.util.List;

/**
 * @ClassName UserDeptUtils
 * @Description
 * @Author 肖润杰
 * @Time 2023/4/13 0:19
 * @Version 1.0
 */
public class UserDeptUtils {
    public static List getSubDeptIds(Long deptId){
        if(deptId==null){
            return null;
        }
        List<Long> deptIds = ApplicationContextUtils.getBean(DeptApi.class).getSubDeptIds(deptId);
        return deptIds;
    }
    public static List getSubDeptIdsAnd(Long deptId){
        if(deptId == null){
            return null;
        }
        List subDeptIds = getSubDeptIds(deptId);
        subDeptIds.add(deptId);
        return subDeptIds;
    }
    public static  List getUserByDeptIds(List deptIds){
        if(deptIds == null){
            return null;
        }
        List userListByDeptIds = ApplicationContextUtils.getBean(AdminUserApi.class).getUserIdListByDeptIds(deptIds);
        return    userListByDeptIds;
    }
}
