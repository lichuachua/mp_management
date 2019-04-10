package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李歘歘
 */
@Data
public class AnnouncementNormalVO {

    private String publisherNick;

    private String publisherId;

    private String publisherAvatar;

    private String announcementType;

    private String title;

    private String content;

    private String accessory;

    private Integer rank;

    private Date updatedAt;


}
