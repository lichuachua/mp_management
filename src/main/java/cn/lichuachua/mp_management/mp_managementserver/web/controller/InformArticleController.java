package cn.lichuachua.mp_management.mp_managementserver.web.controller;


import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李歘歘
 */
@Api(value = "InformArticleController", tags = {"文章举报API"})
@RestController
@RequestMapping(value = "/inform/article")
public class InformArticleController extends BaseController<AdminInfoDTO> {

}
