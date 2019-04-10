package cn.lichuachua.mp_management.mp_managementserver.repository;

import cn.lichuachua.mp_management.mp_managementserver.entity.AdminArticle;
import cn.lichuachua.mp_management.mp_managementserver.entity.AdminComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李歘歘
 */
public interface AdminCommentRepository extends JpaRepository<AdminComment, String> {
}
