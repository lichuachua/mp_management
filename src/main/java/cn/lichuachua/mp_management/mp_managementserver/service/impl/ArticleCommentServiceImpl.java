package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminComment;
import cn.lichuachua.mp_management.mp_managementserver.entity.Article;
import cn.lichuachua.mp_management.mp_managementserver.entity.ArticleComment;
import cn.lichuachua.mp_management.mp_managementserver.entity.User;
import cn.lichuachua.mp_management.mp_managementserver.enums.CommentEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.ArticleCommentException;
import cn.lichuachua.mp_management.mp_managementserver.exception.InformCommentException;
import cn.lichuachua.mp_management.mp_managementserver.exception.UserException;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminCommentService;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleCommentService;

import cn.lichuachua.mp_management.mp_managementserver.service.IUserService;
import cn.lichuachua.mp_management.mp_managementserver.vo.ArticleCommentListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleCommentServiceImpl extends BaseServiceImpl<ArticleComment, String> implements IArticleCommentService {

    @Autowired
    private IAdminCommentService adminCommentService;
    @Autowired
    private IUserService userService;

    @Autowired
    private IArticleCommentService articleCommentService;

    /**
     * 管理评论举报
     * @param operationId
     * @param adminId
     * @return
     */
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


    /**
     * 正常评论列表
     * @return
     */
    @Override
    public List<ArticleCommentListVO> queryNormalList(){
        List<ArticleComment> articleCommentList = selectAll();
        List<ArticleCommentListVO> articleCommentListVOList = new ArrayList<>();
        for (ArticleComment articleComment : articleCommentList ){
            ArticleCommentListVO articleCommentListVO = new ArticleCommentListVO();
            /**
             * 评论属于正常状态
             */
            if (articleComment.getStatus().equals(CommentEnum.COMMENT_EXIT.getStatus())){
                articleCommentListVO.setPublisherId(articleComment.getCommentUserId());
                articleCommentListVO.setCommentId(articleComment.getCommentId());
                articleCommentListVO.setContent(articleComment.getContent());
                articleCommentListVO.setCreatedAt(articleComment.getCreatedAt());
                /**
                 * 根据publisherID求出publisherName
                 */
                Optional<User> userOptional = userService.selectByKey(articleComment.getCommentUserId());
                if (!userOptional.isPresent()){
                    throw new UserException(ErrorCodeEnum.ERROR_USER);
                }
                articleCommentListVO.setPublisherName(userOptional.get().getUserName());
                articleCommentListVO.setPublisherAvatar(userOptional.get().getUserAvatar());
                articleCommentListVOList.add(articleCommentListVO);
            }
        }
        return articleCommentListVOList;
    }


    /**
     * 删除评论列表
     * @return
     */
    @Override
    public List<ArticleCommentListVO> queryDeleteList(){
        List<ArticleComment> articleCommentList = selectAll();
        List<ArticleCommentListVO> articleCommentListVOList = new ArrayList<>();
        for (ArticleComment articleComment : articleCommentList ){
            ArticleCommentListVO articleCommentListVO = new ArticleCommentListVO();
            /**
             * 评论属于正常状态
             */
            if (articleComment.getStatus().equals(CommentEnum.COMMENT_NO_EXIT.getStatus())){
                articleCommentListVO.setPublisherId(articleComment.getCommentUserId());
                articleCommentListVO.setCommentId(articleComment.getCommentId());
                articleCommentListVO.setContent(articleComment.getContent());
                articleCommentListVO.setCreatedAt(articleComment.getCreatedAt());
                /**
                 * 根据publisherID求出publisherName
                 */
                Optional<User> userOptional = userService.selectByKey(articleComment.getCommentUserId());
                if (!userOptional.isPresent()){
                    throw new UserException(ErrorCodeEnum.ERROR_USER);
                }
                articleCommentListVO.setPublisherName(userOptional.get().getUserName());
                articleCommentListVO.setPublisherAvatar(userOptional.get().getUserAvatar());
                articleCommentListVOList.add(articleCommentListVO);
            }
        }
        return articleCommentListVOList;
    }


    /**
     * 禁用评论列表
     * @return
     */
    @Override
    public List<ArticleCommentListVO> queryDisabledList(){
        List<ArticleComment> articleCommentList = selectAll();
        List<ArticleCommentListVO> articleCommentListVOList = new ArrayList<>();
        for (ArticleComment articleComment : articleCommentList ){
            ArticleCommentListVO articleCommentListVO = new ArticleCommentListVO();
            /**
             * 评论属于正常状态
             */
            if (articleComment.getStatus().equals(CommentEnum.COMMENT_DISABLE.getStatus())){
                articleCommentListVO.setPublisherId(articleComment.getCommentUserId());
                articleCommentListVO.setCommentId(articleComment.getCommentId());
                articleCommentListVO.setContent(articleComment.getContent());
                articleCommentListVO.setCreatedAt(articleComment.getCreatedAt());
                /**
                 * 根据publisherID求出publisherName
                 */
                Optional<User> userOptional = userService.selectByKey(articleComment.getCommentUserId());
                if (!userOptional.isPresent()){
                    throw new UserException(ErrorCodeEnum.ERROR_USER);
                }
                articleCommentListVO.setPublisherName(userOptional.get().getUserName());
                articleCommentListVO.setPublisherAvatar(userOptional.get().getUserAvatar());
                articleCommentListVOList.add(articleCommentListVO);
            }
        }
        return articleCommentListVOList;
    }

    @Override
    public void updatedArticleStatus(String adminId, String commentId, Integer status){
        /**
         * 查看评论是否存在
         */
        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentId(commentId);
        articleComment.setStatus(status);
        Optional<ArticleComment> articleCommentOptional = selectOne(Example.of(articleComment));
        if (!articleCommentOptional.isPresent()){
            throw new ArticleCommentException(ErrorCodeEnum.COMMENT_NO);
        }
        /**
         * 更改评论状态
         */
        if (status.equals(CommentEnum.COMMENT_EXIT.getStatus())){
            articleComment.setStatus(CommentEnum.COMMENT_DISABLE.getStatus());
        }else if (status.equals(CommentEnum.COMMENT_DISABLE.getStatus())){
            articleComment.setStatus(CommentEnum.COMMENT_EXIT.getStatus());
        }
        articleComment.setUpdatedAt(new Date());
        articleComment.setThread(articleCommentOptional.get().getThread());
        articleComment.setParentId(articleCommentOptional.get().getParentId());
        articleComment.setDepth(articleCommentOptional.get().getDepth());
        articleComment.setCreatedAt(articleCommentOptional.get().getCreatedAt());
        articleComment.setContent(articleCommentOptional.get().getContent());
        articleComment.setCommentUserId(articleCommentOptional.get().getCommentUserId());

        /**
         * 写入日志
         */
        adminCommentService.addLog(adminId,commentId,status);
        /**
         * 更新状态
         */
        update(articleComment);
    }


}