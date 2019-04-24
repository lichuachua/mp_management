package cn.lichuachua.mp_management.mp_managementserver.service.impl;

import cn.lichuachua.mp_management.common.util.CodeUtil;
import cn.lichuachua.mp_management.common.util.TokenUtil;
import cn.lichuachua.mp_management.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp_management.mp_managementserver.dto.TokenInfo;
import cn.lichuachua.mp_management.mp_managementserver.dto.VerificationCodeInfo;
import cn.lichuachua.mp_management.mp_managementserver.entity.Admin;
import cn.lichuachua.mp_management.mp_managementserver.entity.School;
import cn.lichuachua.mp_management.mp_managementserver.entity.User;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.AdminStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.SchoolStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.enums.UserStatusEnum;
import cn.lichuachua.mp_management.mp_managementserver.exception.AdminException;
import cn.lichuachua.mp_management.mp_managementserver.exception.UserException;
import cn.lichuachua.mp_management.mp_managementserver.form.*;
import cn.lichuachua.mp_management.mp_managementserver.repository.redis.IRedisRepository;
import cn.lichuachua.mp_management.mp_managementserver.service.*;
import cn.lichuachua.mp_management.mp_managementserver.util.MD5Util;
import cn.lichuachua.mp_management.mp_managementserver.util.SendCodeUtil;
import cn.lichuachua.mp_management.mp_managementserver.vo.AdminListVO;
import cn.lichuachua.mp_management.mp_managementserver.vo.AdminVO;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 李歘歘
 * 用户业务类接口实现类
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin,String> implements IAdminService {

    @Autowired
    private IRedisRepository redisRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISchoolService schoolService;

    @Autowired
    private IAcademyService academyService;

    @Autowired
    private IAnnouncementService announcementService;

    /**
     * 给予管理员权限
     * @param adminRegisterForm
     * @param userId
     */
    @Override
    public void register(@Valid AdminRegisterForm adminRegisterForm, String userId) {
         /**
          * 查看该用户是否在用户表里面而且状态正常
         */
        User user = new User();
        user.setMobile(adminRegisterForm.getMobile());
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        Optional<User> userOptional = userService.selectOne(Example.of(user));
        if (!userOptional.isPresent()){
        throw new UserException(ErrorCodeEnum.ERROR_USER_OR_MOBILE_DELETE);
          }
        /**
         * 获取当前登录用户 的mobile
         */
        Optional<Admin> adminOptional1 = selectByKey(userId);
        /**
         * 检验手机是否已经是管理员
         */
        Admin admin = new Admin();
        admin.setMobile(adminRegisterForm.getMobile());
        Optional<Admin> adminOptional = selectOne(Example.of(admin));
        /**
         * 存在，该用户已经是管理
         */
        if (adminOptional.isPresent()){
        throw new AdminException(ErrorCodeEnum.ADMIN_EXIT);
        }
        admin.setStatus(AdminStatusEnum.NORMAL.getStatus());
        admin.setAcademyId(userOptional.get().getAcademyId());
        admin.setSchoolId(userOptional.get().getSchoolId());
        admin.setAdminAvatar(userOptional.get().getUserAvatar());
        admin.setAdminEmail(userOptional.get().getUserEmail());
        admin.setAdminName(userOptional.get().getUserName());
        admin.setAdminNick(userOptional.get().getUserNick());
        admin.setAdminNumber(userOptional.get().getUserNumber());
        admin.setPassword(userOptional.get().getPassword());
         /**
          * 权限的的等级必须小于当前登录用户的等级
          */
         if (adminOptional1.get().getRank()>=adminRegisterForm.getRank()){
             throw new AdminException(ErrorCodeEnum.RANL_ERROR);
         }
            admin.setRank(adminRegisterForm.getRank());
            admin.setGiverId(userId);
            admin.setGiverMobile(adminOptional1.get().getMobile());
            admin.setGiverName(adminOptional1.get().getGiverName());
            admin.setCreatedAt(new Date());
            admin.setUpdatedAt(new Date());
            save(admin);
    }

    /**
     * 检测验证码和手机号,正确则将验证码和手机号的关系删除，不正确则返回验证码不正确，
     * @param mobile
     * @param code
     */
    @Override
    public void verification(String mobile, String code){

        /**
         * 根据手机号查出验证码
         */
        String smsCode = redisRepository.findVerificationCodeByMobile(mobile);
        System.out.println(smsCode);
        /**
         * 验证码不存在提示验证码异常
         */
        if (smsCode == null){
            throw new AdminException(ErrorCodeEnum.VERIFICATION_CODE_EXCEPTION);
        }

        /**
         * 验证码存在，正确则删除，不正确则提示验证码错误
         */
        if (smsCode.equals(code)){
            redisRepository.deleteVerificationCodeByMobile(mobile);
        }else {
            throw new AdminException(ErrorCodeEnum.VERIFICATION_CODE_ERROR);
        }

    }



    /**
     * 用户登录
     */
    @Override
    public TokenInfo login(AdminLoginForm adminLoginForm) {
        /**
         * 查找用户
         */
        Admin admin = new Admin();
        admin.setMobile(adminLoginForm.getMobile());
        admin.setStatus(AdminStatusEnum.NORMAL.getStatus());
        Optional<Admin> optionalUser = selectOne(Example.of(admin));
        /**
         * 查找该用户并且状态正常则允许登录
         */
        if (!optionalUser.isPresent()){
            throw new AdminException(ErrorCodeEnum.MOBILE_NOT_REGISTERED);
        }else {
            admin = optionalUser.get();
        }
        /**
         * 判断密码是否正确
         */
        if (!MD5Util.string2MD5(adminLoginForm.getPassword()).equals(admin.getPassword())){
            throw new AdminException(ErrorCodeEnum.PASSWORD_ERROR);
        }

        /**
         * 生成accessToken
         */
        String accessToken = TokenUtil.genToken();

        /**
         * 将accessToken和用户信息存入Redis ，并删除旧的Token
         */
        String adminId = admin.getAdminId();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserId(adminId);
        tokenInfo.setAccessToken(accessToken);


        //获取旧的Token并删除，---------通知客户端在其他地方登录
        String olderAccessToken = redisRepository.findAccessTokenByUserId(adminId);
        if (null !=olderAccessToken){
            //删除旧的Token
            redisRepository.deleteAccessToken(olderAccessToken);
        }

        //保存新的Token，更新当前用户使用的Token
        redisRepository.saveAccessToken(tokenInfo);
        redisRepository.saveUserAccessToken(adminId,accessToken);


        /**
         * 返回登录结果
         */
        return  tokenInfo;
    }

    /**
     * 发送验证码
     * @param sendCodeForm
     * @throws ClientException
     */
    @Override
    public void sendCode(SendCodeForm sendCodeForm) throws ClientException {
        /**
         * 生成验证码
         */
        String smsCode = CodeUtil.smsCode();

        /**
         * 发送验证码
         */
        SendCodeUtil.sendSms(sendCodeForm.getMobile(), smsCode);

        /**
         * 存储手机号和短信验证码的关系到redis
         */
        VerificationCodeInfo verificationCodeInfo = new VerificationCodeInfo();
        verificationCodeInfo.setTelephone(sendCodeForm.getMobile());
        verificationCodeInfo.setCode(smsCode);

        //保存验证码和手机号到redis
        redisRepository.saveVerificationCode(verificationCodeInfo);
    }


    /**
     * 退出登录
     * @param adminId
     */
    public void logout(String adminId) {
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        Optional<Admin> userOptional = selectOne(Example.of(admin));
        /**
         * 查找到该用户
         */
        if (userOptional.isPresent()){
            /**
             * 获取并
             * 删除token
             */
            String token = redisRepository.findAccessTokenByUserId(adminId);
            redisRepository.deleteAccessToken(token);
            redisRepository.deleteUserAccessToken(adminId);
        }else {
            throw new AdminException(ErrorCodeEnum.ERROR_USER);
        }
    }

    /**
     * 修改密码
     * @param changePasswordForm
     * @param adminId
     */
    @Override
    public void changePassword(ChangePasswordForm changePasswordForm, String adminId) {
        /**
         * 判断该管理员原密码和状态是否正常
         */
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setStatus(AdminStatusEnum.NORMAL.getStatus());
        admin.setPassword(changePasswordForm.getFormerPassword());
        Optional<Admin> adminOptional = selectOne(Example.of(admin));
        if (!adminOptional.isPresent()){
            throw new AdminException(ErrorCodeEnum.FORMER_PASSWORD_ERROR);
        }
        admin.setRank(adminOptional.get().getRank());
        admin.setUpdatedAt(new Date());
        admin.setCreatedAt(adminOptional.get().getCreatedAt());
        admin.setGiverName(adminOptional.get().getGiverName());
        admin.setGiverMobile(adminOptional.get().getMobile());
        admin.setGiverId(adminOptional.get().getGiverId());
        admin.setAdminNumber(adminOptional.get().getAdminNumber());
        admin.setAdminNick(adminOptional.get().getAdminNick());
        admin.setAdminName(adminOptional.get().getAdminName());
        admin.setAdminEmail(adminOptional.get().getAdminEmail());
        admin.setAdminAvatar(adminOptional.get().getAdminAvatar());
        admin.setSchoolId(adminOptional.get().getSchoolId());
        admin.setAcademyId(adminOptional.get().getAcademyId());
        admin.setMobile(adminOptional.get().getMobile());
        admin.setPassword(changePasswordForm.getPassword());
        update(admin);
    }

    /**
     * 根据状态查出管理员列表
     * @param status
     * @return
     */
    @Override
    public List<AdminListVO> queryList(Integer status){
        List<Admin> adminList = selectAll();
        List<AdminListVO> adminListVOList = new ArrayList<>();
        for (Admin admin : adminList){
            AdminListVO adminListVO = new AdminListVO();
            if (admin.getStatus().equals(status)){
                adminListVO.setAdminId(admin.getAdminId());
                adminListVO.setAdminName(admin.getAdminName());
                adminListVO.setAdminEmail(admin.getAdminEmail());
                adminListVO.setAdminMobile(admin.getMobile());
                adminListVO.setCreatedAt(admin.getCreatedAt());
                adminListVO.setGiverMobile(admin.getGiverMobile());
                adminListVO.setGiverName(admin.getGiverName());
                /**
                 * 调用根据adminId和schoolId查询schoolName接口
                 */
                adminListVO.setSchoolName(schoolService.querySchoolName(admin.getAdminId(), admin.getSchoolId()));
                BeanUtils.copyProperties(admin, adminListVO);
                adminListVOList.add(adminListVO);
            }
        }
        return adminListVOList;
    }


    /**
     * 更新管理员状态
     * @param adminId
     * @param status
     */
    @Override
    public void updatedStatus(String adminId1, String adminId, Integer status){
        /**
         * 查看管理员是否存在
         */
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setStatus(status);
        Optional<Admin> adminOptional = selectOne(Example.of(admin));
        if (!adminOptional.isPresent()){
            throw new AdminException(ErrorCodeEnum.ADMIN_NO_EXIT);
        }
        /**
         * 查看当前登录的管理员的等级是否大于操作的管理员等级
         */
        /**
         * 根据adminId1取出当前登录的管理员rank；
         */
        Optional<Admin> adminOptional1 = selectByKey(adminId1);
        if (adminOptional1.get().getRank() >= adminOptional.get().getRank()){
            throw new AdminException(ErrorCodeEnum.NO_JURISDICTION);
        }
        /**
         * 当传进来的当前的admin状态是0--》》1
         * 1--》》0
         */
        if (status.equals(AdminStatusEnum.NORMAL.getStatus())){
            admin.setStatus(AdminStatusEnum.DELETED.getStatus());
        }else if (status.equals(AdminStatusEnum.DELETED.getStatus())){
            admin.setStatus(AdminStatusEnum.NORMAL.getStatus());
        }
        admin.setRank(adminOptional.get().getRank());
        admin.setUpdatedAt(new Date());
        admin.setCreatedAt(adminOptional.get().getCreatedAt());
        /**
         * 修改Giver信息
         */
        admin.setGiverName(adminOptional1.get().getGiverName());
        admin.setGiverMobile(adminOptional1.get().getMobile());
        admin.setGiverId(adminId);
        admin.setAdminNumber(adminOptional.get().getAdminNumber());
        admin.setAdminNick(adminOptional.get().getAdminNick());
        admin.setAdminName(adminOptional.get().getAdminName());
        admin.setAdminEmail(adminOptional.get().getAdminEmail());
        admin.setAdminAvatar(adminOptional.get().getAdminAvatar());
        admin.setSchoolId(adminOptional.get().getSchoolId());
        admin.setAcademyId(adminOptional.get().getAcademyId());
        admin.setMobile(adminOptional.get().getMobile());
        admin.setPassword(adminOptional.get().getPassword());
        update(admin);
    }


    /**
     * 查看自己的信息
     * @param adminId
     * @return
     */
    @Override
    public AdminVO queryMyInformation(String adminId){
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setStatus(AdminStatusEnum.NORMAL.getStatus());
        Optional<Admin> adminOptional = selectOne(Example.of(admin));
        if (!adminOptional.isPresent()){
            throw new AdminException(ErrorCodeEnum.ADMIN_NO_EXIT);
        }
        AdminVO adminVO = new AdminVO();
        adminVO.setAdminNick(adminOptional.get().getAdminNick());
        adminVO.setAdminName(adminOptional.get().getAdminName());
        adminVO.setAdminMobile(adminOptional.get().getMobile());
        adminVO.setAdminEmail(adminOptional.get().getAdminEmail());
        adminVO.setAdminNumber(adminOptional.get().getAdminNumber());
        adminVO.setGiverName(adminOptional.get().getGiverName());
        adminVO.setGiverMobile(adminOptional.get().getGiverMobile());
        adminVO.setCreatedAt(adminOptional.get().getCreatedAt());
        adminVO.setSchoolName(schoolService.querySchoolName(adminId, adminOptional.get().getSchoolId()));
        adminVO.setAcademyName(academyService.queryAcademyName(adminId,adminOptional.get().getAcademyId()));
        adminVO.setRank(adminOptional.get().getRank());
        return adminVO;
    }


    /**
     * 更换头像
     * @param fileName
     * @param adminId
     */
    @Override
    public void updateAvatar(String fileName, String adminId){
        /**
         * 查看用户是否存在
         */
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setStatus(AdminStatusEnum.NORMAL.getStatus());
        Optional<Admin> adminOptional = selectOne(Example.of(admin));
        if (!adminOptional.isPresent()){
            throw new AdminException(ErrorCodeEnum.ADMIN_NO_EXIT);
        }
        admin.setRank(adminOptional.get().getRank());
        admin.setUpdatedAt(new Date());
        admin.setCreatedAt(adminOptional.get().getCreatedAt());
        admin.setGiverName(adminOptional.get().getGiverName());
        admin.setGiverMobile(adminOptional.get().getMobile());
        admin.setGiverId(adminOptional.get().getGiverId());
        admin.setAdminNumber(adminOptional.get().getAdminNumber());
        admin.setAdminNick(adminOptional.get().getAdminNick());
        admin.setAdminName(adminOptional.get().getAdminName());
        admin.setAdminEmail(adminOptional.get().getAdminEmail());
        admin.setAdminAvatar(fileName);
        admin.setSchoolId(adminOptional.get().getSchoolId());
        admin.setAcademyId(adminOptional.get().getAcademyId());
        admin.setMobile(adminOptional.get().getMobile());
        admin.setPassword(adminOptional.get().getPassword());
        update(admin);
        /**
         * 同步更新announcement表的头像
         */
        announcementService.updateAvatar(adminId, fileName);
    }


    /**
     * 修改管理员信息
     * @param adminInforForm
     * @param adminId
     */
    @Override
    public void infor(AdminInforForm adminInforForm, String adminId){
        /**
         * 查看用户是否存在
         */
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setStatus(AdminStatusEnum.NORMAL.getStatus());
        Optional<Admin> adminOptional = selectOne(Example.of(admin));
        if (!adminOptional.isPresent()){
            throw new AdminException(ErrorCodeEnum.ADMIN_NO_EXIT);
        }
        admin.setRank(adminOptional.get().getRank());
        admin.setUpdatedAt(new Date());
        admin.setCreatedAt(adminOptional.get().getCreatedAt());
        admin.setGiverName(adminOptional.get().getGiverName());
        admin.setGiverMobile(adminOptional.get().getMobile());
        admin.setGiverId(adminOptional.get().getGiverId());
        admin.setAdminNumber(adminInforForm.getAdminNumber());
        admin.setAdminNick(adminInforForm.getAdminNick());
        admin.setAdminName(adminInforForm.getAdminName());
        admin.setAdminEmail(adminInforForm.getAdminEmail());
        admin.setAdminAvatar(adminOptional.get().getAdminAvatar());
        admin.setSchoolId(adminInforForm.getSchoolId());
        admin.setAcademyId(adminInforForm.getAcademyId());
        admin.setMobile(adminOptional.get().getMobile());
        admin.setPassword(adminOptional.get().getPassword());
        update(admin);
    }

}
