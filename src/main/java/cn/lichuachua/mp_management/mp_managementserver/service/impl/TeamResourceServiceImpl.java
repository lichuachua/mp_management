package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.Team;
import cn.lichuachua.mp_management.mp_managementserver.entity.TeamResource;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.TeamResourceStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.TeamStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.TeamException;
import cn.lichuachua.mp_management.mp_managementserver.exception.TeamResourceException;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamMemberService;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamResourceService;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamService;
import cn.lichuachua.mp_management.mp_managementserver.service.IUserService;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamResourceListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamResourceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * @author  李歘歘
 */
@Service
public class TeamResourceServiceImpl extends BaseServiceImpl<TeamResource, String> implements ITeamResourceService {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITeamService teamService;

    @Autowired
    private ITeamMemberService teamMemberService;


    /**
     * 根据状态显示资源列表
     * @param teamId
     * @return
     */
    @Override
    public List<TeamResourceListVO> queryList(String teamId, Integer status){
        /**
         * 判断该队伍是否存在
         */
        Team team = new Team();
        team.setTeamId(teamId);
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = teamService.selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        List<TeamResource> teamResourceList = selectAll();
        List<TeamResourceListVO> teamResourceListVOList = new ArrayList<>();
        for (TeamResource teamResource : teamResourceList){
            TeamResourceListVO teamResourceListVO = new TeamResourceListVO();
            /**
             * 根据资源属性查找
             */
            if (teamResource.getTeamId().equals(teamId)&&teamResource.getStatus().equals(status)){
                teamResourceListVO.setCreatedAt(teamResource.getCreatedAt());
                teamResourceListVO.setPublisherNick(teamResource.getPublisherNick());
                teamResourceListVO.setResourceId(teamResource.getResourceId());
                teamResourceListVO.setResourceName(teamResource.getResourceName());
                BeanUtils.copyProperties(teamResource, teamResourceListVO);
                teamResourceListVOList.add(teamResourceListVO);
            }
        }
        return teamResourceListVOList;
    }


    /**
     * 查看资源详情
     * @param resourceId
     * @return
     */
    @Override
    public TeamResourceVO query(String resourceId){
        /**
         * 根据资源Id查找
         */
        TeamResource teamResource = new TeamResource();
        teamResource.setResourceId(resourceId);
        Optional<TeamResource> teamResourceOptional = selectOne(Example.of(teamResource));
        if (!teamResourceOptional.isPresent()){
            throw new TeamResourceException(ErrorCodeEnum.TEAM_RESOURCE_NO_EXIT);
        }
        TeamResourceVO teamResourceVO = new TeamResourceVO();
        teamResourceVO.setPublisherId(teamResourceOptional.get().getPublisherId());
        teamResourceVO.setPublisherNick(teamResourceOptional.get().getPublisherNick());
        teamResourceVO.setResource(teamResourceOptional.get().getResource());
        teamResourceVO.setCreatedAt(teamResourceOptional.get().getCreatedAt());
        teamResourceVO.setResourceName(teamResourceOptional.get().getResourceName());
        return teamResourceVO;
    }


    /**
     * 禁用资源
     * @param resourceId
     */
    @Override
    public void forbidden(String resourceId){
        /**
         * 查看当前的资源是否存在
         */
        TeamResource teamResource = new TeamResource();
        teamResource.setResourceId(resourceId);
        teamResource.setStatus(TeamResourceStatusEnum.NORMAL.getStatus());
        Optional<TeamResource> teamResourceOptional = selectOne(Example.of(teamResource));
        if (!teamResourceOptional.isPresent()){
            throw new TeamResourceException(ErrorCodeEnum.TEAM_RESOURCE_NO_EXIT);
        }
        /**
         * 禁用资源
         */
        teamResource.setStatus(TeamResourceStatusEnum.DISABLED.getStatus());
        teamResource.setPublisherId(teamResourceOptional.get().getPublisherId());
        teamResource.setResource(teamResourceOptional.get().getResource());
        teamResource.setPublisherNick(teamResourceOptional.get().getPublisherNick());
        teamResource.setUpdatedAt(new Date());
        teamResource.setCreatedAt(teamResourceOptional.get().getCreatedAt());
        teamResource.setResourceName(teamResourceOptional.get().getResourceName());
        teamResource.setTeamId(teamResourceOptional.get().getTeamId());
        update(teamResource);
    }


    /**
     * 解除禁用资源
     * @param resourceId
     */
    @Override
    public void relieveForbidden(String resourceId){
        /**
         * 查看当前的资源是否被禁用
         */
        TeamResource teamResource = new TeamResource();
        teamResource.setResourceId(resourceId);
        teamResource.setStatus(TeamResourceStatusEnum.DISABLED.getStatus());
        Optional<TeamResource> teamResourceOptional = selectOne(Example.of(teamResource));
        if (!teamResourceOptional.isPresent()){
            throw new TeamResourceException(ErrorCodeEnum.TEAM_RESOURCE_NO_EXIT);
        }
        /**
         * 解除禁用资源
         */
        teamResource.setStatus(TeamResourceStatusEnum.NORMAL.getStatus());
        teamResource.setPublisherId(teamResourceOptional.get().getPublisherId());
        teamResource.setResource(teamResourceOptional.get().getResource());
        teamResource.setPublisherNick(teamResourceOptional.get().getPublisherNick());
        teamResource.setUpdatedAt(new Date());
        teamResource.setCreatedAt(teamResourceOptional.get().getCreatedAt());
        teamResource.setResourceName(teamResourceOptional.get().getResourceName());
        teamResource.setTeamId(teamResourceOptional.get().getTeamId());
        update(teamResource);

    }


}
