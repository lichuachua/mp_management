package cn.lichuachua.mp_management.mp_managementserver.exception;

import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class ArticleException extends BaseException {
    public ArticleException(int code, String message){super(code,message);}
    public ArticleException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
