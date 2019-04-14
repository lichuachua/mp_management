package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.Team;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.TeamStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.TeamException;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
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


}