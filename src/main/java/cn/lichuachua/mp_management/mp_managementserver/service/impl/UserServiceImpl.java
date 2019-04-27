package cn.lichuachua.mp_management.mp_managementserver.service.impl;


import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.User;
import cn.lichuachua.mp_management.mp_managementserver.enums.EmailCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.UserOperationTypeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.UserStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.UserException;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminUserService;
import cn.lichuachua.mp_management.mp_managementserver.service.IUserService;
import cn.lichuachua.mp_management.mp_managementserver.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static cn.lichuachua.mp_management.mp_managementserver.util.EmailUtil.send;


/**
 * @author 李歘歘
 * 用户业务类接口实现类
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,String> implements IUserService {


    @Autowired
    private IAdminUserService adminUserService;


    /**
     * 管理用户状态
     * @param adminId
     * @param userId
     * @return
     */
    @Override
    public Integer updateStatus(String adminId, String userId){
        /**
         * 根据userId查找对应的用户
         */
        User user = new User();
        user.setUserId(userId);
        Optional<User> userOptional = selectOne(Example.of(user));
        if (!userOptional.isPresent()){
            throw new UserException(ErrorCodeEnum.ERROR_USER);
        }
        /**
         * 用户存在，判断用户状态，用户状态为0===》1
         *  用户状态为1===》》0
         */
        user.setAcademyId(userOptional.get().getAcademyId());
        user.setMobile(userOptional.get().getMobile());
        user.setSchoolId(userOptional.get().getSchoolId());
        user.setUserAvatar(userOptional.get().getUserAvatar());
        user.setUserName(userOptional.get().getUserName());
        user.setUserEmail(userOptional.get().getUserEmail());
        user.setUserNumber(userOptional.get().getUserNumber());
        user.setUserNick(userOptional.get().getUserNick());
        user.setUpdatedAt(new Date());
        user.setCreatedAt(userOptional.get().getCreatedAt());
        user.setVisual(userOptional.get().getVisual());
        user.setPassword(userOptional.get().getPassword());
        if (userOptional.get().getStatus().equals(UserStatusEnum.NORMAL.getStatus())){
            user.setStatus(UserStatusEnum.DISABLED.getStatus());
            update(user);
            /**
             * 写入日志表
             */
            adminUserService.publish(adminId, userId, UserOperationTypeEnum.DELETED.getStatus());
            /**
             * 发送邮件提醒
             */
            try {
                send(userOptional.get().getUserEmail(), EmailCodeEnum.USER_STATUS.getMessage(), EmailCodeEnum.USER.getMessage()+EmailCodeEnum.USER_DISABLED.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        }else {
            user.setStatus(UserStatusEnum.NORMAL.getStatus());
            update(user);
            /**
             * 写入日志表
             */
            adminUserService.publish(adminId, userId, UserOperationTypeEnum.NORMAL.getStatus());
            /**
             * 发送邮件提醒
             */
            try {
                send(userOptional.get().getUserEmail(), EmailCodeEnum.USER_STATUS.getMessage(), EmailCodeEnum.USER.getMessage()+EmailCodeEnum.USER_NORMAL.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    /**
     * 显示正常用户列表
     * @return
     */
    @Override
    public List<UserVO> queryNormalList(){
        List<User> userList = selectAll();
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : userList){
            if (user.getStatus().equals(UserStatusEnum.NORMAL.getStatus())){
                UserVO userVO = new UserVO();
                userVO.setAcademyId(user.getAcademyId());
                userVO.setMobile(user.getMobile());
                userVO.setSchoolId(user.getSchoolId());
                userVO.setUserAvatar(user.getUserAvatar());
                userVO.setUserEmail(user.getUserEmail());
                userVO.setUserId(user.getUserId());
                userVO.setUserName(user.getUserName());
                userVO.setUserNick(user.getUserNick());
                userVO.setUserNumber(user.getUserNumber());
                BeanUtils.copyProperties(user, userVO);
                userVOList.add(userVO);
            }
        }
        return userVOList;

    }

    /**
     * 显示封禁用户列表
     * @return
     */
    @Override
    public List<UserVO> queryDisabledList(){
        List<User> userList = selectAll();
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : userList){
            if (user.getStatus().equals(UserStatusEnum.DISABLED.getStatus())){
                UserVO userVO = new UserVO();
                userVO.setAcademyId(user.getAcademyId());
                userVO.setMobile(user.getMobile());
                userVO.setSchoolId(user.getSchoolId());
                userVO.setUserAvatar(user.getUserAvatar());
                userVO.setUserEmail(user.getUserEmail());
                userVO.setUserId(user.getUserId());
                userVO.setUserName(user.getUserName());
                userVO.setUserNick(user.getUserNick());
                userVO.setUserNumber(user.getUserNumber());
                BeanUtils.copyProperties(user, userVO);
                userVOList.add(userVO);
            }
        }
        return userVOList;

    }

}
