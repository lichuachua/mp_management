package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class AdminVO {

    private String adminName;

    private String adminMobile;

    private String adminEmail;

    private String schoolName;

    private String giverName;

    private String giverMobile;

    private Date createdAt;



}
