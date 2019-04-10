package cn.lichuachua.mp_management.mp_managementserver.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李歘歘
 * 联合主键
 */
@Data
public class FollowPK implements Serializable {
    /**
     * 关注者ID
     */
    private String userId;
    /**
     * 被关注者Id
     */
    private String attentionId;
}
