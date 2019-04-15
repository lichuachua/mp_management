package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminTeamResource;

/**
 * @author 李歘歘
 *
 */
public interface IAdminResourceService extends IBaseService<AdminTeamResource, String> {

    void saveLog(String resourceId, String adminId, Integer status);


    void relieveForbidden(String resourceId, String adminId);


    void forbidden(String resourceId, String adminId);
}
