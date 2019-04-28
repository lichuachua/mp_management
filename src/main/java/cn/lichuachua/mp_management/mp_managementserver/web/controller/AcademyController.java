package cn.lichuachua.mp_management.mp_managementserver.web.controller;

import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.service.IAcademyService;
import cn.lichuachua.mp_management.mp_managementserver.vo.AcademyVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://127.0.0.1:8081", maxAge = 3600)
@Api(value = "AcademyController", tags = {"学院类API"})
@RestController
@RequestMapping(value = "/admin/academy")
public class AcademyController extends BaseController<AdminInfoDTO> {
    @Autowired
    private IAcademyService academyService;

    /**
     * 添加学院
     * @param academyName
     * @return
     */
    @ApiOperation("添加学院")
    @PostMapping("/add/{academyName}")
    public ResultWrapper add(
            @PathVariable(value = "academyName") String academyName){
        academyService.add(academyName);
        return ResultWrapper.success();

    }

    /**
     * 删除学院
     * @param academyId
     * @return
     */
    @ApiOperation("删除学院")
    @PutMapping("/delete/{academyId}")
    public ResultWrapper del(
            @PathVariable(value = "academyId") Integer academyId ){
        academyService.del(academyId);
        return ResultWrapper.success();
    }

    /**
     * 查询正常学院列表
     * @return
     */
    @ApiOperation("查询正常学院列表")
    @GetMapping("/queryNormalList")
    public ResultWrapper<List<AcademyVO>> queryNormalList(){
        List<AcademyVO> academyVOList = academyService.queryNormalList();
        return ResultWrapper.successWithData(academyVOList);
    }


    /**
     * 查询删除学院列表
     * @return
     */
    @ApiOperation("查询删除学院列表")
    @GetMapping("/queryDeleteList")
    public ResultWrapper<List<AcademyVO>> queryDeleteList(){
        List<AcademyVO> academyVOList = academyService.queryDeleteList();
        return ResultWrapper.successWithData(academyVOList);
    }

}
