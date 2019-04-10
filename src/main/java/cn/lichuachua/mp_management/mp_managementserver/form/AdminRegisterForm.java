package cn.lichuachua.mp_management.mp_managementserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 李歘歘
 */
@Data
public class AdminRegisterForm {
    @NotEmpty(message = "请填写手机号")
    private String mobile;

    @NotNull(message = "请填写等级")
    private Integer rank;
}
