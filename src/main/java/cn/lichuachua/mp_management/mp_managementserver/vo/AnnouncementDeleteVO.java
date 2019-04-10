package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class AnnouncementDeleteVO {

    private String publisherNick;

    private String publisherAvatar;

    private String publisherId;

    private String content;

    private Integer rank;

    private String title;

    private String accessory;

    private String deleterId;

    private String announcementType;

    private String deleterMobile;

    private Date updatedAt;


}
