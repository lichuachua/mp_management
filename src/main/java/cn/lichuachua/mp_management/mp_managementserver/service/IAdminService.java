package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.dto.TokenInfo;
import cn.lichuachua.mp_management.mp_managementserver.entity.Admin;
import cn.lichuachua.mp_management.mp_managementserver.form.*;
import cn.lichuachua.mp_management.mp_managementserver.vo.AdminListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AdminVO;
import com.aliyuncs.exceptions.ClientException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

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

    /**
     * 根据状态查看管理员列表
     * @param status
     * @return
     */
    List<AdminListVO> queryList(Integer status);

    /**
     * 更新管理员状态
     * @param adminId
     * @param status
     * @return
     */
    void updatedStatus(String adminId1, String adminId, Integer status);


    /**
     * 显示当前登录的管理员信息
     * @param adminId
     * @return
     */
    AdminVO queryMyInformation(String adminId);

    /**
     * 更换头像
     * @param fileName
     * @param adminId
     */
    void updateAvatar(String fileName, String adminId);

    /**
     * 修改个人信息
     * @param adminInforForm
     * @param adminId
     */
    void infor(@Valid AdminInforForm adminInforForm, String adminId);

}
