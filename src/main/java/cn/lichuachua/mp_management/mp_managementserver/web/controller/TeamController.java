package cn.lichuachua.mp_management.mp_managementserver.web.controller;


import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.enums.TeamStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamService;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamVO;
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
@Api(value = "TeamController", tags = {"队伍API"})
@RestController
@RequestMapping(value = "/team")
public class TeamController extends BaseController<AdminInfoDTO> {

    @Autowired
    private ITeamService teamService;

    /**
     * 正常队伍列表
     * @return
     */
    @ApiOperation("正常队伍列表")
    @GetMapping("/queryNormalList")
    public ResultWrapper<List<TeamListVO>> queryNormalList(){
        List<TeamListVO> teamListVOList = teamService.queryList(TeamStatusEnum.NORMAL.getStatus());
        return ResultWrapper.successWithData(teamListVOList);
    }


    /**
     * 删除队伍列表
     * @return
     */
    @ApiOperation("删除队伍列表")
    @GetMapping("/queryDeleteList")
    public ResultWrapper<List<TeamListVO>> queryDeleteList(){
        List<TeamListVO> teamListVOList = teamService.queryList(TeamStatusEnum.DELETED.getStatus());
        return ResultWrapper.successWithData(teamListVOList);
    }


    /**
     * 禁用队伍列表
     * @return
     */
    @ApiOperation("禁用队伍列表")
    @GetMapping("/queryDisabledList")
    public ResultWrapper<List<TeamListVO>> queryDisabledList(){
        List<TeamListVO> teamListVOList = teamService.queryList(TeamStatusEnum.DISABLED.getStatus());
        return ResultWrapper.successWithData(teamListVOList);
    }


    /**
     * 显示队伍详情
     * @param teamId
     * @return
     */
    @ApiOperation("显示队伍详情")
    @GetMapping("/query/{teamId}")
    public ResultWrapper<TeamVO> query(
            @PathVariable(value = "teamId") String teamId) {
        TeamVO teamVO= teamService.query(teamId);
        return ResultWrapper.successWithData(teamVO);
    }



}
