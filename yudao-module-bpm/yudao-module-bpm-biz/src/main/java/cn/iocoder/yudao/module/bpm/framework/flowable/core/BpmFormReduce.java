package cn.iocoder.yudao.module.bpm.framework.flowable.core;

/**
 * @ClassName BusinessFormReduce
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/11 21:20
 * @Version 1.0
 */
public interface BpmFormReduce {
    /** 流程创建后调用  */
    void create(String processInstanceId);
    /** 流程取消（或拒绝）后调用  */
    void cancel(String processInstanceId);
    /** 流程完成后调用  */
    void completion(String processInstanceId);
}
