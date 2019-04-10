package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminComment;
import cn.lichuachua.mp_management.mp_managementserver.entity.Article;
import cn.lichuachua.mp_management.mp_managementserver.entity.ArticleComment;
import cn.lichuachua.mp_management.mp_managementserver.enums.CommentEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.ArticleCommentException;
import cn.lichuachua.mp_management.mp_managementserver.exception.InformCommentException;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminCommentService;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleCommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ArticleCommentServiceImpl extends BaseServiceImpl<ArticleComment, String> implements IArticleCommentService {

    @Autowired
    private IAdminCommentService adminCommentService;

    @Override
    public Integer updatedStatus(String operationId, String adminId) {
        /**
         * 取出被举报评论
         */
        Optional<AdminComment> adminCommentOptional = adminCommentService.selectByKey(operationId);
        if (!adminCommentOptional.isPresent()){
            throw new InformCommentException(ErrorCodeEnum.INFORM_NO_EXIT);
        }
        /**
         * 判断评论状态
         *  0》》=-1
         *  -1》》=0
         */
        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentId(adminCommentOptional.get().getCommentId());
        Optional<ArticleComment> articleCommentOptional = selectOne(Example.of(articleComment));
        if (!articleCommentOptional.isPresent()){
            throw new ArticleCommentException(ErrorCodeEnum.COMMENT_NO);
        }
        articleComment.setStatus(CommentEnum.COMMENT_DISABLE.getStatus());
        articleComment.setCommentUserId(articleCommentOptional.get().getCommentUserId());
        articleComment.setContent(articleCommentOptional.get().getContent());
        articleComment.setCreatedAt(articleCommentOptional.get().getCreatedAt());
        articleComment.setDepth(articleCommentOptional.get().getDepth());
        articleComment.setParentId(articleCommentOptional.get().getParentId());
        articleComment.setThread(articleCommentOptional.get().getThread());
        articleComment.setUpdatedAt(new Date());
        if (articleCommentOptional.get().getStatus().equals(CommentEnum.COMMENT_EXIT.getStatus())){
            articleComment.setStatus(CommentEnum.COMMENT_DISABLE.getStatus());
            update(articleComment);
            /**
             * 写入评论状态日志
             */
            adminCommentService.publish(operationId, adminId,CommentEnum.COMMENT_DISABLE.getStatus());
            return 1;
        }else if (articleCommentOptional.get().getStatus().equals(CommentEnum.COMMENT_DISABLE.getStatus())){
            articleComment.setStatus(CommentEnum.COMMENT_EXIT.getStatus());
            update(articleComment);
            /**
             * 写入评论状态日志
             */
            adminCommentService.publish(operationId, adminId,CommentEnum.COMMENT_EXIT.getStatus());
            return 0;
        }else {
            throw new ArticleCommentException(ErrorCodeEnum.COMMENT_NO);
        }





    }


}