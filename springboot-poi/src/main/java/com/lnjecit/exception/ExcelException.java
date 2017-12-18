package com.lnjecit.exception;

/**
 * 导入导出excel异常处理
 *
 * @author
 * @create 2017-12-17 16:12
 **/
public class ExcelException extends Exception {

    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }


}
