package cn.lichuachua.mp_management.mp_managementserver.service;
import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.Academy;
import cn.lichuachua.mp_management.mp_managementserver.vo.AcademyVO;

import java.util.List;

public interface IAcademyService extends IBaseService<Academy, Integer> {

    /**
     * 添加学院
     * @param academyName
     */
    void add(String academyName);

    /**
     * 删除学院
     * @param academyId
     */
    void del(Integer academyId);

    /**
     * 查询正常学院列表
     * @return
     */
    List<AcademyVO> queryNormalList();

    /**
     * 查询删除学院列表
     * @return
     */
    List<AcademyVO> queryDeleteList();

    /**
     * academyId根据查询academyName
     * @param academyId
     * @return
     */
    String queryAcademyName(String adminId, Integer academyId);

}
