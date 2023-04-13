package cn.iocoder.yudao.module.c.Util;

import cn.iocoder.yudao.framework.common.util.date.DateUtils;
import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachBean;

import java.time.format.DateTimeFormatter;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.CONTRACT_ATTACH_GRAMMAR_ERROR;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.CONTRACT_ATTACH_PARAM_ERROR;
import static cn.iocoder.yudao.module.c.enums.ErrorCodeConstants.CONTRACT_NOT_EXISTS;

/**
 * @ClassName ContractGenrator
 * @Description
 * @Author 15014
 * @Time 2023/2/18 23:11
 * @Version 1.0
 */
public class ContractGenerator {

    public static String generator(AttachBean attachBean) {
        String content = attachBean.getAttach();
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        int startIndex = -1;
        // 对所有的变量赋值
        while ((startIndex = content.indexOf("{",index))!= -1){
            int endIndex = content.indexOf("}",index);
            // 对错误的{进行处理
            if(content.substring(startIndex + 1, endIndex).contains("{")){
                throw exception(CONTRACT_ATTACH_GRAMMAR_ERROR,"{}{}");
            }else{
                stringBuilder.append(content,index,startIndex);
                String key = content.substring(startIndex + 1, endIndex);
                String data = getData(attachBean,key);
                if(data == null){
                    data = "<font color='red'>"+key+"未赋值</font>";
                }
                stringBuilder.append(data);
                index = endIndex + 1;
            }
        }
        stringBuilder.append(content,index,content.length());
        return stringBuilder.toString();
    }
    private static String[] param = {"姓名","身份证号","薪资","甲方","岗位","业绩要求","违约条款","合同开始时间","合同结束时间","签约时间"};
    private static String getMay(String p){
        int max = 0;
        int pos = 0;
        for (int j = 0; j < param.length; j++) {
            String s = param[j];
            int cache = 0;
            for(int i = 0; i<p.length();i++){
                if(s.indexOf(p.charAt(i)) == -1){
                    continue;
                }
                cache ++;
            }
            if(cache > max){
                max = cache;
                pos = j;
            }
        }
        return param[pos];
    }

    private static String getData(AttachBean attachBean, String param) {
        switch (param) {
            case "姓名":
                return attachBean.getName()==null?"<font color='red'>{"+param+"}</font>":attachBean.getName();
            case "身份证号":
                return attachBean.getIdentityCard()==null?"<font color='red'>{"+param+"}</font>":attachBean.getIdentityCard();
            case "薪资":
                return attachBean.getSalary()==null?"<font color='red'>{"+param+"}</font>":attachBean.getSalary().toString();
            case "甲方":
                return attachBean.getFirstParty()==null?"<font color='red'>{"+param+"}</font>":attachBean.getFirstParty();
            case "岗位":
                return attachBean.getPost()==null?"<font color='red'>{"+param+"}</font>":attachBean.getPost();
            case "业绩要求":
                return attachBean.getPerformanceRequirements()==null?"<font color='red'>{"+param+"}</font>":attachBean.getPerformanceRequirements();
            case "违约条款":
                return attachBean.getDefaultClause()==null?"<font color='red'>{"+param+"}</font>":attachBean.getDefaultClause();
            case "合同开始时间":
                return attachBean.getStartTime()==null?"<font color='red'>{"+param+"}</font>":DateTimeFormatter.ofPattern(DateUtils.FORMAT_YEAR_MONTH_DAY).format(attachBean.getStartTime());
            case "合同结束时间":
                return attachBean.getEndTime()==null?"<font color='red'>{"+param+"}</font>":DateTimeFormatter.ofPattern(DateUtils.FORMAT_YEAR_MONTH_DAY).format(attachBean.getEndTime());
            case "签约时间":
                return attachBean.getSignedTime()==null?"<font color='red'>{"+param+"}</font>":DateTimeFormatter.ofPattern(DateUtils.FORMAT_YEAR_MONTH_DAY).format(attachBean.getSignedTime());
            default:
                // return "<font color='red'>{"+param+"}</font>";
                throw exception(CONTRACT_ATTACH_PARAM_ERROR,param,getMay(param));
        }
    }
}
