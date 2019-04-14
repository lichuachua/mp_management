package cn.lichuachua.mp_management.mp_managementserver.web.controller;

import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李歘歘
 */
@Api(value = "AdminResourceController", tags = {"管理员队伍API"})
@RestController
@RequestMapping(value = "/admin/team/log")
public class AdminResourceController extends BaseController<AdminInfoDTO> {

}
