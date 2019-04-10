package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.mp_managementserver.dto.TokenInfo;

/**
 * @author 李歘歘
 */
public interface ITokenService {
    /**
     * 从redis获取token信息
     * @param accessToken
     * @return
     */
    TokenInfo getTokenInfo(String accessToken);
}
