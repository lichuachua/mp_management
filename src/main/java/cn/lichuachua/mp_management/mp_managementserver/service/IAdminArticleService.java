package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminArticle;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminUser;
import cn.lichuachua.mp_management.mp_managementserver.vo.NoOperationArticleVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.OperationArticleVO;

import java.util.List;

public interface IAdminArticleService extends IBaseService<AdminArticle, String> {

    /**
     * 管理文章举报
     * @param operationId
     * @param adminId
     * @param status
     */
    void publish(String operationId, String adminId, Integer status);

    /**
     * 忽略文章举报
     * @param operationId
     * @param adminId
     */
    void ignore(String operationId, String adminId);

    /**
     * 显示未处理的文章举报
     * @return
     */
    List<NoOperationArticleVO> queryNoList();

    /**
     * 显示处理过的文章举报
     * @return
     */
    List<OperationArticleVO> queryList();



}
