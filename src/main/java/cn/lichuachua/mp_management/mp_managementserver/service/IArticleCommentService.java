package cn.lichuachua.mp_management.mp_managementserver.service;
import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.ArticleComment;
import cn.lichuachua.mp_management.mp_managementserver.vo.ArticleCommentListVO;
import io.swagger.models.auth.In;

import javax.validation.Valid;
import java.util.List;

public interface IArticleCommentService extends IBaseService<ArticleComment, String> {
    /**
     * 处理评论和举报
     * @param operationId
     * @param adminId
     */
    Integer updatedStatus(String operationId, String adminId);

    /**
     * 正常评论列表
     * @return
     */
    List<ArticleCommentListVO> queryNormalList();

    /**
     * 删除评论列表
     * @return
     */
    List<ArticleCommentListVO> queryDeleteList();

    /**
     * 禁用评论列表
     * @return
     */
    List<ArticleCommentListVO> queryDisabledList();

    /**
     *根据评论状态对评论进行操作
     * @param adminId
     * @param commentId
     * @param status
     */
    void updatedArticleStatus(String adminId, String commentId, Integer status);
}