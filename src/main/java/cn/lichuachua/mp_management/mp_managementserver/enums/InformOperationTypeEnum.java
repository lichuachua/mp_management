package cn.lichuachua.mp_management.mp_managementserver.enums;

import lombok.Getter;

/**
 * @author 李歘歘
 * 用户状态类
 */
@Getter
public enum InformOperationTypeEnum {
    /**
     * 正常
     */
    NORMAL(0,"开启"),

    /**
     * 忽略
     */
    IGNORE(1,"忽略"),
    /**
     * 禁用
     */
    DELETED(-1,"禁用");

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 用户状态描述
     */
    private String desc;

    InformOperationTypeEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
