package cn.lichuachua.mp_management.mp_managementserver.repository;

import cn.lichuachua.mp_management.mp_managementserver.entity.AdminResource;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminTeam;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李歘歘
 */

public interface AdminResourceRepository extends JpaRepository<AdminResource, String> {
}
