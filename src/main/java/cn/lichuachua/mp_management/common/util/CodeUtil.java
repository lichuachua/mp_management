package cn.lichuachua.mp_management.common.util;

import cn.lichuachua.mp_management.mp_managementserver.repository.redis.RedisRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static cn.lichuachua.mp_management.common.constant.SysConstant.REGISTERED_VERIFICATON_MAX;
import static cn.lichuachua.mp_management.common.constant.SysConstant.REGISTERED_VERIFICATON_MIN;

/**
 * @author 李歘歘
 */
public class CodeUtil {

    @Autowired
    RedisRepositoryImpl redisRepository = new RedisRepositoryImpl();

    /**
     * 生成验证码
     */
    public static String smsCode(){
        Random random = new Random();
        int codeNum = random.nextInt(REGISTERED_VERIFICATON_MAX) + REGISTERED_VERIFICATON_MIN;
        return String.valueOf(codeNum);
    }

}
