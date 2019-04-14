package cn.lichuachua.mp_management.mp_managementserver.enums;

import lombok.Getter;

/**
 * @author 李歘歘
 */
@Getter
public enum InformArticleEnum {

    /**
     * 未处理
     */
    INFORM_ARTICLE_NO_MANAGE(0,"未处理"),

    /**
     * 已处理
     */
    INFORM_ARTICLE_MANAGE(1,"已处理")

    ;
    InformArticleEnum(Integer status, String desc){
        this.desc = desc;
        this.status = status;
    }

    /**
     * 状态描述
     */
    private String desc;
    /**
     * 状态
     */
    private Integer status;
}
