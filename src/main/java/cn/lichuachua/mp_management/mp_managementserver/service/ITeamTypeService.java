package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.TeamType;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamTypeVO;

import java.util.List;

/**
 * @author 李歘歘
 */
public interface ITeamTypeService extends IBaseService<TeamType, String> {

    /**
     * 查看队伍类型列表
     * @return
     */
    List<TeamTypeVO> queryList();

    /***
     * 添加队伍类型
     * @param typeName
     */
    void add(String typeName);

    /**
     * 删除队伍类型
     * @param typeId
     */
    void deleted(Integer typeId);

    /**
     * 根据typeId查询typeName
     * @param typeId
     * @return
     */
    String queryTypeName(Integer typeId);

}
