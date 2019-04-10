package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.Academy;
import cn.lichuachua.mp_management.mp_managementserver.enums.AcademyStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.AcademyException;
import cn.lichuachua.mp_management.mp_managementserver.service.IAcademyService;
import cn.lichuachua.mp_management.mp_managementserver.vo.AcademyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AcademyServiceImpl extends BaseServiceImpl<Academy, Integer> implements IAcademyService {

    /**
     * 添加学院
     * @param academyName
     */
    @Override
    public void add(String academyName){
        /**
         * 查看是否已经有了该学院
         */
        Academy academy = new Academy();
        academy.setAcademyName(academyName);
        academy.setStatus(AcademyStatusEnum.NORMAL.getStatus());
        Optional<Academy> academyOptional = selectOne(Example.of(academy));
        if (academyOptional.isPresent()){
            throw new AcademyException(ErrorCodeEnum.ACADEMY_EXIT);
        }
        academy.setCreatedAt(new Date());
        academy.setUpdatedAt(new Date());
        save(academy);
    }


    /**
     * 删除学院
     * @param academyId
     */
    @Override
    public void del(Integer academyId) {
        /**
         * 查看是否有该学校
         */
        Academy academy = new Academy();
        academy.setAcademyId(academyId);
        academy.setStatus(AcademyStatusEnum.NORMAL.getStatus());
        Optional<Academy> academyOptional = selectOne(Example.of(academy));
        if (!academyOptional.isPresent()){
            throw new AcademyException(ErrorCodeEnum.ACADEMY_NO_EXIT);
        }
        academy.setStatus(AcademyStatusEnum.DELETED.getStatus());
        academy.setAcademyName(academyOptional.get().getAcademyName());
        academy.setCreatedAt(academyOptional.get().getCreatedAt());
        academy.setUpdatedAt(new Date());
        update(academy);
    }

    /**
     * 查询正常学院列表
     * @return
     */
    @Override
    public List<AcademyVO> queryNormalList(){
        List<Academy> academyList = selectAll();
        List<AcademyVO> academyVOList = new ArrayList<>();
        for (Academy academy : academyList){
            if (academy.getStatus().equals(AcademyStatusEnum.NORMAL.getStatus())){
                AcademyVO academyVO = new AcademyVO();
                academyVO.setAcademyId(academy.getAcademyId());
                academyVO.setAcademyName(academy.getAcademyName());
                BeanUtils.copyProperties(academy, academyVO);
                academyVOList.add(academyVO);
            }
        }
        return academyVOList;
    }


    /**
     * 查询删除学院列表
     * @return
     */
    @Override
    public List<AcademyVO> queryDeleteList(){
        List<Academy> academyList = selectAll();
        List<AcademyVO> academyVOList = new ArrayList<>();
        for (Academy academy : academyList){
            if (academy.getStatus().equals(AcademyStatusEnum.DELETED.getStatus())){
                AcademyVO academyVO = new AcademyVO();
                academyVO.setAcademyId(academy.getAcademyId());
                academyVO.setAcademyName(academy.getAcademyName());
                BeanUtils.copyProperties(academy, academyVO);
                academyVOList.add(academyVO);
            }
        }
        return academyVOList;
    }


}
