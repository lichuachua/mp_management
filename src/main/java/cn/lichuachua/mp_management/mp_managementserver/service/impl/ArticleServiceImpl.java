package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.Admin;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminArticle;
import cn.lichuachua.mp_management.mp_managementserver.entity.Article;
import cn.lichuachua.mp_management.mp_managementserver.entity.ArticleType;
import cn.lichuachua.mp_management.mp_managementserver.enums.ArticleStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ArticleVisualEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.ArticleException;
import cn.lichuachua.mp_management.mp_managementserver.exception.InformArticleException;
import cn.lichuachua.mp_management.mp_managementserver.form.AnnouncementPublishForm;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminArticleService;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminService;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleService;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleTypeService;
import cn.lichuachua.mp_management.mp_managementserver.vo.ArticleListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 李歘歘
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article, String> implements IArticleService {


    @Autowired
    private IAdminArticleService adminArticleService;
    @Autowired
    private IArticleTypeService articleTypeService;
    @Autowired
    private IAdminService adminService;


    /**
     * 处理文章举报
     * @param operationId
     * @param adminId
     */
    @Override
    public Integer updatedStatus(String operationId, String adminId) {

        /**
         * 取出被举报文章
         */
        Optional<AdminArticle> adminArticleOptional = adminArticleService.selectByKey(operationId);
        if (!adminArticleOptional.isPresent()){
            throw new InformArticleException(ErrorCodeEnum.INFORM_NO_EXIT);
        }
        /**
         * 判断文章状态
         *  0》》=1
         *  1》》=0
         */
        Article article = new Article();
        article.setArticleId(adminArticleOptional.get().getArticleId());
        Optional<Article> articleOptional = selectOne(Example.of(article)) ;
        if (!articleOptional.isPresent()){
            throw new ArticleException(ErrorCodeEnum.ARTICLE_NO_EXIT);
        }
        article.setPublisherId(articleOptional.get().getPublisherId());
        article.setPublisherNick(articleOptional.get().getPublisherNick());
        article.setPublisherAvatar(articleOptional.get().getPublisherAvatar());
        article.setAccessory(articleOptional.get().getAccessory());
        article.setArticleType(articleOptional.get().getArticleType());
        article.setContent(articleOptional.get().getContent());
        article.setTitle(articleOptional.get().getTitle());
        article.setVisual(articleOptional.get().getVisual());
        article.setUpdatedAt(new Date());
        article.setRank(articleOptional.get().getRank());
        article.setCreatedAt(articleOptional.get().getCreatedAt());
        if (articleOptional.get().getStatus().equals(ArticleStatusEnum.NORMAL.getStatus())){
            article.setStatus(ArticleStatusEnum.DISABLED.getStatus());
            update(article);
            /**
             * 写入文章状态日志
             */
            adminArticleService.publish(operationId, adminId,ArticleStatusEnum.DISABLED.getStatus());
            return 1;
        }else if (articleOptional.get().getStatus().equals(ArticleStatusEnum.DISABLED.getStatus())){
            article.setStatus(ArticleStatusEnum.NORMAL.getStatus());
            update(article);
            adminArticleService.publish(operationId, adminId, ArticleStatusEnum.NORMAL.getStatus());
            return 0;
        }else {
            throw new ArticleException(ErrorCodeEnum.ARTICLE_NO_EXIT);
        }
    }

    /**
     * 正常文章列表
     * @return
     */
    @Override
    public List<ArticleListVO> queryNormalList(){
        List<Article> articleList = selectAll();
        List<ArticleListVO> articleListVOList = new ArrayList<>();
        for (Article article : articleList){
            ArticleListVO articleListVO = new ArticleListVO();
            /**
             * 文章属于正常状态并且等级为空
             */
            if (article.getStatus().equals(ArticleStatusEnum.NORMAL.getStatus())&&article.getRank()==null){
                articleListVO.setArticleId(article.getArticleId());
                articleListVO.setPublisherAvatar(article.getPublisherAvatar());
                articleListVO.setPublisherNick(article.getPublisherNick());
                articleListVO.setTitle(article.getTitle());
                articleListVO.setUpdatedAt(article.getUpdatedAt());
                articleListVO.setVisual(article.getVisual());
                /**
                 * 根据typeId取出对应的类型名
                 */
                ArticleType articleType = new ArticleType();
                articleType.setTypeId(article.getArticleType());
                Optional<ArticleType> articleTypeOptional = articleTypeService.selectOne(Example.of(articleType));
                if (articleTypeOptional.isPresent()){
                    articleListVO.setArticleType(articleTypeOptional.get().getTypeName());
                }else {
                    articleListVO.setArticleType(null);
                }
                articleListVOList.add(articleListVO);
            }
        }
        return articleListVOList;
    }


    /***
     * 删除文章列表
     * @return
     */
    @Override
    public List<ArticleListVO> queryDeleteList(){
        List<Article> articleList = selectAll();
        List<ArticleListVO> articleListVOList = new ArrayList<>();
        for (Article article : articleList){
            ArticleListVO articleListVO = new ArticleListVO();
            /**
             * 文章属于删除状态并且等级为空
             */
            if (article.getStatus().equals(ArticleStatusEnum.DELETED.getStatus())&&article.getRank()==null){
                articleListVO.setArticleId(article.getArticleId());
                articleListVO.setPublisherAvatar(article.getPublisherAvatar());
                articleListVO.setPublisherNick(article.getPublisherNick());
                articleListVO.setTitle(article.getTitle());
                articleListVO.setUpdatedAt(article.getUpdatedAt());
                articleListVO.setVisual(article.getVisual());
                /**
                 * 根据typeId取出对应的类型名
                 */
                ArticleType articleType = new ArticleType();
                articleType.setTypeId(article.getArticleType());
                Optional<ArticleType> articleTypeOptional = articleTypeService.selectOne(Example.of(articleType));
                if (articleTypeOptional.isPresent()){
                    articleListVO.setArticleType(articleTypeOptional.get().getTypeName());
                }else {
                    articleListVO.setArticleType(null);
                }
                articleListVOList.add(articleListVO);
            }
        }
        return articleListVOList;
    }


    /**
     * 文章禁用列表
     * @return
     */
    @Override
    public List<ArticleListVO> queryDisabledList(){
        List<Article> articleList = selectAll();
        List<ArticleListVO> articleListVOList = new ArrayList<>();
        for (Article article : articleList){
            ArticleListVO articleListVO = new ArticleListVO();
            /**
             * 文章属于禁用状态
             */
            if (article.getStatus().equals(ArticleStatusEnum.DISABLED.getStatus())&&article.getRank()==null){
                articleListVO.setArticleId(article.getArticleId());
                articleListVO.setPublisherAvatar(article.getPublisherAvatar());
                articleListVO.setPublisherNick(article.getPublisherNick());
                articleListVO.setTitle(article.getTitle());
                articleListVO.setUpdatedAt(article.getUpdatedAt());
                articleListVO.setVisual(article.getVisual());
                /**
                 * 根据typeId取出对应的类型名并且等级为空
                 */
                ArticleType articleType = new ArticleType();
                articleType.setTypeId(article.getArticleType());
                Optional<ArticleType> articleTypeOptional = articleTypeService.selectOne(Example.of(articleType));
                if (articleTypeOptional.isPresent()){
                    articleListVO.setArticleType(articleTypeOptional.get().getTypeName());
                }else {
                    articleListVO.setArticleType(null);
                }
                articleListVOList.add(articleListVO);
            }
        }
        return articleListVOList;
    }




}