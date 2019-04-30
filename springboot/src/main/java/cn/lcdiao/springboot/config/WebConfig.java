package cn.lcdiao.springboot.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: diao
 * @Description: 返回指定日期格式并将Long型转为String类型
 * @Date: 2019/4/23 14:48
 */
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Override
    public void configureMessageConverters(List converters) {

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper =new ObjectMapper();
/**
 * 序列换成json时,将所有的long变成string
 * 因为js中得数字类型不能包含所有的java long值
 */
        SimpleModule simpleModule =new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);

        converters.add(jackson2HttpMessageConverter);

    }
}