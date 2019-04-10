package cn.lichuachua.mp_management.mp_managementserver.exception;

import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class AcademyException extends BaseException {
    public AcademyException(int code, String message){super(code,message);}
    public AcademyException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
