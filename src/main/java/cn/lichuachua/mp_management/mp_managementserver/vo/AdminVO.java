package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class AdminVO {

    private String adminNick;

    private String adminName;

    private String adminMobile;

    private String adminEmail;

    private String giverName;

    private String giverMobile;

    private String schoolName;

    private String academyName;

    private String adminNumber;

    private Integer rank;

    private Date createdAt;


}
