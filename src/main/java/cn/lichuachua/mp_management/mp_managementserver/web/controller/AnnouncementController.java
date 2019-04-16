package cn.lichuachua.mp_management.mp_managementserver.web.controller;

import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.form.AnnouncementPublishForm;
import cn.lichuachua.mp_management.mp_managementserver.service.IAnnouncementService;
import cn.lichuachua.mp_management.mp_managementserver.util.FileUtil;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementDeleteListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementDeleteVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementNormalListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementNormalVO;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 *@author 李歘歘
 */
@Api(value = "AnnouncementController", tags = {"公告日志API"})
@RestController
@RequestMapping(value = "/announcement")


public class AnnouncementController extends BaseController<AdminInfoDTO> {

    @Autowired
    private IAnnouncementService announcementService;

    /**
     * 发布公告
     * @param announcementPublishForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("发布公告")
    @PostMapping("/publish")
    public ResultWrapper publish(
            @Valid AnnouncementPublishForm announcementPublishForm,
            MultipartFile file,
            BindingResult bindingResult){
        /**
         * 参数检验
         */
        validateParams(bindingResult);

        /**
         * 获取当前登录的sdminId
         */
        String adminId = getCurrentUserInfo().getUserId();
        /**
         * 上传文件
         */
        if (file!=(null)) {
            //文件路径
            String filePath = "C:/Users/Administrator/Desktop/Mp/mp_management/src/main/resources/static/announcement/";
            //文件名
            String fileName = file.getOriginalFilename();
            /**
             * 调用上传文件方法
             */
            try {
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            announcementService.publish(announcementPublishForm, adminId, fileName);
        }else {
            announcementService.publish(announcementPublishForm, adminId, null);
        }
        return ResultWrapper.success();
    }

    /**
     * 删除公告
     * @param announcementId
     * @return
     */
    @ApiOperation("/删除公告")
    @PutMapping("/deleted/{announcementId}")
    public ResultWrapper deleted(
            @PathVariable(value = "announcementId") String announcementId){
        /**
         * 获取当前登录的用户
         */
        String adminId = getCurrentUserInfo().getUserId();
        announcementService.deleted(announcementId, adminId);
        return ResultWrapper.success();
    }

    /**
     * 显示正常公告列表
     */
    @ApiOperation("/显示正常公告列表")
    @PutMapping("/queryNormalList")
    public ResultWrapper<List<AnnouncementNormalListVO>> queryNormalList(){
        List<AnnouncementNormalListVO> announcementListVONormalList = announcementService.queryNormalList();
        return ResultWrapper.successWithData(announcementListVONormalList);
    }


    /**
     * 显示删除公告列表
     */
    @ApiOperation("/显示删除公告列表")
    @PutMapping("/queryDeletedList")
    public ResultWrapper<List<AnnouncementDeleteListVO>> queryDeletedList(){
        List<AnnouncementDeleteListVO> announcementListVONormalList = announcementService.queryDeletedList();
        return ResultWrapper.successWithData(announcementListVONormalList);
    }


    /**
     * 显示正常公告详情
     * @param announcementId
     * @return
     */
    @ApiOperation("/显示正常公告详情")
    @PutMapping("/queryNormal/{announcementId}")
    public ResultWrapper<AnnouncementNormalVO> queryNormal(
            @PathVariable(value = "announcementId") String announcementId ){
        AnnouncementNormalVO announcementNormalVO = announcementService.queryNormal(announcementId);
        return ResultWrapper.successWithData(announcementNormalVO);
    }


    /**
     * 显示删除公告详情
     * @param announcementId
     * @return
     */
    @ApiOperation("/显示删除公告详情")
    @PutMapping("/queryDelete/{announcementId}")
    public ResultWrapper<AnnouncementDeleteVO> queryDelete(
            @PathVariable(value = "announcementId") String announcementId ){
        AnnouncementDeleteVO announcementDeleteVO = announcementService.queryDelete(announcementId);
        return ResultWrapper.successWithData(announcementDeleteVO);
    }

}
