package cn.lcdiao.springboot.aspect;

import cn.lcdiao.springboot.annotation.MyToken;
import cn.lcdiao.springboot.enums.ResultEnum;
import cn.lcdiao.springboot.exception.DataCenterException;
import cn.lcdiao.springboot.message.DefaultMessage;
import cn.lcdiao.springboot.message.Message;
import cn.lcdiao.springboot.message.MessageBuilder;
import cn.lcdiao.springboot.util.IllegalStrFilterUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

/**
 * 调用接口打印性能日志以及接口报错之后记录错误日志
 */
@Component
@Aspect
public class InterfaceRequestErrrorAndPerformanceLog {

    public static final Logger logger = LoggerFactory.getLogger(InterfaceRequestErrrorAndPerformanceLog.class);


    /**在controller包下aop*/
    @Pointcut("execution(* cn.lcdiao.springboot.controller.*.*(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public Message handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable{
        Stopwatch stopwatch = Stopwatch.createStarted();
        Message result;
        try {
            logger.info("执行Controller开始:" + pjp.getSignature() + "参数:" + Lists.newArrayList(pjp.getArgs()).toString());



            //处理注解
//            MethodSignature signature = (MethodSignature) pjp.getSignature();
//            Method method = pjp.getTarget().getClass().getMethod(signature.getName(),signature.getMethod().getParameterTypes());
//            Annotation[] annotations = method.getAnnotations();
//            for (Annotation a:annotations) {
//                //自定义注解
//                if (a instanceof MyToken) {
//                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//                    HttpServletRequest request = attributes.getRequest();
//                    String token = request.getHeader("Authorization");
//                }
//            }




            //处理入参特殊字符和sql注入攻击
            //checkRequestParam(pjp);
            //执行访问接口操作
            result = (Message) pjp.proceed(pjp.getArgs());
            try {
                logger.info("执行Controller结束:" + pjp.getSignature() + "返回值:" + JSONObject.toJSONString(result));
                //此处将日志打印放入try-catch是因为项目中有些对象实体bean过于复杂，导致序列化为json的时候报错，但是此处报错并不影响主要功能使用，只是返回结果日志没有打印，所以catch中也不做抛出异常处理
            } catch (Exception e) {
                logger.error(pjp.getSignature() + "接口记录返回结果失败！原因为:{}", e.getMessage());

            }
            //消费时间
            Long consumeTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            logger.info("耗时: " + consumeTime + " 毫秒.");
            //TODO 请求时间过长，标记为异常调用时间，并记录
        } catch (Exception e) {
            result = handleException(pjp,e);
        }
        return result;
    }

    /**
     * 处理接口调用异常
     */
    private Message handleException(ProceedingJoinPoint pjp,Throwable e) {
        Message message;
        if (e instanceof DataCenterException) {
          logger.error("DataCenterException{方法: " + pjp.getSignature() + ",参数: " + pjp.getArgs() +
                  ",\n异常: " + e.getMessage() + "}",e);
          message = MessageBuilder.createMessage(ResultEnum.PARAM_ERROR);

        } else if (e instanceof JWTDecodeException || e instanceof SignatureVerificationException) {
            logger.error("token异常{方法: " + pjp.getSignature() + ",参数: " + pjp.getArgs() + "\n异常： " + e.getMessage());
            message = MessageBuilder.createMessage(ResultEnum.TOKEN_ERROR);

        } else if (e instanceof TokenExpiredException) {
            logger.error("token过期{方法: " + pjp.getSignature() + ",参数: " + pjp.getArgs() + "\n异常： " + e.getMessage());
            message = MessageBuilder.createMessage(ResultEnum.TOKEN_OVERDUE);

        } else if (e instanceof DataIntegrityViolationException) {
            logger.error("DataIntegrityViolationException{方法: " + pjp.getSignature() + ",参数: " + pjp.getArgs() +
                    ",\n异常: " + e.getMessage() + "}",e);
            message = MessageBuilder.createMessage(ResultEnum.PARAM_ERROR);

        } else if(e instanceof ConnectException) {
            logger.error("ConnectException{方法: " + pjp.getSignature() + ",参数: " + pjp.getArgs() +
                    ",\n异常: " + e.getMessage() + "}",e);
            message = MessageBuilder.createMessage(ResultEnum.OVERTIME);

        }else if (e instanceof RuntimeException) {
            logger.error("RuntimeException{方法: " + pjp.getSignature() + ",参数: " + pjp.getArgs() +
                    ",\n异常: " + e.getMessage() + "}",e);
            message = MessageBuilder.createMessage(ResultEnum.ERROR);

        }  else {
            logger.error("Exception{方法：" + pjp.getSignature() + "， 参数：" + pjp.getArgs() +
                    ",\n异常：" + e.getMessage() + "}", e);
            message = MessageBuilder.createMessage(ResultEnum.ERROR);
        }
        return message;
    }

    private void checkRequestParam(ProceedingJoinPoint pjp){
        String str = Lists.newArrayList(pjp.getArgs()).toString();
        if (!IllegalStrFilterUtil.sqlStrFilter(str)) {
            logger.info("访问接口：" + pjp.getSignature() + "，输入参数存在SQL注入风险！参数为：" + str);

            //抛出异常
            throw new DataCenterException("输入参数存在SQL注入风险！参数为：" + str);
        }
//        if (!IllegalStrFilterUtil.isIllegalStr(str)) {
//            logger.info("访问接口：" + pjp.getSignature() + ",输入参数含有非法字符!，参数为：" + Lists.newArrayList(pjp.getArgs()).toString());
//            DcErrorEntity dcErrorEntity = interfaceErrorService.processDcErrorEntity(pjp.getSignature() + "",Lists.newArrayList(pjp.getArgs()).toString(),"输入参数含有非法字符!");
//            throw new DataCenterException(dcErrorEntity);
//        }
    }




}
