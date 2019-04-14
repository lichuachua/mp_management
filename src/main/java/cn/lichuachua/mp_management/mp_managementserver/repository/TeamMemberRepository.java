package cn.lichuachua.mp_management.mp_managementserver.repository;

import cn.lichuachua.mp_management.mp_managementserver.entity.TeamMember;
import cn.lichuachua.mp_management.mp_managementserver.entity.TeamMemberPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 李歘歘
 */
@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberPK> {
}
