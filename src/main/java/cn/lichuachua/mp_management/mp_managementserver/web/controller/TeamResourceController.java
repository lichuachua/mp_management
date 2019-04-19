package cn.lichuachua.mp_management.mp_managementserver.web.controller;
import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.enums.TeamResourceStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamResourceService;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamResourceListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamResourceVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author  李歘歘
 */
@CrossOrigin(origins = "http://127.0.0.1:8080", maxAge = 3600)
@Api(value = "TeamResourceController", tags = {"队伍资源API"})
@RestController
@RequestMapping(value = "/teamResource")
public class TeamResourceController extends BaseController<AdminInfoDTO> {

    @Autowired
    private ITeamResourceService teamResourceService;

    /**
     * 根据teamId获取正常资源列表
     * @param teamId
     * @return
     */
    @ApiOperation("根据teamId获取正常资源列表")
    @GetMapping("/queryNormalList/{teamId}")
    public ResultWrapper<List<TeamResourceListVO>> queryNormalList(
            @PathVariable(value = "teamId") String teamId) {
        /**
         * 查询列表
         */
        List<TeamResourceListVO> resourceListVOList = teamResourceService.queryList(teamId, TeamResourceStatusEnum.NORMAL.getStatus());
        return ResultWrapper.successWithData(resourceListVOList);
    }

    /**
     * 根据teamId获取删除资源列表
     * @param teamId
     * @return
     */
    @ApiOperation("根据teamId获取删除资源列表")
    @GetMapping("/queryDeleteList/{teamId}")
    public ResultWrapper<List<TeamResourceListVO>> queryDeleteList(
            @PathVariable(value = "teamId") String teamId ) {
        /**
         * 查询列表
         */
        List<TeamResourceListVO> resourceListVOList = teamResourceService.queryList(teamId,TeamResourceStatusEnum.DELETED.getStatus());
        return ResultWrapper.successWithData(resourceListVOList);
    }

    /**
     * 根据teamId获取禁用资源列表
     * @param teamId
     * @return
     */
    @ApiOperation("根据teamId获取禁用资源列表")
    @GetMapping("/queryDisabledList/{teamId}")
    public ResultWrapper<List<TeamResourceListVO>> queryDisabledList(
            @PathVariable(value = "teamId") String teamId ) {
        /**
         * 查询列表
         */
        List<TeamResourceListVO> resourceListVOList = teamResourceService.queryList(teamId,TeamResourceStatusEnum.DISABLED.getStatus());
        return ResultWrapper.successWithData(resourceListVOList);
    }


    /**
     * 获取资料详情
     * @param resourceId
     * @return
     */
    @ApiOperation("获取资料详情")
    @GetMapping("/query/{resourceId}")
    public ResultWrapper<TeamResourceVO> query(
            @PathVariable(value = "resourceId") String resourceId ) {
        TeamResourceVO teamResourceVO = teamResourceService.query(resourceId);
        return ResultWrapper.successWithData(teamResourceVO);
    }





}
