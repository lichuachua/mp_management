package cn.lichuachua.mp_management.mp_managementserver.web.controller;


import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminArticleService;
import cn.lichuachua.mp_management.mp_managementserver.vo.NoOperationArticleVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.OperationArticleVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 李歘歘
 */
@Api(value = "AdminArticleController", tags = {"管理文章日志API"})
@RestController
@RequestMapping(value = "/admin/article/log")
public class AdminArticleController extends BaseController<AdminInfoDTO> {

    @Autowired
    private IAdminArticleService adminArticleService;

    /**
     * 忽略文章举报
     * @param operationId
     * @return
     */
    @ApiOperation("忽略文章举报")
    @PutMapping("/ignore/{operationId}")
    public ResultWrapper ignore(
            @PathVariable(value = "operationId" ) String operationId ){
        /**
         * 获取当前的登录的adminId
         */
        String adminId = getCurrentUserInfo().getUserId();
        adminArticleService.ignore(operationId, adminId);
        return ResultWrapper.success();
    }

    /**
     * 显示未处理的文章举报
     * @return
     */
    @ApiOperation("显示未处理的文章举报")
    @GetMapping("/queryNoList")
    public ResultWrapper<List<NoOperationArticleVO>> queryNoList(){
        List<NoOperationArticleVO> noOperationArticleVOList = adminArticleService.queryNoList();
        return ResultWrapper.successWithData(noOperationArticleVOList);
    }

    /**
     * 显示处理过的文章举报
     * @return
     */
    @ApiOperation("显示处理过的文章举报")
    @GetMapping("/queryList")
    public ResultWrapper<List<OperationArticleVO>> queryList(){
        List<OperationArticleVO> operationArticleVOList = adminArticleService.queryList();
        return ResultWrapper.successWithData(operationArticleVOList);
    }


}