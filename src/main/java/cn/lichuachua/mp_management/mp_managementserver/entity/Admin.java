package cn.lichuachua.mp_management.mp_managementserver.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
@Entity(name = "mp_admin")
public class Admin {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_nick")
    private String adminNick;

    @Column(name = "admin_number")
    private String adminNumber;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "giver_id")
    private String giverId;

    @Column(name = "giver_name")
    private String giverName;

    @Column(name = "giver_mobile")
    private String giverMobile;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "status")
    private Integer status;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "password")
    private String password;

    @Column(name = "admin_avatar")
    private String adminAvatar;

    @Column(name = "school_id")
    private Integer schoolId;

    @Column(name = "academy_id")
    private Integer academyId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
