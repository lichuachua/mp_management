package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.Team;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.TeamStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.TeamVisualEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.TeamException;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamService;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 李歘歘
 */
@Service
public class TeamServiceImpl extends BaseServiceImpl<Team, String> implements ITeamService {


    /**
     * 禁用队伍
     * @param teamId
     */
    @Override
    public void forbidden(String teamId){
        /**
         * 查看当前的队伍是否存在
         */
        Team team = new Team();
        team.setTeamId(teamId);
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        /**
         * 禁用队伍
         */
        team.setStatus(TeamStatusEnum.DISABLED.getStatus());
        team.setCreatedAt(teamOptional.get().getCreatedAt());
        team.setDescription(teamOptional.get().getDescription());
        team.setHeaderAvatar(teamOptional.get().getHeaderAvatar());
        team.setHeaderId(teamOptional.get().getHeaderId());
        team.setHeaderMobile(teamOptional.get().getHeaderMobile());
        team.setNumber(teamOptional.get().getNumber());
        team.setHeaderNick(teamOptional.get().getHeaderNick());
        team.setPassword(teamOptional.get().getPassword());
        team.setTeamName(teamOptional.get().getTeamName());
        team.setType(teamOptional.get().getType());
        team.setUpdatedAt(new Date());
        team.setVisual(teamOptional.get().getVisual());
        update(team);

    }


    /**
     * 解除禁用队伍
     * @param teamId
     */
    @Override
    public void relieveForbidden(String teamId){
        /**
         * 查看当前的队伍是否禁用
         */
        Team team = new Team();
        team.setTeamId(teamId);
        team.setStatus(TeamStatusEnum.DISABLED.getStatus());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        /**
         * 解除禁用队伍
         */
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        team.setCreatedAt(teamOptional.get().getCreatedAt());
        team.setDescription(teamOptional.get().getDescription());
        team.setHeaderAvatar(teamOptional.get().getHeaderAvatar());
        team.setHeaderId(teamOptional.get().getHeaderId());
        team.setHeaderMobile(teamOptional.get().getHeaderMobile());
        team.setNumber(teamOptional.get().getNumber());
        team.setHeaderNick(teamOptional.get().getHeaderNick());
        team.setPassword(teamOptional.get().getPassword());
        team.setTeamName(teamOptional.get().getTeamName());
        team.setType(teamOptional.get().getType());
        team.setUpdatedAt(new Date());
        team.setVisual(teamOptional.get().getVisual());
        update(team);
    }

    /**
     * 显示队伍列表
     * @return
     */
    @Override
    public List<TeamListVO> queryList(Integer status) {
        List<Team> teamList = selectAll();
        List<TeamListVO> teamListVOList = new ArrayList<>();
        for (Team team : teamList){
            if (team.getStatus().equals(status)){
                TeamListVO teamListVO = new TeamListVO();
                if (team.getVisual().equals(TeamVisualEnum.VISUAL.getStatus())){
                    teamListVO.setVisual(TeamVisualEnum.VISUAL.getDesc());
                }else if (team.getVisual().equals(TeamVisualEnum.NO_VISUAL.getStatus())){
                    teamListVO.setVisual(TeamVisualEnum.NO_VISUAL.getDesc());
                }
                teamListVO.setCreatedAt(team.getCreatedAt());
                teamListVO.setDescription(team.getDescription());
                teamListVO.setHeaderAvatar(team.getHeaderAvatar());
                teamListVO.setHeaderNick(team.getHeaderNick());
                teamListVO.setTeamId(team.getTeamId());
                teamListVO.setTeamName(team.getTeamName());
                teamListVO.setType(team.getType());
                BeanUtils.copyProperties(team, teamListVO);
                teamListVOList.add(teamListVO);
            }
        }
        return teamListVOList;
    }
}