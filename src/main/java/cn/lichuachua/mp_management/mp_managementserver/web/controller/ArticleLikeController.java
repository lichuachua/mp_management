package cn.lichuachua.mp_management.mp_managementserver.web.controller;


import cn.lichuachua.mp_management.core.support.web.controller.BaseController;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://127.0.0.1:8081", maxAge = 3600)
@Api(value ="ArticleLikeController",tags = {"点赞API"})
@RestController
@RequestMapping(value = "/article/likes")
public class ArticleLikeController extends BaseController<AdminInfoDTO> {
}
