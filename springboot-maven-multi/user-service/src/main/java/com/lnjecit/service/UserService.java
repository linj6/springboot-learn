package com.lnjecit.service;

import com.lnjecit.dao.UserDao;
import com.lnjecit.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lnj
 * createTime 2018-11-03 10:01
 **/
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public List<User> query() {
        return userDao.query();
    }

}
