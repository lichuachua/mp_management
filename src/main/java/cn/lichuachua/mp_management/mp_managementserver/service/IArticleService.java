package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.Article;
import cn.lichuachua.mp_management.mp_managementserver.form.AnnouncementPublishForm;
import cn.lichuachua.mp_management.mp_managementserver.vo.ArticleListVO;

import javax.validation.Valid;
import java.util.List;


/**
 * @author 李歘歘
 */
public interface IArticleService extends IBaseService<Article, String> {
    /**
     * 处理文章和举报
     * @param operationId
     * @param adminId
     */
    Integer updatedStatus(String operationId, String adminId);

    /**
     * 正常文章列表
     * @return
     */
    List<ArticleListVO> queryNormalList();

    /**
     * 删除文章列表
     * @return
     */
    List<ArticleListVO> queryDeleteList();

    /**
     * 文章禁用列表
     * @return
     */
    List<ArticleListVO> queryDisabledList();

    /**
     * 根据文章状态对文章进行操作
     * @param adminId
     * @param articleId
     * @param status
     */
    void updatedArticleStatus(String adminId, String articleId, Integer status);

}