package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class TeamTypeVO {

    private Integer typeId;

    private String typeName;

    private Date createdAt;

    private Integer ststus;

}
