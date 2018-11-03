package com.lnjecit.dao;

import com.lnjecit.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lnj
 * createTime 2018-11-03 9:59
 **/
@Component
public class UserDao {
    public List<User> query() {
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setName("lnj");
        user.setPassword("123456");
        list.add(user);
        return list;
    }
}
