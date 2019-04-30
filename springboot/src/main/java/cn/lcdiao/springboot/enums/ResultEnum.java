package cn.lcdiao.springboot.enums;

/**
 * 返回结果的状态码和消息
 */
public enum ResultEnum {
    UNKONW_ERROR("222222","未知错误"),

    SUCCESS("000000","成功"),

    PARAM_ERROR("000001","参数异常"),

    ERROR("111111","请求失败"),

    OVERTIME("200002","请求超时"),

    TOKEN_ERROR("300001","token错误"),
    TOKEN_OVERDUE("300002","token过期"),
    ;

    private String code;

    private String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
