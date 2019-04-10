package cn.lichuachua.mp_management.mp_managementserver.exception;

import cn.lichuachua.mp_management.mp_managementserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class AnnouncementException extends BaseException {
    public AnnouncementException(int code, String message) {
        super(code, message);
    }
    public AnnouncementException(ErrorCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
    }
}
