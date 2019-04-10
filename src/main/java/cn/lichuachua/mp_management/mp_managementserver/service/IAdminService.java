package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.dto.TokenInfo;
import cn.lichuachua.mp_management.mp_managementserver.entity.Admin;
import cn.lichuachua.mp_management.mp_managementserver.form.ChangePasswordForm;
import cn.lichuachua.mp_management.mp_managementserver.form.SendCodeForm;
import cn.lichuachua.mp_management.mp_managementserver.form.AdminLoginForm;
import cn.lichuachua.mp_management.mp_managementserver.form.AdminRegisterForm;
import com.aliyuncs.exceptions.ClientException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author 李歘歘
 * 用户业务类接口
 */

public interface IAdminService extends IBaseService<Admin, String> {
    /**
     * 用户注册
     * @param
     */
    void register(@Valid AdminRegisterForm adminRegisterForm, String userId);

    /**
     * 登录
     * @param adminLoginForm
     * @return
     */
    TokenInfo login(@Valid AdminLoginForm adminLoginForm);

    /**
     * 退出登录
     * @param adminId
     */
    void logout(String adminId);

    /**
     * 发送验证码
     * @param sendCodeForm
     */
    void sendCode(@Valid SendCodeForm sendCodeForm) throws ClientException;



    /**
     * 检测验证码和手机号
     * @param mobile
     * @param code
     */
    void verification(@Pattern(message = "手机号不合法", regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$") @NotEmpty(message = "请填写手机号") String mobile, @NotEmpty(message = "验证码不为空") String code);

    /**
     * 修改密码
     * @param changePasswordForm
     * @param adminId
     */
    void changePassword(@Valid ChangePasswordForm changePasswordForm, String adminId);
}