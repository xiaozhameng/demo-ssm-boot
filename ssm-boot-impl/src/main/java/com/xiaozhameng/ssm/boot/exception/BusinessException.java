package com.xiaozhameng.ssm.boot.exception;

/**
 * @author xiaozhameng
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = -3375946018095049929L;
    private String code = "UNKNOWN";
    private String message = "未设置异常编码";
    private Object errorObj;

    public BusinessException() {
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String code, Object errorObj) {
        this.code = code;
        this.errorObj = errorObj;
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String code, String message, Throwable throwable) {
        super(throwable);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getErrorObj() {
        return errorObj;
    }

    public void setErrorObj(Object errorObj) {
        this.errorObj = errorObj;
    }
}
