package cn.lichuachua.mp_management.mp_managementserver.web.hanlder;

import cn.lichuachua.mp_management.common.enums.BaseErrorCodeEnum;
import cn.lichuachua.mp_management.core.exception.SysException;
import cn.lichuachua.mp_management.mp_managementserver.wrapper.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 李歘歘
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultWrapper<?> handle(Exception e){
        e.printStackTrace();
        if (e instanceof SysException){
            return ResultWrapper.failure(((SysException) e).getCode(),e.getMessage());
        }
        return ResultWrapper.failure(BaseErrorCodeEnum.UN_KNOW_EXCEPTION.getCode(), BaseErrorCodeEnum.UN_KNOW_EXCEPTION.getMessage());
    }


}
