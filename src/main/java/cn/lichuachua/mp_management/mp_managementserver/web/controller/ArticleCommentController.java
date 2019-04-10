package cn.lichuachua.mp_management.mp_managementserver.web.controller;


import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleCommentService;
import cn.lichuachua.mp_management.mp_managementserver.vo.ArticleCommentListVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@Api(value = "ArticleCommentController", tags = {"评论API"})
@RestController
@RequestMapping(value = "/admin/artircle/comment")
public class ArticleCommentController extends BaseController<AdminInfoDTO> {

    @Autowired
    private IArticleCommentService articleCommentService;


    /**
     * 管理评论举报
     * @param operationId
     * @return
     */
    @ApiOperation("管理评论举报")
    @PutMapping("/publish/{operationId}")
    public ResultWrapper updatedStatus(
            @PathVariable(value = "operationId") String operationId){
        /**
         * 获取当前登录的用户Id
         */
        String adminId = getCurrentUserInfo().getUserId();

        Integer status = articleCommentService.updatedStatus(operationId, adminId);
        return ResultWrapper.successWithData(status);
    }

    /**
     * 正常评论列表
     * @return
     */
    @ApiOperation("正常评论列表")
    @GetMapping("queryNormalList")
    public ResultWrapper<List<ArticleCommentListVO>> queryNormalList(){
        /**
         * 显示正常文章列表
         */
        List<ArticleCommentListVO> articleCommentListVOList = articleCommentService.queryNormalList();
        return ResultWrapper.successWithData(articleCommentListVOList);
    }

    /**
     * 删除评论列表
     * @return
     */
    @ApiOperation("删除评论列表")
    @GetMapping("queryDeleteList")
    public ResultWrapper<List<ArticleCommentListVO>> queryDeleteList(){
        /**
         * 显示正常文章列表
         */
        List<ArticleCommentListVO> articleCommentListVOList = articleCommentService.queryDeleteList();
        return ResultWrapper.successWithData(articleCommentListVOList);
    }


    /**
     * 禁用评论列表
     * @return
     */
    @ApiOperation("禁用评论列表")
    @GetMapping("queryDisabledList")
    public ResultWrapper<List<ArticleCommentListVO>> queryDisabledList(){
        /**
         * 显示正常文章列表
         */
        List<ArticleCommentListVO> articleCommentListVOList = articleCommentService.queryDisabledList();
        return ResultWrapper.successWithData(articleCommentListVOList);
    }

}
