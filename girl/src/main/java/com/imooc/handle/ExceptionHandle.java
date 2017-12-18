package com.imooc.handle;

import com.imooc.domain.Result;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.GirlException;
import com.imooc.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler
    @ResponseBody
    public Result handle(Exception e) {
        if(e instanceof GirlException) {
            GirlException girlException = (GirlException) e;

            return ResultUtil.error(girlException.getCode(), girlException.getMessage());
        } else {
            logger.info("[系统异常] {}", e );
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR);
        }
    }
}
