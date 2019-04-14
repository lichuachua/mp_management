package cn.lichuachua.mp_management.mp_managementserver.enums;

import lombok.Getter;

/**
 * @author 李歘歘
 */
@Getter
public enum ErrorCodeEnum {

    /**
     * 手机号已经被注册
     */
    MOBILE_REGISTERED(1111, "该手机号已被注册"),
    MOBILE_REGISTERED_DELETE(1111, "该手机号已被注销"),
    /**
     * 手机号还没注册成为系统的账号
     */
    MOBILE_NOT_REGISTERED(1111, "该手机号还未注册"),
    MOBILE_BANNED(1112, "该账号被封"),
    TWO_PASSWORD_NO_EQUALS(1111, "两次密码不一样"),
    /**
     * 用户密码错误
     */
    PASSWORD_ERROR(1111, "密码错误"),
    FORMER_PASSWORD_ERROR(1112,"原密码错误"),
    UNAUTHORIZED(1201, "未认证"),

    BAD_ACCESS_TOKEN(1201, "错误的token"),
    ERROR_USER_ID(1111, "错误的USER_ID"),
    NONE_FRIEND_APPLY(1111, "不存在的好友申请"),
    INVALID_APPLY_STATUS(1111, "申请状态错误"),
    ERROR_USER(1222,"该用户不存在"),
    NO_VISUAL_USER(1223,"该用户设置不可见"),
    ERROR_USER_OR_MOBILE_DELETE(1224,"该用户不存在或者该账号被封禁"),
    /**
     * 文章不存在或者非此用户所发布  不能操作
     * @param code
     * @param message
     */
    ARTICLE_NO(12124,"操作错误"),
    ARTICLE_NO_VISUAL(12121,"文章不可见"),

    ARTICLE_NO_EXIT(12224,"文章不存在"),
    ARTICLE_DELETED(12225,"文章已被删除"),
    ARTICLE_DELETED_OR_INFORM(12226,"文章已被封禁或者删除"),
    COMMENT_NO(12226,"评论不存在"),
    COLLECT_EXIT(12111,"收藏已存在"),
    COLLECT_NO_EXIT(12112,"收藏不存在"),
    INFORM_EXIT(1212,"已经举报成功，请勿重复操作"),
    INFORM_NO_EXIT(1211,"该举报不存在"),
    INFORM_OPERATION(1212,"该举报已被处理"),
    FOLLOW_EXIT(12114,"收藏已存在"),
    FOLLOW_NO_EXIT(12113,"收藏不存在"),
    ADMIN_EXIT(11111,"该用户已经是管理员"),
    ADMIN_NO_EXIT(11112,"管理员不存在"),
    RANL_ERROR(11102,"等级过高"),
    SCHOOL_EXIT(12122,"该学校已经存在"),
    SCHOOL_NO_EXIT(12122,"该学校不存在"),
    TYPE_EXIT(12123,"该文章类型已经存在"),
    TYPE_NO_EXIT(12124,"该学院不存在"),
    ACADEMY_EXIT(12125,"该学院已经存在"),
    ACADEMY_NO_EXIT(12126,"该学院不存在"),
    ANNOUNCEMENT_NO_EXIT(12224,"公告不存在"),
    /**
     * 队伍不存在
     */
    TEAM_NO_EXIT(1333,"队伍不存在"),
    TEAM_EXIT(1333,"队伍已经存在"),
    NO_JURISDICTION(13331,"没有权限"),
    /**
     * 已在该队伍中
     */
    TEAMMEMBER_EXIT(12211,"已在该队伍中"),
    TEAMMEMBER_NO_EXIT(12211,"您不在该队伍中"),
    /**
     * 队伍类型不存在
     */
    TEAM_TYPE_NO_EXIT(12111,"该队伍类型不存在"),
    TEAM_RESOURCE_NO_EXIT(12111,"该资源不存在"),
    /**
     * 验证码错误
     * @param code
     * @param message
     */
    VERIFICATION_CODE_ERROR(122222,"验证码错误"),
    /**
     * 验证码异常
     * @param code
     * @param message
     */
    VERIFICATION_CODE_EXCEPTION(122222,"手机号和接收验证码的手机号不同引起-验证码异常"),
    /**
     * 评论不存在或非用户所发，无权限删除
     */
    COMMENT_NO_EXIT(12223, "操作不正确");





    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;
}
