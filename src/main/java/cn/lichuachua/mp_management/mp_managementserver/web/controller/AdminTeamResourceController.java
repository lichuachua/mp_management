package cn.lichuachua.mp_management.mp_managementserver.web.controller;

import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminResourceService;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@Api(value = "AdminTeamResourceController", tags = {"管理员资源API"})
@RestController
@RequestMapping(value = "/admin/teamResource/log")
public class AdminTeamResourceController extends BaseController<AdminInfoDTO> {

    @Autowired
    private IAdminResourceService adminResourceService;


    /**
     * 禁用资源
     * @param resourceId
     * @return
     */
    @ApiOperation("禁用资源")
    @PostMapping("/forbidden/{resourceId}")
    public ResultWrapper forbidden(
            @PathVariable(value = "resourceId") String resourceId) {
        /**
         * 获取当前用户Id
         */
        String adminId = getCurrentUserInfo().getUserId();
        adminResourceService.forbidden(resourceId, adminId);
        return ResultWrapper.success();
    }


    /**
     * 解除禁用资源
     * @param resourceId
     * @return
     */
    @ApiOperation("解除禁用资源")
    @PostMapping("/relieveForbidden/{resourceId}")
    public ResultWrapper relieveForbidden(
            @PathVariable(value = "resourceId") String resourceId) {
        /**
         * 获取当前用户Id
         */
        String adminId = getCurrentUserInfo().getUserId();
        adminResourceService.relieveForbidden(resourceId, adminId);
        return ResultWrapper.success();
    }

}
