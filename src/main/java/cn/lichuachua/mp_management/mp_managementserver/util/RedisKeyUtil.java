package cn.lichuachua.mp_management.mp_managementserver.util;

/**
 * @author 李歘歘
 * Redis键生成器
 */
public class RedisKeyUtil {

    public static String buildKey(String template, String ...args) {
        return String.format(template, args);
    }
}
