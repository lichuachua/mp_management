package cn.lichuachua.mp_management.mp_managementserver.service;


import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.Team;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamVO;

import java.util.List;

/**
 * @author 李歘歘
 */

public interface ITeamService extends IBaseService<Team, String> {
    /**
     * 禁用队伍
     * @param teamId
     */
    void forbidden(String teamId);

    /**
     * 解除禁用队伍
     * @param teamId
     */
    void relieveForbidden(String teamId);

    List<TeamListVO> queryList(Integer status);

    TeamVO query(String teamId);
}
