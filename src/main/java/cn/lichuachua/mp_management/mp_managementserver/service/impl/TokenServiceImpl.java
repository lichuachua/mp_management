package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.mp_managementserver.dto.TokenInfo;
import cn.lichuachua.mp_management.mp_managementserver.repository.redis.IRedisRepository;
import cn.lichuachua.mp_management.mp_managementserver.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 李歘歘
 */
@Service
public class TokenServiceImpl implements ITokenService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    private IRedisRepository redisRepository;

    @Override
    public TokenInfo getTokenInfo(String accessToken) {
        TokenInfo tokenInfo = redisRepository.findTokenInfoByAccessToken(accessToken);
        return tokenInfo;
    }
}
