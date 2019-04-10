package cn.lichuachua.mp_management.mp_managementserver.dto;

import lombok.Data;

/**
 * @author 李歘歘
 */
@Data
public class ClientInfo {

    private String accessToken;

    private String userId;

    private Long mostSignBits;

    private Long leastSignBits;
}
