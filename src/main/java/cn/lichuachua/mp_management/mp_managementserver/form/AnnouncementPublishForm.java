package cn.lichuachua.mp_management.mp_managementserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 李歘歘
 */
@Data
public class AnnouncementPublishForm {

    /**
     * 公告标题
     */
    @NotEmpty(message = "请填写公告标题")
    private String title;

    /**
     * 公告内容
     */
    @NotEmpty(message = "请填写公告内容")
    private String content;

    /**
     * 公告等级
     */
    @NotNull(message = "请填写公告等级")
    private Integer rank;

    /**
     * 公告类型
     */
    private Integer type;
}
