package cn.lichuachua.mp_management.mp_managementserver.web.controller;


import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.ISchoolService;
import cn.lichuachua.mp_management.mp_managementserver.vo.SchoolVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://127.0.0.1:8080", maxAge = 3600)
@Api(value = "SchoolController", tags = {"学校类API"})
@RestController
@RequestMapping(value = "/admin/school")
public class SchoolController extends BaseController<AdminInfoDTO> {

    @Autowired
    private ISchoolService schoolService;

    /**
     * 添加学校
     * @param schoolName
     * @return
     */
    @ApiOperation("添加学校")
    @PostMapping("/add/{schoolName}")
    public ResultWrapper add(
            @PathVariable(value = "schoolName") String schoolName){
        schoolService.add(schoolName);
        return ResultWrapper.success();

    }


    /**
     * 删除学校
     * @param schoolId
     * @return
     */
    @ApiOperation("删除学校")
    @PutMapping("/delete/{schoolId}")
    public ResultWrapper del(
            @PathVariable(value = "schoolId") Integer schoolId ){
        schoolService.del(schoolId);
        return ResultWrapper.success();
    }

    /**
     * 查询正常学校列表
     * @return
     */
    @ApiOperation("查询正常学校列表")
    @GetMapping("/queryNormalList")
    public ResultWrapper<List<SchoolVO>> queryNormalList(){
        List<SchoolVO> schoolVOList = schoolService.queryNormalList();
        return ResultWrapper.successWithData(schoolVOList);
    }

    /**
     * 查询删除学校列表
     * @return
     */
    @ApiOperation("查询删除学校列表")
    @GetMapping("/queryDeleteList")
    public ResultWrapper<List<SchoolVO>> queryDeleteList(){
        List<SchoolVO> schoolVOList = schoolService.queryDeleteList();
        return ResultWrapper.successWithData(schoolVOList);
    }

}