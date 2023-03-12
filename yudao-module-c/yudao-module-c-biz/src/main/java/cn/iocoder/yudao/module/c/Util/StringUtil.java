package cn.iocoder.yudao.module.c.Util;

/**
 * @ClassName StringUtil
 * @Description
 * @Author 15014
 * @Time 2023/3/3 17:12
 * @Version 1.0
 */
public class StringUtil {
    public static boolean notEmpty(String s){
        if(s == null || s.length()== 0){
            return false;
        }
        if(s.trim().length() == 0 ){
            return false;
        }
        return true;
    }
}
