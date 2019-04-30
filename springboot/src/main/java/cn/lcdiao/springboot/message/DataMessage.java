package cn.lcdiao.springboot.message;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/4/30 15:05
 */
public class DataMessage<T> implements Message {
    private String code;
    private String msg;
    private T data;
    @Override
    public String getCode() {
        return code;
    }
    @Override
    public void setCode(String code) {
        this.code = code;
    }
    @Override
    public String getMsg() {
        return msg;
    }
    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DataMessage(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
