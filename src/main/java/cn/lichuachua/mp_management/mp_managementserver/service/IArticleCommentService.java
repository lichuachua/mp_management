package cn.lichuachua.mp_management.mp_managementserver.service;
import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.ArticleComment;
import io.swagger.models.auth.In;

import javax.validation.Valid;

public interface IArticleCommentService extends IBaseService<ArticleComment, String> {
    /**
     * 处理评论和举报
     * @param operationId
     * @param adminId
     */
    Integer updatedStatus(String operationId, String adminId);
}