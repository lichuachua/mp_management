package cn.lichuachua.mp_management.mp_managementserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author 李歘歘
 */
@Data

public class AdminInforForm {

    @NotEmpty(message = "姓名不能为空")
    private String adminName;

    @NotEmpty(message = "昵称不能为空")
    private String adminNick;

    @NotEmpty(message = "学号不能为空")
    private String adminNumber;

    @Pattern(message = "邮箱不合格", regexp = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$")
    @NotEmpty(message = "邮箱不能为空")
    private String adminEmail;

    @NotNull(message = "学校不能为空")
    private Integer schoolId;

    @NotNull(message = "学院不能为空")
    private Integer academyId;
}
