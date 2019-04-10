package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminUser;

public interface IAdminUserService extends IBaseService<AdminUser, String> {
    /**
     * 将更改用户的状态信息写入日志
     * @param adminId
     * @param userId
     * @param status
     */
    void publish(String adminId, String userId, Integer status);
}
