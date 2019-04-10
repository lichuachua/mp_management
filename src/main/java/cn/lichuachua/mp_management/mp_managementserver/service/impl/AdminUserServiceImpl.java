package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.Admin;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminUser;
import cn.lichuachua.mp_management.mp_managementserver.entity.User;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminService;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminUserService;
import cn.lichuachua.mp_management.mp_managementserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUser, String> implements IAdminUserService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAdminService adminService;
    /**
     * 将更改用户的状态信息写入日志
     * @param adminId
     * @param userId
     * @param status
     */
    @Override
    public void publish(String adminId, String userId, Integer status) {
        AdminUser adminUser = new AdminUser();
        adminUser.setAdminId(adminId);
        adminUser.setUserId(userId);
        adminUser.setOperationType(status);
        adminUser.setCreatedAt(new Date());
        adminUser.setUpdatedAt(new Date());
        /**
         * 根据userId取出userMobile
         */
        Optional<User> userOptional = userService.selectByKey(userId);
        adminUser.setUserMobile(userOptional.get().getMobile());
        /**
         * 根据adminId取出adminMobile
         */
        Optional<Admin> adminOptional = adminService.selectByKey(adminId);
        adminUser.setAdminMobile(adminOptional.get().getMobile());
        save(adminUser);
    }
}