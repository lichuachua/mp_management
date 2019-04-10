package cn.lichuachua.mp_management.mp_managementserver.service;


import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.User;
import cn.lichuachua.mp_management.mp_managementserver.vo.UserVO;

import java.util.List;

/**
 * @author 李歘歘
 * 用户业务类接口
 */

public interface IUserService extends IBaseService<User, String> {


    /**
     * 管理用户状态
     * @param adminId
     * @param userId
     * @return
     */
    Integer updateStatus(String adminId, String userId);

    /**
     * 显示封禁用户列表
     * @return
     */
    List<UserVO> queryNormalList();

    /**
     * 显示封禁用户列表
     * @return
     */
    List<UserVO> queryDisabledList();
}
