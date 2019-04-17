package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.TeamType;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.TeamTypeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.TeamTypeException;
import cn.lichuachua.mp_management.mp_managementserver.service.ITeamTypeService;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamTypeVO;
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
public class TeamTypeServiceImpl extends BaseServiceImpl<TeamType, String> implements ITeamTypeService {


    /**
     * 查看队伍类型列表
     * @return
     */
    @Override
    public List<TeamTypeVO> queryList(){
        List<TeamType> teamTypeList = selectAll();
        List<TeamTypeVO> teamTypeVOList = new ArrayList<>();
        for (TeamType teamType : teamTypeList){
            TeamTypeVO teamTypeVO = new TeamTypeVO();
            teamTypeVO.setTypeId(teamType.getTypeId());
            teamTypeVO.setTypeName(teamType.getTypeName());
            teamTypeVO.setCreatedAt(teamType.getCreatedAt());
            teamTypeVO.setStstus(teamType.getStatus());
            BeanUtils.copyProperties(teamType, teamTypeVO);
            teamTypeVOList.add(teamTypeVO);
        }
        return teamTypeVOList;
    }

    /**
     * 添加队伍类型
     */
    @Override
    public void add(String typeName){
        TeamType teamType = new TeamType();
        teamType.setTypeName(typeName);
        teamType.setStatus(TeamTypeEnum.NORMAL.getStatus());
        Optional<TeamType> teamTypeOptional = selectOne(Example.of(teamType));
        if (teamTypeOptional.isPresent()){
            throw new TeamTypeException(ErrorCodeEnum.TEAM_TYPE_EXIT);
        }
        teamType.setCreatedAt(new Date());
        teamType.setUpdatedAt(new Date());
        save(teamType);
    }

    /**
     * 删除队伍类型
     * @param typeId
     */
    @Override
    public void deleted(Integer typeId){
        TeamType teamType = new TeamType();
        teamType.setTypeId(typeId);
        teamType.setStatus(TeamTypeEnum.NORMAL.getStatus());
        Optional<TeamType> teamTypeOptional = selectOne(Example.of(teamType));
        if (!teamTypeOptional.isPresent()){
            throw new TeamTypeException(ErrorCodeEnum.TEAM_TYPE_NO_EXIT);
        }
        teamType.setTypeName(teamTypeOptional.get().getTypeName());
        teamType.setCreatedAt(teamTypeOptional.get().getCreatedAt());
        teamType.setStatus(TeamTypeEnum.DELETED.getStatus());
        teamType.setUpdatedAt(new Date());
        update(teamType);
    }

}
