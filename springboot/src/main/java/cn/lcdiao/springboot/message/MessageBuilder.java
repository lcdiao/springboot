package cn.lcdiao.springboot.message;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/4/30 15:31
 */
public class MessageBuilder {
    public static Message createMessage(String code,String msg){
        return new DefaultMessage(code,msg);
    }
    public static Message createMessage(String code,String msg,Object data){
        return new DataMessage(code,msg,data);
    }
}
