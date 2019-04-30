package cn.lcdiao.springboot.service;

import cn.lcdiao.springboot.entity.User;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/4/30 15:18
 */
public interface UserService {

    /**
     * 通过id获取user对象
     * @param id 主键
     * @return user
     */
    User getUser(Long id);

    /**
     * 插入user对象
     * @param user 对象
     * @return 插入的条数，一般只有1条
     */
    int insertUser(User user);
}
