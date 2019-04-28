package cn.lichuachua.mp_management.mp_managementserver.web.controller;

import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminUserService;
import cn.lichuachua.mp_management.mp_managementserver.service.IUserService;
import cn.lichuachua.mp_management.mp_managementserver.vo.UserVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李歘歘
 * 用户接口
 */
@CrossOrigin(origins = "http://127.0.0.1:8081", maxAge = 3600)
@Api(value = "UserController", tags = {"用户API"})
@RestController
@RequestMapping(value = "/admin/user")
public class UserController extends BaseController<AdminInfoDTO> {

    @Autowired
    private IUserService userService;


    /**
     * 管理用户状态
     * @param userId
     * @return
     */
    @ApiOperation("管理用户状态")
    @PutMapping("/updateStatus/{userId}")
    public ResultWrapper<Integer> updateStatus(
            @PathVariable(value = "userId") String userId){
        /**
         * 获取当前登录的adminId
         */
        String adminId = getCurrentUserInfo().getUserId();
        /**
         * 修改其状态
         */
        Integer status = userService.updateStatus(adminId, userId);

        return ResultWrapper.successWithData(status);
    }

    /**
     * 显示正常用户列表
     * @return
     */
    @ApiOperation("显示正常用户列表")
    @GetMapping("/queryNormalList")
    public ResultWrapper<List<UserVO>> queryNormalList(){
        /**
         * 显示正常用户列表
         */
        List<UserVO> userVOList = userService.queryNormalList();
        return ResultWrapper.successWithData(userVOList);
    }

    /**
     * 显示封禁用户列表
     * @return
     */
    @ApiOperation("显示封禁用户列表")
    @GetMapping("/queryDisabledList")
    public ResultWrapper<List<UserVO>> queryDisabledList(){
        /**
         * 显示封禁用户列表
         */
        List<UserVO> userVOList = userService.queryDisabledList();
        return ResultWrapper.successWithData(userVOList);
    }


}
