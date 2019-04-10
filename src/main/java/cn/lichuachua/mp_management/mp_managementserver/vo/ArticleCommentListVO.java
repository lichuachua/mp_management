package cn.lichuachua.mp_management.mp_managementserver.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 李宇豪
 */
@Data
public class ArticleCommentListVO {

    private String publisherId;

    private String publisherAvatar;

    private String commentId;

    private String publisherName;

    private String content;

    private Date createdAt;

}
