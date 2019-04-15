package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.TeamMember;
import cn.lichuachua.mp_management.mp_managementserver.entity.TeamMemberPK;
import cn.lichuachua.mp_management.mp_managementserver.entity.User;
import cn.lichuachua.mp_management.mp_managementserver.enums.TeamMemberStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamMemberService;
import cn.lichuachua.mp_management.mp_managementserver.service.IUserService;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author 李歘歘
 */
@Service
public class TeamMemberServiceImpl extends BaseServiceImpl<TeamMember, TeamMemberPK> implements ITeamMemberService {

    @Autowired
    private IUserService userService;




    /**
     * 查询队伍
     * @param teamId
     * @return
     */
    @Override
    public List<TeamMemberVO> queryList(String teamId){
        List<TeamMember> teamMemberList = selectAll();
        List<TeamMemberVO> teamMemberVOList = new ArrayList<>();
        for (TeamMember teamMember : teamMemberList){
            TeamMemberVO teamMemberVO = new TeamMemberVO();
            if (teamMember.getTeamId().equals(teamId)&&teamMember.getStatus().equals(TeamMemberStatusEnum.NORMAL.getStatus())){
                /**
                 * 根据userId 查出用户信息
                 */
                Optional<User> userOptional = userService.selectByKey(teamMember.getUserId());
                if (userOptional.isPresent()){
                    teamMemberVO.setMemberId(teamMember.getUserId());
                    teamMemberVO.setMemberAvatar(userOptional.get().getUserAvatar());
                    teamMemberVO.setMemberNick(userOptional.get().getUserNick());
                    teamMemberVOList.add(teamMemberVO);
                }
            }
        }
        return teamMemberVOList;
    }
}