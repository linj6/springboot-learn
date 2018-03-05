package com.lnjecit.service.system.impl;

import com.lnjecit.common.base.BaseServiceImpl;
import com.lnjecit.dao.system.UserDao;
import com.lnjecit.entity.system.User;
import com.lnjecit.service.system.UserService;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author lnj
 * @create 2018-03-02 15:06
 **/
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {

}
