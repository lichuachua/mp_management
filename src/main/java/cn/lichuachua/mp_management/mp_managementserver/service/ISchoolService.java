package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.School;
import cn.lichuachua.mp_management.mp_managementserver.vo.SchoolVO;

import java.util.List;


public interface ISchoolService extends IBaseService<School, Integer> {

    /**
     * 添加学校
     * @param schoolName
     */
    void add(String schoolName);

    /**
     * 删除学校
     * @param schoolId
     */
    void del(Integer schoolId);


    /**
     * 查询正常学校列表
     * @return
     */
    List<SchoolVO> queryNormalList();

    /**
     * 查询删除学校列表
     * @return
     */
    List<SchoolVO> queryDeleteList();
}
