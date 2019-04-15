package cn.lichuachua.mp_management.mp_managementserver.service;
import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.TeamResource;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamResourceListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.TeamResourceVO;

import java.util.List;


/**
 * @author  李歘歘
 */

public interface ITeamResourceService extends IBaseService<TeamResource, String> {


    /**
     * 根据teamI和状态获取禁用资源列表
     * @param teamId
     * @param status
     * @return
     */
    List<TeamResourceListVO> queryList(String teamId, Integer status);

    /**
     * 获取资料详情
     * @param resourceId
     * @return
     */
    TeamResourceVO query(String resourceId);


    void forbidden(String resourceId);

    void relieveForbidden(String resourceId);

}
