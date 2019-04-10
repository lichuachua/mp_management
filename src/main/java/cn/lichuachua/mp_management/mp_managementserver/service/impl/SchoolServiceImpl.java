package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.School;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.SchoolStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.SchoolException;
import cn.lichuachua.mp_management.mp_managementserver.service.ISchoolService;

import cn.lichuachua.mp_management.mp_managementserver.vo.SchoolVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl extends BaseServiceImpl<School, Integer> implements ISchoolService {

    /**
     * 添加学校
     * @param schoolName
     */
    @Override
    public void add(String schoolName){
        /**
         * 查看是否已经有了该学校
         */
        School school = new School();
        school.setSchoolName(schoolName);
        school.setStatus(SchoolStatusEnum.NORMAL.getStatus());
        Optional<School> schoolOptional = selectOne(Example.of(school));
        if (schoolOptional.isPresent()){
            throw new SchoolException(ErrorCodeEnum.SCHOOL_EXIT);
        }
        school.setCreatedAt(new Date());
        school.setUpdatedAt(new Date());
        save(school);
    }



    /**
     * 删除学校
     * @param schoolId
     */
    @Override
    public void del(Integer schoolId) {
        /**
         * 查看是否有该学校
         */
        School school = new School();
        school.setSchoolId(schoolId);
        school.setStatus(SchoolStatusEnum.NORMAL.getStatus());
        Optional<School> schoolOptional = selectOne(Example.of(school));
        if (!schoolOptional.isPresent()){
            throw new SchoolException(ErrorCodeEnum.SCHOOL_NO_EXIT);
        }
        school.setStatus(SchoolStatusEnum.DELETED.getStatus());
        school.setSchoolName(schoolOptional.get().getSchoolName());
        school.setCreatedAt(schoolOptional.get().getCreatedAt());
        school.setUpdatedAt(new Date());
        update(school);
    }

    /**
     * 查询正常学校列表
     * @return
     */
    @Override
    public List<SchoolVO> queryNormalList(){
        List<School> schoolList = selectAll();
        List<SchoolVO> schoolVOList = new ArrayList<>();
        for (School school : schoolList){
            if (school.getStatus().equals(SchoolStatusEnum.NORMAL.getStatus())){
                SchoolVO schoolVO = new SchoolVO();
                schoolVO.setSchoolId(school.getSchoolId());
                schoolVO.setSchoolName(school.getSchoolName());
                BeanUtils.copyProperties(school, schoolVO);
                schoolVOList.add(schoolVO);
            }
        }
        return schoolVOList;
    }

    /**
     * 查询删除学校列表
     * @return
     */
    @Override
    public List<SchoolVO> queryDeleteList(){
        List<School> schoolList = selectAll();
        List<SchoolVO> schoolVOList = new ArrayList<>();
        for (School school : schoolList){
            if (school.getStatus().equals(SchoolStatusEnum.DELETED.getStatus())){
                SchoolVO schoolVO = new SchoolVO();
                schoolVO.setSchoolId(school.getSchoolId());
                schoolVO.setSchoolName(school.getSchoolName());
                BeanUtils.copyProperties(school, schoolVO);
                schoolVOList.add(schoolVO);
            }
        }
        return schoolVOList;
    }


}
