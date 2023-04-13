package cn.iocoder.yudao.module.system.controller.admin.home;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.home.vo.UserInfoRespVO;
import cn.iocoder.yudao.module.system.service.home.ChickenSoupService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 首页")
@RestController
@RequestMapping("/system/home")
@Validated
public class HomeController {
    @Resource
    AdminUserService adminUserService;
    @Resource
    ChickenSoupService chickenSoupService;
    @GetMapping("/getInfo")
    @Operation(summary = "获得首页用户所需信息")
    public CommonResult<UserInfoRespVO> getUserInfo() {
        return success(adminUserService.getHomeUserInfo(getLoginUserId()));
    }

    @GetMapping("/chickenSoupForTheSoul")
    @Operation(summary = "获得一个心灵鸡汤")
    public CommonResult<String> getChickenSoupForTheSoul(@RequestParam(required = false) Long id) {
        return success(chickenSoupService.getRandom(id));
    }

}
