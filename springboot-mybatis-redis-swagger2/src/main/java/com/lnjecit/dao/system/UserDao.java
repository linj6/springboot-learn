package com.lnjecit.dao.system;

import com.lnjecit.common.base.BaseDao;
import com.lnjecit.entity.system.User;
import org.apache.ibatis.annotations.Param;

/**
 * Create by lnj
 * create time: 2018-05-13 22:34:10
 */
public interface UserDao extends BaseDao<User> {

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return
     */
    User getByUsername(@Param("username") String username);
}
