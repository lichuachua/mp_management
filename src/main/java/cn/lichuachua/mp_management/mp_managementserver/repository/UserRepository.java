package cn.lichuachua.mp_management.mp_managementserver.repository;

import cn.lichuachua.mp_management.mp_managementserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李歘歘
 */
public interface UserRepository extends JpaRepository<User, String> {
}
