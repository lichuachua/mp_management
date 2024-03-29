package cn.lichuachua.mp_management.mp_managementserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author 李歘歘
 */
@Data
public class SendCodeForm {
    @Pattern(message = "手机号不合法", regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$")
    @NotEmpty(message = "请填写手机号")
    private String mobile;
}
