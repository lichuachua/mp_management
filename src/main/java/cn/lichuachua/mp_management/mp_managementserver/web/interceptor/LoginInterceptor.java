package cn.lichuachua.mp_management.mp_managementserver.web.interceptor;

import cn.lichuachua.mp_management.common.constant.SysConstant;
import cn.lichuachua.mp_management.common.util.JsonUtil;
import cn.lichuachua.mp_management.common.util.ThreadLocalMap;
import cn.lichuachua.mp_management.mp_managementserver.constant.mutualPlatformConstant;
import cn.lichuachua.mp_management.mp_managementserver.dto.TokenInfo;
import cn.lichuachua.mp_management.mp_managementserver.dto.AdminInfoDTO;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp_management.mp_managementserver.service.ITokenService;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李歘歘
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private ITokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String accessToken = null;

        accessToken = request.getHeader(mutualPlatformConstant.HTTP_HEADER_MA_ACCESS_TOKEN);

        if (null == accessToken) {
            accessToken = request.getParameter(mutualPlatformConstant.PQE_PARAM_MA_ACCESS_TOKEN);
        }


        ResultWrapper  resultWrapper =null;
        response.setHeader("Content-Type", "application/json;charset=utf-8");

        /**
         * 用户没有传递token，返回错误信息给用户
         */
        if (null == accessToken) {
            ThreadLocalMap.remove(SysConstant.THREAD_LOCAL_KEY_LOGIN_ADMIN);
            resultWrapper = ResultWrapper.failure(ErrorCodeEnum.UNAUTHORIZED);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.getWriter().println(JsonUtil.toJson(resultWrapper));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        /**
         * token错误
         */
        TokenInfo tokenInfo = tokenService.getTokenInfo(accessToken);
        if (null == tokenInfo) {
            ThreadLocalMap.remove(SysConstant.THREAD_LOCAL_KEY_LOGIN_ADMIN);
            resultWrapper = ResultWrapper.failure(ErrorCodeEnum.BAD_ACCESS_TOKEN);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().println(JsonUtil.toJson(resultWrapper));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        /**
         * 将UserInfo设置到ThreadLocal
         */
        AdminInfoDTO adminInfoDTO = new AdminInfoDTO();
        adminInfoDTO.setUserId(tokenInfo.getUserId());
        ThreadLocalMap.put(SysConstant.THREAD_LOCAL_KEY_LOGIN_ADMIN, adminInfoDTO);
        return true;
    }
}
