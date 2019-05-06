package cn.lcdiao.springboot.message;

import cn.lcdiao.springboot.enums.ResultEnum;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/4/30 15:31
 */
public class MessageBuilder {

    public static Message createSuccessMessage(){return new DefaultMessage(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage());};
    public static <T> Message createSuccessMessage(T data){return new DataMessage(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),data);};

    public static Message createMessage(ResultEnum resultEnum){
        return new DefaultMessage(resultEnum.getCode(),resultEnum.getMessage());
    }
}
