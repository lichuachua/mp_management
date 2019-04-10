package cn.lichuachua.mp_management.mp_managementserver.exception;

import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class InformArticleException extends BaseException {
    public InformArticleException(int code, String message) {super(code, message);}

    public InformArticleException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
