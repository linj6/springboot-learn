package com.lnjecit.dao.system;

import com.lnjecit.common.base.BaseDao;
import com.lnjecit.entity.system.UserRole;
import org.apache.ibatis.annotations.Param;

/**
 * Create by lnj
 * create time: 2018-05-15 22:08:15
 */
public interface UserRoleDao extends BaseDao<UserRole> {

    /**
     * 通过用户id删除用户与角色关联关系
     *
     * @param userId 用户id
     */
    void deleteByUserId(@Param("userId") Long userId);
}
