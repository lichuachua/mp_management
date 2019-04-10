package cn.lichuachua.mp_management.mp_managementserver.exception;

import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class ArticleCollectException extends BaseException {
    public ArticleCollectException(int code, String message) {super(code, message);}

    public ArticleCollectException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
