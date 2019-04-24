package cn.lichuachua.mp_management.mp_managementserver.web.controller;

import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamTypeService;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamTypeVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://127.0.0.1:8080", maxAge = 3600)
@Api(value = "TeamTypeController", tags = {"队伍类型API"})
@RestController
@RequestMapping(value = "/teamType")
public class TeamTypeController extends BaseController<AdminInfoDTO> {
    @Autowired
    private ITeamTypeService teamTypeService;


    /**
     * 查看队伍类型列表
     * @return
     */
    @ApiOperation("查看队伍类型列表")
    @GetMapping("/queryList")
    public ResultWrapper<List<TeamTypeVO>> queryList() {
        List<TeamTypeVO> teamTypeVOList = teamTypeService.queryList();
        return ResultWrapper.successWithData(teamTypeVOList);
    }

    /**
     * 添加类型
     * @param typeName
     * @return
     */
    @ApiOperation("添加类型")
    @GetMapping("/add/{typeName}")
    public ResultWrapper add(
            @PathVariable(value = "typeName") String typeName ){
        teamTypeService.add(typeName);
        return ResultWrapper.success();
    }

    /**
     * 删除类型
     * @param typeId
     * @return
     */
    @ApiOperation("删除类型")
    @PutMapping("/deleted/{typeId}")
    public ResultWrapper deleted(
            @PathVariable(value = "typeId") Integer typeId ){
        teamTypeService.deleted(typeId);
        return ResultWrapper.success();
    }

}
