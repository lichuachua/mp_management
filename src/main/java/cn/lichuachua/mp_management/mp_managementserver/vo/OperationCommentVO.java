package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class OperationCommentVO {
    private String operationId;

    private String commentId;

    private String commentContent;

    private String informedMobile;

    private String informedName;

    private String informedId;

    private String informerName;

    private String informerId;

    private String adminId;

    private String adminMobile;

    private Integer operationType;

    private Date createdAt;

}
