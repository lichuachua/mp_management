package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.TeamMember;
import cn.lichuachua.mp_management.mp_managementserver.entity.TeamMemberPK;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamMemberVO;

import java.util.List;

public interface ITeamMemberService extends IBaseService<TeamMember, TeamMemberPK> {
    /**
     * 根据队伍Id查询出队伍里面的人
     * @param teamId
     * @return
     */
    List<TeamMemberVO> queryList(String teamId);
}
