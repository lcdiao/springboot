package cn.lcdiao.springboot.controller;

import cn.lcdiao.springboot.entity.User;
import cn.lcdiao.springboot.enums.ResultEnum;
import cn.lcdiao.springboot.message.Message;
import cn.lcdiao.springboot.message.MessageBuilder;
import cn.lcdiao.springboot.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/4/30 15:26
 */
@Controller
@RequestMapping("/api/user")
//防止跨域
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/getUser")
    @ResponseBody
    public Message getUser(@RequestBody JSONObject jsonObject) {
        if (null == jsonObject) {
            return MessageBuilder.createMessage(ResultEnum.PARAM_ERROR);
        }
        Long id = jsonObject.getLong("id");
        User user = userService.getUser(id);
        return MessageBuilder.createSuccessMessage(user);
    }

    @PostMapping("/insertUser")
    @ResponseBody
    public Message insertUser(@RequestBody User user) {
        if (null == user) {
            return MessageBuilder.createMessage(ResultEnum.PARAM_ERROR);
        }
        userService.insertUser(user);
        return MessageBuilder.createSuccessMessage();
    }
}
