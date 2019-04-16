package cn.lichuachua.mp_management.mp_managementserver.web.controller;


import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleService;
import cn.lichuachua.mp_management.mp_managementserver.vo.ArticleListVO;
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
@Api(value = "ArticleController",tags = {"文章API"})
@RestController
@RequestMapping(value = "/admin/article")
public class ArticleController extends BaseController<AdminInfoDTO> {
    @Autowired
    private IArticleService articleService;

    /**
     * 管理文章举报
     * @param operationId
     * @return
     */
    @ApiOperation("管理文章举报")
    @PutMapping("/publish/{operationId}")
    public ResultWrapper<Integer> updatedStatus(
            @PathVariable(value = "operationId") String operationId){
        /**
         * 获取当前登录的adminId
         */
        String adminId = getCurrentUserInfo().getUserId();
         Integer status = articleService.updatedStatus(operationId, adminId);
         return ResultWrapper.successWithData(status);
    }

    /**
     * 正常文章列表
     * @return
     */
    @ApiOperation("正常文章列表")
    @GetMapping("/queryNormalList")
    public ResultWrapper<List<ArticleListVO>> queryNormalList(){
        /**
         * 显示正常文章列表
         */
        List<ArticleListVO> articleListVOList = articleService.queryNormalList();
        return ResultWrapper.successWithData(articleListVOList);
    }

    /**
     * 删除文章列表
     * @return
     */
    @ApiOperation("删除文章列表")
    @GetMapping("/queryDeleteList")
    public ResultWrapper<List<ArticleListVO>> queryDeleteList(){
        /**
         * 显示正常文章列表
         */
        List<ArticleListVO> articleListVOList = articleService.queryDeleteList();
        return ResultWrapper.successWithData(articleListVOList);
    }

    /**
     * 禁用文章列表
     * @return
     */
    @ApiOperation("禁用文章列表")
    @GetMapping("/queryDisabledList")
    public ResultWrapper<List<ArticleListVO>> queryDisabledList(){
        /**
         * 显示正常文章列表
         */
        List<ArticleListVO> articleListVOList = articleService.queryDisabledList();
        return ResultWrapper.successWithData(articleListVOList);
    }



}
