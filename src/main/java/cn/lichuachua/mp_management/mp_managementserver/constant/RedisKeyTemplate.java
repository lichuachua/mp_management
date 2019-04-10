package cn.lichuachua.mp_management.mp_managementserver.constant;

public class RedisKeyTemplate {

    /**
     * 记录token的详细信息
     * 键：ACCESS_TOKEN:accessToken
     * 值：{ accessToken, userId }
     */
    public final static String T_ACCESS_TOKEN = "ATOKEN:%s";

    /**
     * 记录用户当前正在使用的token
     * 键：USER_ACCESS_TOKEN:userId
     * 值：accessToken
     */
    public final static String T_ADMIN_CURRENT_TOKEN = "ADMIN_TOKEN:%s";

    /**
     * 记录用户当前正在使用的socket客户端信息
     * 键：USER_ACCESS_TOKEN:userId
     * 值：accessToken
     */
    public final static String T_ADMIN_CURRENT_CLIENT = "ADMIN_CLIENT:%s";

    /**
     * 记录用户的离线消息
     * 键：OFFLINE_MSG:userId
     * 值：离线消息列表
     */
    public final static String T_OFFLINE_MSG = "OFFLINE_MSG:%s";

    /**
     * 记录手机号和验证码
     * 键：T_VERIFICATION_CODE
     * 值：{ telephone, code }
     */
    public final static String T_VERIFICATION_CODE = "ADMIN_VERIFICATION:%s";

}
