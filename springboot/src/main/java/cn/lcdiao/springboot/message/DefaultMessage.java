package cn.lcdiao.springboot.message;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/4/30 15:05
 */
public class DefaultMessage implements Message {
    private String code;
    private String msg;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DefaultMessage(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "DefaultMessage{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
