package cn.iocoder.yudao.module.c.Util;

import cn.iocoder.yudao.module.c.controller.admin.Util.vo.AttachReqVO;

/**
 * @ClassName ContractGenrator
 * @Description
 * @Author 15014
 * @Time 2023/2/18 23:11
 * @Version 1.0
 */
public class ContractGenerator {

    public static String generator(AttachReqVO attachReqVO) {
        String content = attachReqVO.getAttach();
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        int startIndex = -1;
        // 对所有的变量赋值
        while ((startIndex = content.indexOf("{",index))!= -1){
            int endIndex = content.indexOf("}",index);
            // 对错误的{进行处理
            if(content.substring(startIndex + 1, endIndex).contains("{")){
                throw new IllegalArgumentException("非法的变量赋值，请检查{}");
            }else{
                stringBuilder.append(content,index,startIndex);
                String key = content.substring(startIndex + 1, endIndex);
                String data = getData(attachReqVO,key);
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

    private static String getData(AttachReqVO attachReqVO, String param) {
        switch (param) {
            case "姓名":
                return attachReqVO.getName();
            case "身份证号":
                return attachReqVO.getIdentityCard();
            case "薪资":
                return attachReqVO.getSalary().toString();
            case "甲方":
                return attachReqVO.getFirstParty();
            case "岗位":
                return attachReqVO.getPost();
            case "业绩要求":
                return attachReqVO.getPerformanceRequirements();
            case "违约条款":
                return attachReqVO.getDefaultClause();
            case "合同开始时间":
                return attachReqVO.getStartTime().toString();
            case "合同结束时间":
                return attachReqVO.getEndTime().toString();
            case "签约时间":
                return attachReqVO.getSignedTime().toString();
            default:
                throw new IllegalArgumentException("非法的变量赋值，请检查："+param);
        }
    }
}
