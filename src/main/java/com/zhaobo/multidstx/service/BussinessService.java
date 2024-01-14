package com.zhaobo.multidstx.service;

import com.zhaobo.multidstx.annotation.MultiTransactional;
import com.zhaobo.multidstx.mapper.db1.MessageMapper;
import com.zhaobo.multidstx.mapper.db2.UserMapper;
import com.zhaobo.multidstx.model.Message;
import com.zhaobo.multidstx.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BussinessService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserMapper userMapper;


    @MultiTransactional(transactionManagers = {"transactionManager1", "transactionManager2"})
    public Boolean sendMessage(Message message, User user) {
        int ret1 = messageMapper.insertSelective(message);
        int i = 1 / 0;
        int ret2 = userMapper.insertSelective(user);
//        int j = 1/0;

        // other http invoking method
        return ret1 + ret2 == 2;
    }
}
