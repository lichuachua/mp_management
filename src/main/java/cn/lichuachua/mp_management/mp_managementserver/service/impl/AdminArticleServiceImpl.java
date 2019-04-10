package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.Admin;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminArticle;
import cn.lichuachua.mp_management.mp_managementserver.entity.Article;
import cn.lichuachua.mp_management.mp_managementserver.enums.ArticleStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.InformOperationTypeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.ArticleException;
import cn.lichuachua.mp_management.mp_managementserver.exception.InformArticleException;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminArticleService;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminService;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleService;
import cn.lichuachua.mp_management.mp_managementserver.vo.NoOperationArticleVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.OperationArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminArticleServiceImpl extends BaseServiceImpl<AdminArticle, String> implements IAdminArticleService {


    @Autowired
    private IAdminService adminService;
    @Autowired
    private IArticleService articleService;

    @Override
    public  void publish(String operationId, String adminId, Integer status){
        /**
         * 更新举报表
         */
        Optional<AdminArticle> adminArticleOptional = selectByKey(operationId);
        if (!adminArticleOptional.isPresent()){
            throw new InformArticleException(ErrorCodeEnum.INFORM_NO_EXIT);
        }
        AdminArticle adminArticle = new AdminArticle();
        adminArticle.setArticleId(adminArticleOptional.get().getArticleId());
        adminArticle.setArticleTitle(adminArticleOptional.get().getArticleTitle());
        adminArticle.setInformedId(adminArticleOptional.get().getInformedId());
        adminArticle.setInformedName(adminArticleOptional.get().getInformedName());
        adminArticle.setInformedMobile(adminArticleOptional.get().getInformedMobile());
        adminArticle.setInformerId(adminArticleOptional.get().getInformerId());
        adminArticle.setInformerName(adminArticleOptional.get().getInformerName());
        adminArticle.setCreatedAt(adminArticleOptional.get().getCreatedAt());
        adminArticle.setUpdatedAt(new Date());
        adminArticle.setAdminId(adminId);
        adminArticle.setOperationType(status);
        /**
         * 根据adminId找出adminMobile
         */
        Optional<Admin> adminOptional = adminService.selectByKey(adminId);
        adminArticle.setAdminMobile(adminOptional.get().getMobile());
        /**
         * 判断是否有管理员操作过，若有人操作过则直接新增
         *  没人操作过则直接修改
         */
        if (adminArticleOptional.get().getOperationType()==null){
            adminArticle.setOperationId(operationId);
            update(adminArticle);
        }else {
            save(adminArticle);
        }
    }

    /**
     * 忽略文章举报
     * @param operationId
     * @param adminId
     */
    @Override
    public void ignore(String operationId, String adminId){
        /**
         * 查看举报是否存在，并且没人处理
         */
        AdminArticle adminArticle = new AdminArticle();
        adminArticle.setOperationId(operationId);
        Optional<AdminArticle> adminArticleOptional = selectOne(Example.of(adminArticle));
        if (!adminArticleOptional.isPresent()){
            throw new InformArticleException(ErrorCodeEnum.INFORM_NO_EXIT);
        }
        /**
         * 查看举报的文章是否存在
         */
        Article article = new Article();
        article.setArticleId(adminArticleOptional.get().getArticleId());
        article.setStatus(ArticleStatusEnum.NORMAL.getStatus());
        Optional<Article> articleOptional = articleService.selectOne(Example.of(article));
        if (!articleOptional.isPresent()){
            throw new ArticleException(ErrorCodeEnum.ARTICLE_NO_EXIT);
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
        adminArticle.setArticleId(adminArticleOptional.get().getArticleId());
        adminArticle.setArticleTitle(adminArticleOptional.get().getArticleTitle());
        adminArticle.setInformedId(adminArticleOptional.get().getInformedId());
        adminArticle.setInformedName(adminArticleOptional.get().getInformedName());
        adminArticle.setInformedMobile(adminArticleOptional.get().getInformedMobile());
        adminArticle.setInformerId(adminArticleOptional.get().getInformerId());
        adminArticle.setInformerName(adminArticleOptional.get().getInformerName());
        adminArticle.setCreatedAt(adminArticleOptional.get().getCreatedAt());
        adminArticle.setUpdatedAt(new Date());
        adminArticle.setAdminId(adminId);
        adminArticle.setOperationType(InformOperationTypeEnum.IGNORE.getStatus());
        /**
         * 根据adminId找出adminMobile
         */
        Optional<Admin> adminOptional = adminService.selectByKey(adminId);
        adminArticle.setAdminMobile(adminOptional.get().getMobile());
        update(adminArticle);
    }

    /**
     * 显示未处理的文章举报
     * @return
     */
    @Override
    public List<NoOperationArticleVO> queryNoList(){
        List<AdminArticle> adminArticleList = selectAll();
        List<NoOperationArticleVO> noOperationArticleVOList = new ArrayList<>();
        /**
         * 取出文章存在，并且未处理的举报
         */
        for (AdminArticle adminArticle : adminArticleList) {
            /**
             *  判断是否未处理，对未处理的文章进行操作
             */
            if (adminArticle.getOperationType()==null){
                /**
                 * 判断文章是否存在
                 */
                Article article = new Article();
                article.setArticleId(adminArticle.getArticleId());
                article.setStatus(ArticleStatusEnum.NORMAL.getStatus());
                Optional<Article> articleOptional = articleService.selectOne(Example.of(article));
                if (articleOptional.isPresent()){
                    NoOperationArticleVO noOperationArticleVO = new NoOperationArticleVO();
                    noOperationArticleVO.setArticleId(adminArticle.getArticleId());
                    noOperationArticleVO.setArticleTitle(adminArticle.getArticleTitle());
                    noOperationArticleVO.setCreatedAt(adminArticle.getCreatedAt());
                    noOperationArticleVO.setInformedId(adminArticle.getInformedId());
                    noOperationArticleVO.setInformedMobile(adminArticle.getInformedMobile());
                    noOperationArticleVO.setInformedName(adminArticle.getInformedName());
                    noOperationArticleVO.setInformerId(adminArticle.getInformerId());
                    noOperationArticleVO.setInformerName(adminArticle.getInformerName());
                    noOperationArticleVO.setOperationId(adminArticle.getOperationId());
                    noOperationArticleVOList.add(noOperationArticleVO);
                }
            }
        }
        return noOperationArticleVOList;
    }


    /**
     * 显示处理过的文章举报
      * @return
     */
    @Override
    public List<OperationArticleVO> queryList(){
        List<AdminArticle> adminArticleList = selectAll();
        List<OperationArticleVO> operationArticleVOList = new ArrayList<>();
        /**
         * 取出文章存在，并且处理的举报
         */
        for (AdminArticle adminArticle : adminArticleList) {
            /**
             *  判断是否未处理，对未处理的文章进行操作
             */
            if (adminArticle.getOperationType()!=null){
                /**
                 * 不必判断文章是否存在
                 */
                    OperationArticleVO operationArticleVO = new OperationArticleVO();
                    operationArticleVO.setArticleId(adminArticle.getArticleId());
                    operationArticleVO.setArticleTitle(adminArticle.getArticleTitle());
                    operationArticleVO.setCreatedAt(adminArticle.getCreatedAt());
                    operationArticleVO.setInformedId(adminArticle.getInformedId());
                    operationArticleVO.setInformedMobile(adminArticle.getInformedMobile());
                    operationArticleVO.setInformedName(adminArticle.getInformedName());
                    operationArticleVO.setInformerId(adminArticle.getInformerId());
                    operationArticleVO.setInformerName(adminArticle.getInformerName());
                    operationArticleVO.setOperationId(adminArticle.getOperationId());
                    operationArticleVO.setAdminId(adminArticle.getAdminId());
                    operationArticleVO.setAdminMobile(adminArticle.getAdminMobile());
                    operationArticleVO.setOperationType(adminArticle.getOperationType());
                    operationArticleVOList.add(operationArticleVO);
            }
        }
        return operationArticleVOList;
    }

}