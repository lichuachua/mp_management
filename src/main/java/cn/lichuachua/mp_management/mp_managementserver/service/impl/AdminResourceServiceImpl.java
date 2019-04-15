package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.*;
import cn.lichuachua.mp_management.mp_managementserver.enums.AdminStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.OperationTypeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.AdminException;
import cn.lichuachua.mp_management.mp_managementserver.exception.TeamResourceException;
import cn.lichuachua.mp_management.mp_managementserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author 李歘歘
 */
@Service

public class AdminResourceServiceImpl extends BaseServiceImpl<AdminTeamResource, String> implements IAdminResourceService {

    @Autowired
    private ITeamResourceService teamResourceService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IUserService userService;

    /**
     * 保存日志
     * @param resourceId
     * @param adminId
     */
    @Override
    public void saveLog(String resourceId, String adminId, Integer status){
        /**
         * 写入日志
         */
        AdminTeamResource adminTeamResource = new AdminTeamResource();
        adminTeamResource.setAdminId(adminId);
        /**
         * 根据adminId查出adminMobile
         */
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setStatus(AdminStatusEnum.NORMAL.getStatus());
        Optional<Admin> adminOptional = adminService.selectOne(Example.of(admin));
        if (!adminOptional.isPresent()){
            throw new AdminException(ErrorCodeEnum.ADMIN_NO_EXIT);
        }
        adminTeamResource.setAdminMobile(adminOptional.get().getMobile());
        /**
         * 查询出tresourceId对应的resource
         */
        Optional<TeamResource> teamResourceOptional = teamResourceService.selectByKey(resourceId);
        if (!teamResourceOptional.isPresent()){
            throw new TeamResourceException(ErrorCodeEnum.TEAM_RESOURCE_NO_EXIT);
        }
        adminTeamResource.setCreatedAt(new Date());
        adminTeamResource.setOperationType(status);
        adminTeamResource.setPublisherId(teamResourceOptional.get().getPublisherId());
        /**
         * 根据用户Id查出用户手机号
         */
        Optional<User> userOptional = userService.selectByKey(teamResourceOptional.get().getPublisherId());
        adminTeamResource.setPublisherMobile(userOptional.get().getMobile());
        adminTeamResource.setResourceId(resourceId);
        adminTeamResource.setUpdatedAt(new Date());
        save(adminTeamResource);
    }


    /**
     * 禁用队伍
     * @param resourceId
     * @param adminId
     */
    @Override
    public void forbidden(String resourceId, String adminId){
        /**
         * 禁用队伍
         */
        teamResourceService.forbidden(resourceId);
        /**
         * 保存日志
         */
        saveLog(resourceId, adminId, OperationTypeEnum.DELETED.getStatus());
    }

    /**
     * 解除禁用队伍
     * @param resourceId
     * @param adminId
     */
    @Override
    public void relieveForbidden(String resourceId, String adminId){
        /**
         * 解除禁用队伍
         */
        teamResourceService.relieveForbidden(resourceId);
        /**
         * 保存日志
         */
        saveLog(resourceId, adminId, OperationTypeEnum.NORMAL.getStatus());
    }


}
