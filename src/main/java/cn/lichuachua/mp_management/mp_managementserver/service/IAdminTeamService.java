package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminTeam;
import lombok.Data;

/**
 * @author 李歘歘
 *
 */
public interface IAdminTeamService extends IBaseService<AdminTeam, String> {

    /**
     * 禁用队伍
     * @param teamId
     * @param adminId
     */
    void forbidden(String teamId, String adminId);

    /**
     * 保存日志
     * @param teamId
     * @param adminId
     * @param status
     */
    void saveLog(String teamId, String adminId, Integer status);

    /**
     * 解除禁用队伍
     * @param teamId
     * @param adminId
     */
    void relieveForbidden(String teamId, String adminId);
}
