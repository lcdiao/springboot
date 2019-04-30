package cn.lcdiao.springboot;

import cn.lcdiao.springboot.util.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class SpringbootApplicationTests {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void contextLoads() {
        redisUtils.set("k1","v1");
        System.out.println(redisUtils.get("k1"));
    }

}
