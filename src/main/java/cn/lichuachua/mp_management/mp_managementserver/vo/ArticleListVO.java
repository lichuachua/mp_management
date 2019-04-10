package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class ArticleListVO {
    private String articleId;

    private String publisherNick;

    private String publisherAvatar;

    private String articleType;

    private String title;

    private Integer visual;

    private Date updatedAt;


}
