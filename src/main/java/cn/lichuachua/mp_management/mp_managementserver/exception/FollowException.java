package cn.lichuachua.mp_management.mp_managementserver.exception;

import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class FollowException extends BaseException {
    public FollowException(int code, String message) {super(code, message);}

    public FollowException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
