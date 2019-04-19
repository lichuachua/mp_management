package cn.lichuachua.mp_management.mp_managementserver.web.controller;

import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminTeamService;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://127.0.0.1:8080", maxAge = 3600)
@Api(value = "AdminTeamController", tags = {"管理员队伍API"})
@RestController
@RequestMapping(value = "/admin/team/log")
public class AdminTeamController extends BaseController<AdminInfoDTO> {
    @Autowired
    private IAdminTeamService adminTeamService;

    /**
     * 禁用队伍
     * @param teamId
     * @return
     */
    @ApiOperation("禁用队伍")
    @PostMapping("/forbidden/{teamId}")
    public ResultWrapper forbidden(
            @PathVariable(value = "teamId") String teamId) {
        /**
         * 获取当前用户Id
         */
        String adminId = getCurrentUserInfo().getUserId();
        adminTeamService.forbidden(teamId, adminId);
        return ResultWrapper.success();
    }


    /**
     * 解除禁用队伍
     * @param teamId
     * @return
     */
    @ApiOperation("解除禁用队伍")
    @PostMapping("/relieveForbidden/{teamId}")
    public ResultWrapper relieveForbidden(
            @PathVariable(value = "teamId") String teamId) {
        /**
         * 获取当前用户Id
         */
        String adminId = getCurrentUserInfo().getUserId();
        adminTeamService.relieveForbidden(teamId, adminId);
        return ResultWrapper.success();
    }


}
