package cn.lichuachua.mp_management.mp_managementserver.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李歘歘
 */
@Data
public class AdminInfoDTO implements Serializable{

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String userNick;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 注册时间
     */
    private String createdAt;


}
