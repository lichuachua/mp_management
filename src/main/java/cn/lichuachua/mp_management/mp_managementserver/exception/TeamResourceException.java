package cn.lichuachua.mp_management.mp_managementserver.exception;

import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class TeamResourceException extends BaseException {
    public TeamResourceException(int code, String message){super(code,message);}
    public TeamResourceException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
