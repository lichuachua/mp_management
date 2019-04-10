package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.Date;

@Data
public class FollowVO {
    private String followerId;

    private String followerAvatar;

    private String followerNick;

    private Date followerCreatedAt;
}
