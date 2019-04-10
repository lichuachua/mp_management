package cn.lichuachua.mp_management.mp_managementserver.dto;

import lombok.Data;

/**
 * @author 李歘歘
 * 用户登录的Token
 */
@Data
public class TokenInfo {
    /**
     * 本次登录的Token
     */
    private String accessToken;
    /**
     * 用户Id
     */
    private String userId;
}
