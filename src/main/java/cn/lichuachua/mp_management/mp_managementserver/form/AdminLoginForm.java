package cn.lichuachua.mp_management.mp_managementserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李歘歘
 */

@Data
public class AdminLoginForm {
    @NotEmpty(message = "请填写手机号")
    private String mobile;

    @NotEmpty(message = "请填写密码")
    private String password;

}
