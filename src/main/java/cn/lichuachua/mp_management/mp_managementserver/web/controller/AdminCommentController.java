package cn.lichuachua.mp_management.mp_managementserver.web.controller;


import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminCommentService;
import cn.lichuachua.mp_management.mp_managementserver.vo.NoOperationCommentVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.OperationCommentVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.NO_IMPLEMENT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://127.0.0.1:8080", maxAge = 3600)
@Api(value = "AdminCommentController", tags = {"管理评论日志API"})
@RestController
@RequestMapping(value = "/admin/comment/log")
public class AdminCommentController extends BaseController<AdminInfoDTO> {

    @Autowired
    private IAdminCommentService adminCommentService;
    /**
     * 忽略文章举报
     * @param operationId
     * @return
     */
    @ApiOperation("忽略评论举报")
    @PutMapping("/ignore/{operationId}")
    public ResultWrapper ignore(
            @PathVariable(value = "operationId" ) String operationId ){
        /**
         * 获取当前的登录的adminId
         */
        String adminId = getCurrentUserInfo().getUserId();
        adminCommentService.ignore(operationId, adminId);
        return ResultWrapper.success();
    }

    /**
     * 显示未处理的评论举报
     * @return
     */
    @ApiOperation("显示未处理的评论举报")
    @GetMapping("/queryNoList")
    public ResultWrapper<List<NoOperationCommentVO>> queryNoList(){
        List<NoOperationCommentVO> noOperationCommentVOList = adminCommentService.queryNoList();
        return ResultWrapper.successWithData(noOperationCommentVOList);
    }

    /**
     * 显示处理过的文章举报
     * @return
     */
    @ApiOperation("显示处理过的评论举报")
    @GetMapping("/queryList")
    public ResultWrapper<List<OperationCommentVO>> queryList(){
        List<OperationCommentVO> operationCommentVOList = adminCommentService.queryList();
        return ResultWrapper.successWithData(operationCommentVOList);
    }

}