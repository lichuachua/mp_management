package cn.lichuachua.mp_management.mp_managementserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "mp_academy")
public class Academy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "academy_id")
    private Integer academyId;

    @Column(name = "academy_name")
    private String academyName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
