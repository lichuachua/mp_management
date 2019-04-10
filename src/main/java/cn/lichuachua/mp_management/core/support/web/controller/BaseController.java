package cn.lichuachua.mp_management.core.support.web.controller;

import cn.lichuachua.mp_management.common.constant.SysConstant;
import cn.lichuachua.mp_management.common.enums.BaseErrorCodeEnum;
import cn.lichuachua.mp_management.common.util.ThreadLocalMap;
import cn.lichuachua.mp_management.core.exception.SysException;
import org.springframework.validation.BindingResult;

/**
 * @author 李歘歘
 * 通用Controller类
 * @param <T> 当前登录的Java用户
 */
public class BaseController<T> {

    protected T getCurrentUserInfo(){
        T currentUser = (T) ThreadLocalMap.get(SysConstant.THREAD_LOCAL_KEY_LOGIN_ADMIN);

        if (null==currentUser) {
            throw new SysException(BaseErrorCodeEnum.NO_ADMIN_INFO_FOUND);
        }
        return currentUser;
    }

    protected void validateParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SysException(BaseErrorCodeEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
