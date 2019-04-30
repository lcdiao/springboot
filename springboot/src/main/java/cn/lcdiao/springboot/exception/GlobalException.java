package cn.lcdiao.springboot.exception;

import cn.lcdiao.springboot.enums.ResultEnum;
import cn.lcdiao.springboot.message.DefaultMessage;
import cn.lcdiao.springboot.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * 处理AOP处理不了的异常
 */
@ControllerAdvice
public class GlobalException {
    public static final Logger logger = LoggerFactory.getLogger(GlobalException.class);
    /**
     * 处理空参数转换异常(发生在进入controller之前，在这里处理)
     * @param e
     * @param request
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Message handleMessageNotReadableException(Exception e, WebRequest request) {
        logger.error("HttpMessageNotReadableException;",e);
        return new DefaultMessage(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
    }

}
