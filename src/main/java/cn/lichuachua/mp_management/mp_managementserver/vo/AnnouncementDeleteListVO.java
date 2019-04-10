package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class AnnouncementDeleteListVO {

    private String publisherNick;

    private String publisherAvatar;

    private String content;

    private String title;

    private String accessory;

    private String deleterId;

    private String announcementType;

    private String deleterMobile;

    private Integer rank;

    private Date updatedAt;


}
