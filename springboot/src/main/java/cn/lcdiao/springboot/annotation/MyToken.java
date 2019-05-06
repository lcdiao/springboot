package cn.lcdiao.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//注解的生命周期
@Retention(RetentionPolicy.RUNTIME)
//注解的应用范围
@Target(ElementType.METHOD)
public @interface MyToken {
}
