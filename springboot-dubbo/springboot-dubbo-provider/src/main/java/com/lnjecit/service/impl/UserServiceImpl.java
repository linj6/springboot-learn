package com.lnjecit.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lnjecit.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lnj
 * createTime 2018-11-05 20:56
 **/
@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<String> query() {
        List<String> userList = new ArrayList<>();
        userList.add("李白");
        userList.add("杜甫");
        userList.add("白居易");
        return userList;
    }
}
