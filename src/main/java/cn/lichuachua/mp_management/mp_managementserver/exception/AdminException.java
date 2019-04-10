package cn.lichuachua.mp_management.mp_managementserver.exception;

import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class AdminException extends BaseException {
    public AdminException(int code, String message) {
        super(code, message);
    }
    public AdminException(ErrorCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
    }
}
