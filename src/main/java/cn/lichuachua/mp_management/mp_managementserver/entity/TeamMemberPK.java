package cn.lichuachua.mp_management.mp_managementserver.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李歘歘
 */
@Data
public class TeamMemberPK implements Serializable{
    /**
     * 队伍Id
     */
    private String teamId;

    /**
     * 用户Id
     */
    private String userId;
}
