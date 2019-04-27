package cn.lichuachua.mp_management.mp_managementserver.enums;

import lombok.Getter;

/**
 * @author 李歘歘
 */
@Getter
public enum EmailCodeEnum {
    /**
     * 用户尊称
     */
    USER("尊敬的用户,"),
    /**
     * 用户状态修改
     */
    USER_STATUS("用户管理中心"),

    /**
     * 解除封禁
     */
    USER_NORMAL("您的账号已经恢复使用，如需其他操作请联系管理员"),
    /**
     * 封禁用户
     * @param message
     */
    USER_DISABLED("您的账号涉嫌不正当的发言或者操作，已经被管理员封禁，如需解封，请联系管理员");


    EmailCodeEnum(String message) {
        this.message = message;
    }

    private String message;
}
