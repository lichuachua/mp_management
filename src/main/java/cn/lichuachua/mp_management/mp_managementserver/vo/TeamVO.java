package cn.lichuachua.mp_management.mp_managementserver.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李歘歘
 */
@Data
public class TeamVO {
    private String teamName;

    private String headerNick;

    private String description;

    private String headerId;

    private String headerAvatar;

    private String type;

    private Date createdAt;

    List<TeamMemberVO> teamMemberVOList = new ArrayList<>();

}
