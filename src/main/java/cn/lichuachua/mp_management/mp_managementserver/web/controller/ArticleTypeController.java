package cn.lichuachua.mp_management.mp_managementserver.web.controller;


import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleTypeService;
import cn.lichuachua.mp_management.mp_managementserver.vo.ArticleTypeVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://127.0.0.1:8081", maxAge = 3600)
@Api(value = "ArticleTypeController", tags = {"文章类型API"})
@RestController
@RequestMapping(value = "/article/type")
public class ArticleTypeController extends BaseController<AdminInfoDTO> {
    @Autowired
    private IArticleTypeService articleTypeService;

    /**
     * 添加文章类型
     * @param typeName
     * @return
     */
    @ApiOperation("添加文章类型")
    @PostMapping("/add/{typeName}")
    public ResultWrapper add(
            @PathVariable(value = "typeName") String typeName){
        articleTypeService.add(typeName);
        return ResultWrapper.success();
    }

    /**
     * 删除文章类型
     * @param typeId
     * @return
     */
    @ApiOperation("删除文章类型")
    @DeleteMapping("/delete/{typeId}")
    public ResultWrapper del(
            @PathVariable(value = "typeId") Integer typeId ){
        articleTypeService.del(typeId);
        return ResultWrapper.success();
    }

    /**
     * 查询所有文章类型列表
     * @return
     */
    @ApiOperation("查询所有文章类型列表")
    @GetMapping("/queryArticleTypeList")
    public ResultWrapper<List<ArticleTypeVO>> queryArticleTypeList(){
        List<ArticleTypeVO> articleTypeList = articleTypeService.queryArticleTypeList();
        return ResultWrapper.successWithData(articleTypeList);
    }

}
