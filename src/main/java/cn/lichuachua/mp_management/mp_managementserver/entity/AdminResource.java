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
@Entity(name = "mp_admin_resource")
public class AdminResource {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "operation_id")
    private String operationId;

    @Column(name = "resource_id")
    private String resourceId;


    @Column(name = "publisher_id")
    private String publisherId;

    @Column(name = "publisher_mobile")
    private String publisherMobile;

    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "admin_mobile")
    private String adminMobile;

    @Column(name = "operation_type")
    private Integer operationType;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
