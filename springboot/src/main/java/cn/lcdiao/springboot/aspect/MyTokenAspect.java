package cn.lcdiao.springboot.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/5/6 9:47
 */
//@Component
//@Aspect
public class MyTokenAspect {

    @Pointcut("@annotation(cn.lcdiao.springboot.annotation.MyToken)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object aspectToken(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("方法执行之前");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String token = request.getHeader("Authorization");
        System.out.println(token);

        Object result = joinPoint.proceed();

        System.out.println("方法执行之后");

        return result;
    }
}
