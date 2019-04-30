package cn.lcdiao.springboot.exception;

/**
 * 随便做的自定义异常。。。
 */
public class DataCenterException extends RuntimeException {
    private static final long serialVersionUID = 1234567890L;

    public DataCenterException(String msg) {
        super(msg);
    }
}
