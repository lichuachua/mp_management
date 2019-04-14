package cn.lichuachua.mp_management.mp_managementserver.exception;


import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class TeamException extends BaseException {
    public TeamException(int code, String message){super(code,message);}
    public TeamException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
