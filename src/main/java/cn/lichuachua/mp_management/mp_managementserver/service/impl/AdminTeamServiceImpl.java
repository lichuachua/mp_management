package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.Admin;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminTeam;
import cn.lichuachua.mp_management.mp_managementserver.entity.Team;
import cn.lichuachua.mp_management.mp_managementserver.enums.AdminStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.OperationTypeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.TeamStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.AdminException;
import cn.lichuachua.mp_management.mp_managementserver.exception.TeamException;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminService;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminTeamService;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author 李歘歘
 */
@Service

public class AdminTeamServiceImpl extends BaseServiceImpl<AdminTeam, String> implements IAdminTeamService {

    @Autowired
    private ITeamService teamService;
    @Autowired
    private IAdminService adminService;

    /**
     * 保存日志
     * @param teamId
     * @param adminId
     */
    @Override
    public void saveLog(String teamId, String adminId, Integer status){
        /**
         * 写入日志
         */
        AdminTeam adminTeam = new AdminTeam();
        adminTeam.setAdminId(adminId);
        /**
         * 根据adminId查出adminTeam
         */
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setStatus(AdminStatusEnum.NORMAL.getStatus());
        Optional<Admin> adminOptional = adminService.selectOne(Example.of(admin));
        if (!adminOptional.isPresent()){
            throw new AdminException(ErrorCodeEnum.ADMIN_NO_EXIT);
        }
        adminTeam.setAdminMobile(adminOptional.get().getMobile());
        /**
         * 查询出teamId对应的team
         */
        Optional<Team> teamOptional = teamService.selectByKey(teamId);
        adminTeam.setHeaderId(teamOptional.get().getHeaderId());
        adminTeam.setHeaderMobile(teamOptional.get().getHeaderMobile());
        adminTeam.setTeamName(teamOptional.get().getTeamName());
        adminTeam.setCreatedAt(new Date());
        adminTeam.setUpdatedAt(new Date());
        adminTeam.setOperationType(status);
        adminTeam.setTeamId(teamId);
        save(adminTeam);
    }


    /**
     * 禁用队伍
     * @param teamId
     * @param adminId
     */
    @Override
    public void forbidden(String teamId, String adminId){
        /**
         * 禁用队伍
         */
        teamService.forbidden(teamId);
        /**
         * 保存日志
         */
        saveLog(teamId, adminId, OperationTypeEnum.DELETED.getStatus());
    }

    /**
     * 解除禁用队伍
     * @param teamId
     * @param adminId
     */
    @Override
    public void relieveForbidden(String teamId, String adminId){
        /**
         * 解除禁用队伍
         */
        teamService.relieveForbidden(teamId);
        /**
         * 保存日志
         */
        saveLog(teamId, adminId, OperationTypeEnum.NORMAL.getStatus());
    }
}
