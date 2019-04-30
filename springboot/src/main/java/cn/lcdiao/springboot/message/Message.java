package cn.lcdiao.springboot.message;

/**
 * 封装返回给前端的数据
 */
public interface Message {

    /**
     * 返回状态码
     * @return  状态码
     */
    String getCode();

    /**
     * 设置状态码
     * @param code
     */
    void setCode(String code);

    /**
     * @return 说明
     */
    String getMsg();

    /**
     * 设置说明
     * @param msg
     */
    void setMsg(String msg);

}
