package cn.lichuachua.mp_management.mp_managementserver.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author  李歘歘
 *  联合主键
 */
@Data
public class ArticleCollectPK implements Serializable {
    /**
     * 文章Id
     */
    private String articleId;
    /**
     * 用户Id
     */
    private String userId;
}
