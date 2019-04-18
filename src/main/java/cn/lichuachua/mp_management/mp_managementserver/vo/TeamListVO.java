package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class TeamListVO {
    private String teamId;

    private String teamName;

    private String headerNick;

    private String description;

    private String headerAvatar;

    private String type;

    private String visual;

    private Date createdAt;


}
