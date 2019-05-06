package cn.lcdiao.springboot.service.impl;

import cn.lcdiao.springboot.dao.UserMapper;
import cn.lcdiao.springboot.entity.User;
import cn.lcdiao.springboot.exception.DataCenterException;
import cn.lcdiao.springboot.service.UserService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/4/30 15:18
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    //在业务层处理事务

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "testcache",key = "#id")
    public User getUser(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        //没数据就抛出异常，由aop处理并返回错误码到前端
        if (user == null) {
            throw new DataCenterException("参数异常");
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(User user) {
        int n = userMapper.insert(user);
        if (n != 1) {
            throw new DataCenterException("参数异常");
        }
        return n;
    }
}
