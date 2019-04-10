package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class NoOperationArticleVO {

    private String operationId;

    private String articleId;

    private String articleTitle;

    private String informedMobile;

    private String informedName;

    private String informedId;

    private String informerName;

    private String informerId;

    private Date createdAt;

}
