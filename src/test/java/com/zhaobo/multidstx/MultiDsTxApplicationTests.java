package com.zhaobo.multidstx;

import com.zhaobo.multidstx.model.Message;
import com.zhaobo.multidstx.model.User;
import com.zhaobo.multidstx.service.BussinessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiDsTxApplicationTests {

    @Resource
    private BussinessService service;

    @Test
    public void test1() {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setTitle("message.");
        message.setContent("this is a message");
        User user = new User();
        user.setAge(19);
        user.setId(UUID.randomUUID().toString());
        user.setName("lisi");

        Boolean aBoolean = service.sendMessage(message,user);
        System.out.println(aBoolean);

    }

}
