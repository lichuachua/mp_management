package cn.lichuachua.mp_management.mp_managementserver.exception;

import cn.lichuachua.mp_management.common.enums.BaseErrorCodeEnum;
import cn.lichuachua.mp_management.core.exception.SysException;
import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author  李歘歘
 */

public class BaseException extends SysException {

    public BaseException(int code, String message){
        super(message);
        this.code = code;
    }
    public BaseException(BaseErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}

    public BaseException(ErrorCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.getMessage(), args));
        this.code = codeEnum.getCode();
    }
}
