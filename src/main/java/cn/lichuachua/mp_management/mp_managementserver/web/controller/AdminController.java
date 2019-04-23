package cn.lichuachua.mp_management.mp_managementserver.web.controller;

import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.TokenInfo;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.enums.AdminStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.form.*;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminService;
import cn.lichuachua.mp_management.mp_managementserver.util.FileUtil;
import cn.lichuachua.mp_management.mp_managementserver.vo.AdminListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AdminVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 李歘歘
 * 用户接口
 */
@CrossOrigin(origins = "http://127.0.0.1:8080", maxAge = 3600)
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

    /**
     * 查看正常管理员列表
     * @return
     */
    @ApiOperation("查看正常管理员列表")
    @GetMapping("/queryNormalList")
    public ResultWrapper<List<AdminListVO>> queryNormalList(){
        List<AdminListVO> adminListVOList = adminService.queryList(AdminStatusEnum.NORMAL.getStatus());
        return ResultWrapper.successWithData(adminListVOList);
    }

    /**
     * 查看删除管理员列表
     * @return
     */
    @ApiOperation("查看删除管理员列表")
    @GetMapping("/queryDeletedList")
    public ResultWrapper<List<AdminListVO>> queryDeletedList(){
        List<AdminListVO> adminListVOList = adminService.queryList(AdminStatusEnum.DELETED.getStatus());
        return ResultWrapper.successWithData(adminListVOList);
    }


    /**
     * 解除管理员
     * @return
     */
    @ApiOperation("解除管理员")
    @GetMapping("/deleted/{adminId}")
    public ResultWrapper deleted(
            @PathVariable(value = "adminId") String adminId){
        /**
         * 获取当前登录的用户Id
         */
        String adminId1 = getCurrentUserInfo().getUserId();
        /**
         * 传入当前的管理员状态
         */
        adminService.updatedStatus(adminId1,adminId, AdminStatusEnum.NORMAL.getStatus());
        return ResultWrapper.success();
    }


    /**
     * 恢复管理员
     * @return
     */
    @ApiOperation("恢复管理员")
    @GetMapping("/relieve/{adminId}")
    public ResultWrapper relieve(
            @PathVariable(value = "adminId") String adminId){
        /**
         * 获取当前登录的用户Id
         */
        String adminId1 = getCurrentUserInfo().getUserId();
        /**
         * 传入当前的管理员状态
         */
        adminService.updatedStatus(adminId1,adminId,AdminStatusEnum.DELETED.getStatus());
        return ResultWrapper.success();
    }

    /**
     * 显示当前登录的管理员信息
     * @return
     */
    @ApiOperation("显示当前登录的管理员信息")
    @GetMapping("/queryMyInformation")
    public ResultWrapper<AdminVO> queryMyInformation(){
        /**
         * 获取当前登录用户Id
         */
        String adminId = getCurrentUserInfo().getUserId();
        AdminVO adminVO = adminService.queryMyInformation(adminId);
        return ResultWrapper.successWithData(adminVO);
    }


    /**
     * 更换头像
     * @param file
     * @return
     */
    @ApiOperation("更换头像")
    @PutMapping("/updateAvatar/{file}")
    public ResultWrapper updateAvatar(
            @PathVariable(value = "file") MultipartFile file) {
        String filePath = "/static/avatar/";
        String fileName = file.getOriginalFilename();
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 获取当前登录用户
         */
        String adminId= getCurrentUserInfo().getUserId();
        adminService.updateAvatar(fileName, adminId);
        return ResultWrapper.success();
    }



    /**
     * 修改个人信息
     * @param adminInforForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("/修改个人信息")
    @PutMapping("/infor")
    public ResultWrapper infor(
            @Valid AdminInforForm adminInforForm,
            BindingResult bindingResult) {
        /**
         * 验证参数
         */
        validateParams(bindingResult);

        /**
         * 获取当前登录的用户
         */
        String adminId = getCurrentUserInfo().getUserId();

        /**
         * 修改个人信息
         */
        adminService.infor(adminInforForm, adminId);
        return ResultWrapper.success();
    }
}
