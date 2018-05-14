package com.lnjecit.service.system;

import com.lnjecit.common.base.BaseService;
import com.lnjecit.entity.system.User;

/**
 * Create by lnj
 * create time: 2018-05-13 22:34:10
 */
public interface UserService extends BaseService<User> {

    /**
     * 用户名是否存在
     *
     * @param username 用户名
     * @return
     */
    boolean existUsername(String username);
}
