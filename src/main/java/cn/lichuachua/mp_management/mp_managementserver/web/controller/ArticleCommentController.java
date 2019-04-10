package cn.lichuachua.mp_management.mp_managementserver.web.controller;


import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleCommentService;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("管理文章举报")
    @PutMapping("/publish/{operationId}")
    public ResultWrapper updatedStatus(
            @PathVariable(value = "operationId") String operationId){
        /**
         * 获取当前登录的用户Id
         */String adminId = getCurrentUserInfo().getUserId();

        Integer status = articleCommentService.updatedStatus(operationId, adminId);
        return ResultWrapper.successWithData(status);

    }

}
