package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李歘歘
 */
@Data
public class AnnouncementNormalListVO {
    private String announcementId;

    private String publisherNick;

    private String publisherAvatar;

    private String content;

    private String title;

    private Integer rank;

    private String announcementType;

    private String accessory;

    private Date updatedAt;


}
