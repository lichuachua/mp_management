package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.*;
import cn.lichuachua.mp_management.mp_managementserver.enums.ArticleStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.CommentEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.InformOperationTypeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.ArticleException;
import cn.lichuachua.mp_management.mp_managementserver.exception.InformArticleException;
import cn.lichuachua.mp_management.mp_managementserver.exception.InformCommentException;
import cn.lichuachua.mp_management.mp_managementserver.service.*;
import cn.lichuachua.mp_management.mp_managementserver.vo.NoOperationCommentVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.OperationCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminCommentServiceImpl extends BaseServiceImpl<AdminComment, String> implements IAdminCommentService {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IArticleCommentService articleCommentService;

    @Override
    public void publish(String operationId, String adminId, Integer status){
        /**
         * 更新举报表
         */
        Optional<AdminComment> adminCommentOptional = selectByKey(operationId);
        if (!adminCommentOptional.isPresent()){
            throw new InformCommentException(ErrorCodeEnum.INFORM_NO_EXIT);
        }
        AdminComment adminComment = new AdminComment();
        adminComment.setCommentId(adminCommentOptional.get().getCommentId());
        adminComment.setCommentContent(adminCommentOptional.get().getCommentContent());
        adminComment.setCreatedAt(adminCommentOptional.get().getCreatedAt());
        adminComment.setInformedName(adminCommentOptional.get().getInformedName());
        adminComment.setInformedMobile(adminCommentOptional.get().getInformedMobile());
        adminComment.setInformedId(adminCommentOptional.get().getInformedId());
        adminComment.setInformerId(adminCommentOptional.get().getInformerId());
        adminComment.setInformerName(adminCommentOptional.get().getInformerName());
        adminComment.setUpdatedAt(new Date());
        adminComment.setOperationType(status);
        /**
         * 根据adminId找出adminMobile
         */
        Optional<Admin> adminOptional = adminService.selectByKey(adminId);
        adminComment.setAdminId(adminId);
        adminComment.setAdminMobile(adminOptional.get().getMobile());
        /**
         * 判断是否有管理员操作过，若有人操作过则直接新增
         *  没人操作过则直接修改
         */
        if (adminCommentOptional.get().getOperationType()==null){
            adminComment.setOperationId(operationId);
            update(adminComment);
        }else {
            save(adminComment);
        }
    }
    /**
     * 忽略评论举报
     * @param operationId
     * @param adminId
     */
    @Override
    public void ignore(String operationId, String adminId){
        /**
         * 查看举报是否存在，并且没人处理
         */
        AdminComment adminComment = new AdminComment();
        adminComment.setOperationId(operationId);
        Optional<AdminComment> adminArticleOptional = selectOne(Example.of(adminComment));
        if (!adminArticleOptional.isPresent()){
            throw new InformArticleException(ErrorCodeEnum.INFORM_NO_EXIT);
        }
        /**
         * 查看举报的评论是否存在
         */
        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentId(adminArticleOptional.get().getCommentId());
        articleComment.setStatus(CommentEnum.COMMENT_EXIT.getStatus());
        Optional<ArticleComment> articleCommentOptional = articleCommentService.selectOne(Example.of(articleComment));
        if (!articleCommentOptional.isPresent()){
            throw new ArticleException(ErrorCodeEnum.COMMENT_NO);
        }
        /**
         * 查看举是否有人处理过
         */
        if (adminArticleOptional.get().getOperationType()!=null){
            throw new InformArticleException(ErrorCodeEnum.INFORM_OPERATION);
        }
        /**
         * 更新举报列表，将忽略operationType更新为1
         */
        adminComment.setAdminId(adminId);
        adminComment.setUpdatedAt(new Date());
        adminComment.setInformerName(adminArticleOptional.get().getInformerName());
        adminComment.setInformerId(adminArticleOptional.get().getInformerId());
        adminComment.setInformedName(adminArticleOptional.get().getInformedName());
        adminComment.setInformedId(adminArticleOptional.get().getInformedId());
        adminComment.setInformedMobile(adminArticleOptional.get().getInformedMobile());
        adminComment.setCreatedAt(adminArticleOptional.get().getCreatedAt());
        adminComment.setCommentContent(articleCommentOptional.get().getContent());
        adminComment.setCommentId(articleCommentOptional.get().getCommentId());
        adminComment.setOperationType(InformOperationTypeEnum.IGNORE.getStatus());
        /**
         * 根据adminId找出adminMobile
         */
        Optional<Admin> adminOptional = adminService.selectByKey(adminId);
        adminComment.setAdminMobile(adminOptional.get().getMobile());
        update(adminComment);
    }

    /**
     * 显示未处理的评论举报
     * @return
     */
    @Override
    public List<NoOperationCommentVO> queryNoList(){
        List<AdminComment> adminCommentList = selectAll();
        List<NoOperationCommentVO> noOperationCommentVOList = new ArrayList<>();
        /**
         * 取出评论存在并且未处理的举报
         */
        for (AdminComment adminComment : adminCommentList){
            /**
             * 判断是否未处理，对未处理的文章进行操作
             */
            if (adminComment.getOperationType()==null){
                /**
                 * 判断评论是否存在
                 */
                ArticleComment articleComment = new ArticleComment();
                articleComment.setCommentId(adminComment.getCommentId());
                articleComment.setStatus(CommentEnum.COMMENT_EXIT.getStatus());
                Optional<ArticleComment> articleCommentOptional = articleCommentService.selectOne(Example.of(articleComment));
                if (articleCommentOptional.isPresent()){
                    NoOperationCommentVO noOperationCommentVO = new NoOperationCommentVO();
                    noOperationCommentVO.setCommentContent(adminComment.getCommentContent());
                    noOperationCommentVO.setCommentId(adminComment.getCommentId());
                    noOperationCommentVO.setCreatedAt(adminComment.getCreatedAt());
                    noOperationCommentVO.setInformedId(adminComment.getInformedId());
                    noOperationCommentVO.setInformedMobile(adminComment.getInformedMobile());
                    noOperationCommentVO.setInformedName(adminComment.getInformedName());
                    noOperationCommentVO.setInformerId(adminComment.getInformerId());
                    noOperationCommentVO.setInformerName(adminComment.getInformerName());
                    noOperationCommentVO.setOperationId(adminComment.getOperationId());
                    noOperationCommentVOList.add(noOperationCommentVO);
                }
            }
        }
        return noOperationCommentVOList;
    }

    /**
     * 显示处理过的文章举报
     * @return
     */
    @Override
    public List<OperationCommentVO> queryList(){
        List<AdminComment> adminCommentList = selectAll();
        List<OperationCommentVO> operationCommentVOList = new ArrayList<>();
        /**
         * 取出评论存在并且未处理的举报
         */
        for (AdminComment adminComment : adminCommentList){
            /**
             * 判断是否未处理，对未处理的文章进行操作
             */
            if (adminComment.getOperationType()!=null){
                    OperationCommentVO operationCommentVO = new OperationCommentVO();
                    operationCommentVO.setCommentContent(adminComment.getCommentContent());
                    operationCommentVO.setCommentId(adminComment.getCommentId());
                    operationCommentVO.setCreatedAt(adminComment.getCreatedAt());
                    operationCommentVO.setInformedId(adminComment.getInformedId());
                    operationCommentVO.setInformedMobile(adminComment.getInformedMobile());
                    operationCommentVO.setInformedName(adminComment.getInformedName());
                    operationCommentVO.setInformerId(adminComment.getInformerId());
                    operationCommentVO.setInformerName(adminComment.getInformerName());
                    operationCommentVO.setOperationId(adminComment.getOperationId());
                    operationCommentVO.setAdminId(adminComment.getAdminId());
                    operationCommentVO.setAdminMobile(adminComment.getAdminMobile());
                    operationCommentVO.setOperationType(adminComment.getOperationType());
                    operationCommentVOList.add(operationCommentVO);
                }
            }

        return operationCommentVOList;
    }
}