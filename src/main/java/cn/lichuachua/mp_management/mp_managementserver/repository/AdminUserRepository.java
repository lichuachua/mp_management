package cn.lichuachua.mp_management.mp_managementserver.repository;

import cn.lichuachua.mp_management.mp_managementserver.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李歘歘
 */
public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
}
