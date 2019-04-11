package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.entity.Admin;
import cn.lichuachua.mp_management.mp_managementserver.entity.Announcement;
import cn.lichuachua.mp_management.mp_managementserver.entity.ArticleType;
import cn.lichuachua.mp_management.mp_managementserver.enums.AnnouncementStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ArticleStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.AnnouncementException;
import cn.lichuachua.mp_management.mp_managementserver.form.AnnouncementPublishForm;
import cn.lichuachua.mp_management.mp_managementserver.service.IAdminService;
import cn.lichuachua.mp_management.mp_managementserver.service.IAnnouncementService;
import cn.lichuachua.mp_management.mp_managementserver.service.IArticleTypeService;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementDeleteListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementDeleteVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementNormalListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AnnouncementNormalVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl extends BaseServiceImpl<Announcement, String> implements IAnnouncementService {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IArticleTypeService articleTypeService;



    /**
     * 发布公告
     * @param announcementPublishForm
     * @param adminId
     */
    @Override
    public void publish(AnnouncementPublishForm announcementPublishForm, String adminId){
        /**
         * 保存公告信息
         */
        Announcement announcement = new Announcement();
        announcement.setRank(announcementPublishForm.getRank());
        announcement.setStatus(ArticleStatusEnum.NORMAL.getStatus());
        announcement.setContent(announcementPublishForm.getContent());
        announcement.setTitle(announcementPublishForm.getTitle());
        announcement.setCreatedAt(new Date());
        announcement.setUpdatedAt(new Date());
        announcement.setPublisherId(adminId);
        /**
         * 根据adminId取出管理员信息
         */
        Optional<Admin> adminOptional = adminService.selectByKey(adminId);
        announcement.setPublisherAvatar(adminOptional.get().getAdminAvatar());
        announcement.setPublisherNick(adminOptional.get().getAdminNick());
        announcement.setAnnouncementType(announcementPublishForm.getType());
        announcement.setAccessory(announcementPublishForm.getAccessory());
        save(announcement);
    }


    /**
     * 删除公告
     * @param announcementId
     * @param adminId
     */
    @Override
    public void deleted(String announcementId, String adminId){
        /**
         * 判断公告是否存在
         */
        Announcement announcement = new Announcement();
        announcement.setAnnouncementId(announcementId);
        announcement.setStatus(AnnouncementStatusEnum.NORMAL.getStatus());
        Optional<Announcement> announcementOptional = selectOne(Example.of(announcement));
        if (!announcementOptional.isPresent()){
            throw new AnnouncementException(ErrorCodeEnum.ANNOUNCEMENT_NO_EXIT);
        }
        /**
         * 更改文章状态，并将删除人加入系统
         */
        announcement.setStatus(AnnouncementStatusEnum.DELETED.getStatus());
        announcement.setAnnouncementType(announcementOptional.get().getAnnouncementType());
        announcement.setAccessory(announcementOptional.get().getAccessory());
        announcement.setContent(announcementOptional.get().getContent());
        announcement.setCreatedAt(announcementOptional.get().getCreatedAt());
        announcement.setPublisherAvatar(announcementOptional.get().getPublisherAvatar());
        announcement.setPublisherId(announcementOptional.get().getPublisherId());
        announcement.setPublisherNick(announcementOptional.get().getPublisherNick());
        announcement.setRank(announcementOptional.get().getRank());
        announcement.setTitle(announcementOptional.get().getTitle());
        announcement.setUpdatedAt(new Date());
        /**
         * 根据adminId取出管理员信息
         */
        Optional<Admin> adminOptional = adminService.selectByKey(adminId);
        announcement.setDeleterId(adminOptional.get().getAdminId());
        announcement.setDeleterMobile(adminOptional.get().getMobile());
        update(announcement);
    }

    /**
     * 显示正常公告列表
     * @return
     */
    @Override
    public List<AnnouncementNormalListVO>  queryNormalList(){
        List<Announcement> announcementList = selectAll();
        List<AnnouncementNormalListVO> announcementListVONormalList = new ArrayList<>();
        for (Announcement announcement : announcementList){
            AnnouncementNormalListVO announcementNormalListVO = new AnnouncementNormalListVO();
            if (announcement.getStatus().equals(AnnouncementStatusEnum.NORMAL.getStatus())){
                announcementNormalListVO.setAnnouncementId(announcement.getAnnouncementId());
                announcementNormalListVO.setAccessory(announcement.getAccessory());
                announcementNormalListVO.setArticleType(announcement.getAccessory());
                announcementNormalListVO.setUpdatedAt(announcement.getUpdatedAt());
                announcementNormalListVO.setPublisherAvatar(announcement.getPublisherAvatar());
                announcementNormalListVO.setPublisherNick(announcement.getPublisherNick());
                announcementNormalListVO.setTitle(announcement.getTitle());
                announcementNormalListVO.setRank(announcement.getRank());
                /**
                 * 判断当前公告类型是否为空
                 */
                if (announcement.getAnnouncementType()==null){
                    announcementNormalListVO.setAnnouncementType(null);
                }else {
                    /**
                     * 查找该类型是否存在
                     */
                    ArticleType articleType = new ArticleType();
                    articleType.setTypeId(announcement.getAnnouncementType());
                    Optional<ArticleType> articleTypeOptional = articleTypeService.selectOne(Example.of(articleType));
                    if (articleTypeOptional.isPresent()){
                        announcementNormalListVO.setAnnouncementType(articleTypeOptional.get().getTypeName());
                    }else {
                        announcementNormalListVO.setAnnouncementType(null);
                    }
                }
                BeanUtils.copyProperties(announcement, announcementNormalListVO);
                announcementListVONormalList.add(announcementNormalListVO);
            }
        }
        return announcementListVONormalList;
    }

    /**
     * 显示删除公告列表
     * @return
     */
    @Override
    public List<AnnouncementDeleteListVO>  queryDeletedList(){
        List<Announcement> announcementList = selectAll();
        List<AnnouncementDeleteListVO> announcementDeleteListVOList = new ArrayList<>();
        for (Announcement announcement : announcementList){
            AnnouncementDeleteListVO announcementDeleteListVO = new AnnouncementDeleteListVO();
            if (announcement.getStatus().equals(AnnouncementStatusEnum.DELETED.getStatus())){
                announcementDeleteListVO.setAccessory(announcement.getAccessory());
                announcementDeleteListVO.setContent(announcement.getContent());
                announcementDeleteListVO.setUpdatedAt(announcement.getUpdatedAt());
                announcementDeleteListVO.setPublisherAvatar(announcement.getPublisherAvatar());
                announcementDeleteListVO.setPublisherNick(announcement.getPublisherNick());
                announcementDeleteListVO.setTitle(announcement.getTitle());
                announcementDeleteListVO.setDeleterMobile(announcement.getDeleterMobile());
                announcementDeleteListVO.setDeleterId(announcement.getDeleterId());
                announcementDeleteListVO.setRank(announcement.getRank());
                /**
                 * 判断当前公告类型是否为空
                 */
                if (announcement.getAnnouncementType()==null){
                    announcementDeleteListVO.setAnnouncementType(null);
                }else {
                    /**
                     * 查找该类型是否存在
                     */
                    ArticleType articleType = new ArticleType();
                    articleType.setTypeId(announcement.getAnnouncementType());
                    Optional<ArticleType> articleTypeOptional = articleTypeService.selectOne(Example.of(articleType));
                    if (articleTypeOptional.isPresent()){
                        announcementDeleteListVO.setAnnouncementType(articleTypeOptional.get().getTypeName());
                    }else {
                        announcementDeleteListVO.setAnnouncementType(null);
                    }
                }
                BeanUtils.copyProperties(announcement, announcementDeleteListVO);
                announcementDeleteListVOList.add(announcementDeleteListVO);
            }
        }
        return announcementDeleteListVOList;
    }

    /**
     * 显示正常公告详情
     * @param announcementId
     * @return
     */
    @Override
    public AnnouncementNormalVO queryNormal(String announcementId){
        Announcement announcement = new Announcement();
        announcement.setAnnouncementId(announcementId);
        announcement.setStatus(AnnouncementStatusEnum.NORMAL.getStatus());
        Optional<Announcement> announcementOptional = selectOne(Example.of(announcement));
        if (!announcementOptional.isPresent()){
            throw new AnnouncementException(ErrorCodeEnum.ANNOUNCEMENT_NO_EXIT);
        }
        AnnouncementNormalVO announcementNormalVO = new AnnouncementNormalVO();
        announcementNormalVO.setAccessory(announcementOptional.get().getAccessory());
        announcementNormalVO.setContent(announcementOptional.get().getContent());
        announcementNormalVO.setPublisherAvatar(announcementOptional.get().getPublisherAvatar());
        announcementNormalVO.setPublisherNick(announcementOptional.get().getPublisherNick());
        announcementNormalVO.setTitle(announcementOptional.get().getTitle());
        announcementNormalVO.setUpdatedAt(announcementOptional.get().getUpdatedAt());
        announcementNormalVO.setPublisherId(announcementOptional.get().getPublisherId());
        announcementNormalVO.setRank(announcementOptional.get().getRank());
        /**
         * 判断当前公告类型是否为空
         */
        if (announcementOptional.get().getAnnouncementType()==null){
            announcementNormalVO.setAnnouncementType(null);
        }else {

            /**
             * 获取当前的公告类型
             */
            ArticleType articleType = new ArticleType();
            articleType.setTypeId(announcementOptional.get().getAnnouncementType());
            Optional<ArticleType> articleTypeOptional = articleTypeService.selectOne(Example.of(articleType));
            if (!articleTypeOptional.isPresent()){
                announcementNormalVO.setAnnouncementType(null);
            }else {
                announcementNormalVO.setAnnouncementType(articleTypeOptional.get().getTypeName());
            }
        }
        return announcementNormalVO;
    }



    /**
     * 显示删除公告详情
     * @param announcementId
     * @return
     */
    @Override
    public AnnouncementDeleteVO queryDelete(String announcementId){
        /**
         * 判断公告是否存在
         */
        Announcement announcement = new Announcement();
        announcement.setAnnouncementId(announcementId);
        announcement.setStatus(AnnouncementStatusEnum.DELETED.getStatus());
        Optional<Announcement> announcementOptional = selectOne(Example.of(announcement));
        if (!announcementOptional.isPresent()){
            throw new AnnouncementException(ErrorCodeEnum.ANNOUNCEMENT_NO_EXIT);
        }
        AnnouncementDeleteVO announcementDeleteVO = new AnnouncementDeleteVO();
        announcementDeleteVO.setAccessory(announcementOptional.get().getAccessory());
        announcementDeleteVO.setContent(announcementOptional.get().getContent());
        announcementDeleteVO.setPublisherAvatar(announcementOptional.get().getPublisherAvatar());
        announcementDeleteVO.setPublisherNick(announcementOptional.get().getPublisherNick());
        announcementDeleteVO.setTitle(announcementOptional.get().getTitle());
        announcementDeleteVO.setUpdatedAt(announcementOptional.get().getUpdatedAt());
        announcementDeleteVO.setPublisherId(announcementOptional.get().getPublisherId());
        announcementDeleteVO.setDeleterId(announcementOptional.get().getDeleterId());
        announcementDeleteVO.setDeleterMobile(announcementOptional.get().getDeleterMobile());
        announcementDeleteVO.setRank(announcementOptional.get().getRank());
        /**
         * 判断当前公告类型是否为空
         */
        if (announcementOptional.get().getAnnouncementType()==null){
            announcementDeleteVO.setAnnouncementType(null);
        }else {

            /**
             * 获取当前的公告类型
             */
            ArticleType articleType = new ArticleType();
            articleType.setTypeId(announcementOptional.get().getAnnouncementType());
            Optional<ArticleType> articleTypeOptional = articleTypeService.selectOne(Example.of(articleType));
            if (!articleTypeOptional.isPresent()){
                announcementDeleteVO.setAnnouncementType(null);
            }else {
                announcementDeleteVO.setAnnouncementType(articleTypeOptional.get().getTypeName());
            }
        }
        return announcementDeleteVO;
    }

}
