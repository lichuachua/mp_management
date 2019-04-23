package cn.lichuachua.mp_management.mp_managementserver.service;

import cn.lichuachua.mp_management.core.support.service.IBaseService;
import cn.lichuachua.mp_management.mp_managementserver.entity.Announcement;
import cn.lichuachua.mp_management.mp_managementserver.form.AnnouncementPublishForm;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementDeleteListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementDeleteVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementNormalListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementNormalVO;

import javax.validation.Valid;
import java.util.List;

public interface IAnnouncementService extends IBaseService<Announcement, String> {
    /**
     * 发布公告
     * @param announcementPublishForm
     * @param adminId
     */
    void publish(@Valid AnnouncementPublishForm announcementPublishForm, String adminId, String fileName);

    /**
     * 删除公告
     * @param announcementId
     * @param adminId
     */
    void deleted(String announcementId, String adminId);

    /**
     * 显示正常公告列表
     * @return
     */
    List<AnnouncementNormalListVO> queryNormalList();

    /**
     * 显示删除公告列表
     * @return
     */
    List<AnnouncementDeleteListVO> queryDeletedList();

    /**
     * 显示正常公告详情
     * @param announcementId
     * @return
     */
    AnnouncementNormalVO queryNormal(String announcementId);

    /**
     * 显示删除公告详情
     * @param announcementId
     * @return
     */
    AnnouncementDeleteVO queryDelete(String announcementId);

    /**
     * 更新头像
     * @param adminId
     * @param fileName
     */
    void updateAvatar(String adminId, String fileName);

}
