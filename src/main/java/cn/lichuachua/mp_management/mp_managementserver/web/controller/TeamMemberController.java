package cn.lichuachua.mp_management.mp_managementserver.web.controller;

import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.*;



/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@Api(value = "TeamMemberController", tags = {"队伍成员API"})
@RestController
@RequestMapping(value = "/teamMember")
public class TeamMemberController extends BaseController<AdminInfoDTO> {
}
