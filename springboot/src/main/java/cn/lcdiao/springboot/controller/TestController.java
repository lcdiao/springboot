package cn.lcdiao.springboot.controller;

import cn.lcdiao.springboot.enums.ResultEnum;
import cn.lcdiao.springboot.message.Message;
import cn.lcdiao.springboot.message.MessageBuilder;
import cn.lcdiao.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/4/30 15:26
 */
@Controller
@RequestMapping("/api/test")
//防止跨域
@CrossOrigin
public class TestController {

    @Resource
    private UserService userService;

    @GetMapping("/getHello")
    @ResponseBody
    public Message getHello() {
        Map m = new HashMap();
        m.put("hello","world");
        m.put("date",new Date());
        return MessageBuilder.createMessage(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),m);
    }
}
