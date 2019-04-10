package cn.lichuachua.mp_management.mp_managementserver.exception;

import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class InformCommentException extends BaseException {
    public InformCommentException(int code, String message) {super(code, message);}

    public InformCommentException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
