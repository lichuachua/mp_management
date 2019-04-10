package cn.lichuachua.mp_management.mp_managementserver.repository;

import cn.lichuachua.mp_management.mp_managementserver.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 李歘歘
 */
public interface AdminRepository extends JpaRepository<Admin, String> {
}
