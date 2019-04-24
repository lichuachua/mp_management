package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminComment;
import cn.lichuachua.mp_management.mp_managementserver.vo.NoOperationCommentVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.OperationCommentVO;

import java.util.List;

public interface IAdminCommentService extends IBaseService<AdminComment, String> {

    /**
     * 写入评论状态日志
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
     * 显示未处理的评论举报
     * @return
     */
    List<NoOperationCommentVO> queryNoList();

    /**
     * 显示处理过的文章举报
     * @return
     */
    List<OperationCommentVO> queryList();

    /**
     * 写入日志
     * @param adminId
     * @param commentId
     * @param status
     */
    void addLog(String adminId, String commentId, Integer status);

}
