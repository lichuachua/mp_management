package cn.lichuachua.mp_management.mp_managementserver.web.controller;

import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.TokenInfo;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.form.ChangePasswordForm;
import cn.lichuachua.mp_management.mp_managementserver.form.SendCodeForm;
import cn.lichuachua.mp_management.mp_managementserver.form.AdminLoginForm;
import cn.lichuachua.mp_management.mp_managementserver.form.AdminRegisterForm;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminService;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminUserService;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 李歘歘
 * 用户接口
 */
@Api(value = "AdminController", tags = {"管理员API"})
@RestController
@RequestMapping(value = "/admin")
public class AdminController extends BaseController<AdminInfoDTO> {

    @Autowired
    private IAdminService adminService;


    /**
     * 给予管理员权限
     * @param adminRegisterForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("给予管理员权限")
    @PostMapping("/register/")
    public ResultWrapper register(
            @Valid AdminRegisterForm adminRegisterForm,
            BindingResult bindingResult)  {
        /**
         * 检验参数
         */
        validateParams(bindingResult);
        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();

        /**
         * 注册
         */
        adminService.register(adminRegisterForm,userId);

        return ResultWrapper.success();
    }


    /**
     * 用户登录
     * @param adminLoginForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("管理员登录")
    @PostMapping("/login")
    public ResultWrapper login(
            @Valid @RequestBody AdminLoginForm adminLoginForm,
            BindingResult bindingResult) {
        /**
         * 验证参数
         */
        validateParams(bindingResult);

        /**
         * 登录
         */
        TokenInfo tokenInfo = adminService.login(adminLoginForm);
        /**
         * 返回token
         */
        return ResultWrapper.successWithData(tokenInfo);


    }

    /**
     * 发送验证码
     * @param sendCodeForm
     * @param bindingResult
     * @return
     * @throws ClientException
     */
    @ApiOperation("发送验证码")
    @PostMapping("/sendCode")
    public ResultWrapper sendCode(
            @Valid SendCodeForm sendCodeForm,
            BindingResult bindingResult) throws ClientException {
        /**
         * 验证参数
         */
        validateParams(bindingResult);
        /**
         * 发送验证码，并存储
         */
        adminService.sendCode(sendCodeForm);

        return ResultWrapper.success();

    }

    /**
     * 退出登录
     * @return
     */
    @ApiOperation("/退出登录")
    @PutMapping("/logout")
    public ResultWrapper logout(){

        /**
         * 获取当前登录的用户
         */
        String adminId = getCurrentUserInfo().getUserId();
        adminService.logout(adminId);
        return ResultWrapper.success();
    }

    /**
     * 修改密码
     * @param changePasswordForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("修改密码")
    @PutMapping("/changePassword")
    public ResultWrapper changePassword(
            @Valid ChangePasswordForm changePasswordForm,
            BindingResult bindingResult){
        /**
         * 验证参数
         */
        validateParams(bindingResult);
        /**
         * 获取当前登录的用户
         */
        String adminId = getCurrentUserInfo().getUserId();
        /**
         * 修改密码
         */
        adminService.changePassword(changePasswordForm, adminId);
        return ResultWrapper.success();
    }

}
