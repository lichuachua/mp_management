package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.Academy;
import cn.lichuachua.mp_management.mp_managementserver.entity.Admin;
import cn.lichuachua.mp_management.mp_managementserver.enums.AcademyStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.AcademyException;
import cn.lichuachua.mp_management.mp_managementserver.service.IAcademyService;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminService;
import cn.lichuachua.mp_management.mp_managementserver.vo.AcademyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AcademyServiceImpl extends BaseServiceImpl<Academy, Integer> implements IAcademyService {
    @Autowired
    private IAdminService adminService;

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


    /**
     * 根据userId和academyId查询academyName
     * @param adminId
     * @param academyId
     * @return
     */
    @Override
    public String queryAcademyName(String adminId, Integer academyId){
        /**
         * 如果用户的学院不为空
         * 根据academyId查询出academyName
         */
        /**
         * 查询用户的学院信息
         */
        Optional<Admin> adminOptional = adminService.selectByKey(adminId);
        if (adminOptional.get().getAcademyId()==null){
            return null;
        }else {
            Academy academy = new Academy();
            academy.setStatus(AcademyStatusEnum.NORMAL.getStatus());
            academy.setAcademyId(academyId);
            Optional<Academy> academyOptional = selectOne(Example.of(academy));
            /**
             * 学校存在--学校名
             * 学校不存在，显示null
             */
            if (!academyOptional.isPresent()){
                return null;
            }else {
                return academyOptional.get().getAcademyName();
            }
        }
    }

}
