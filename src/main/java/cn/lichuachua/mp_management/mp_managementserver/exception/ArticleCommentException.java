package cn.lichuachua.mp_management.mp_managementserver.exception;


import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李宇豪
 */
public class ArticleCommentException extends BaseException {

    public ArticleCommentException(int code, String message) {
        super(code, message);
    }

    public ArticleCommentException(ErrorCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
    }
}
