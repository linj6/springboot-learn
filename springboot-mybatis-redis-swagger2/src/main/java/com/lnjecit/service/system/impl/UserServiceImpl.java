package com.lnjecit.service.system.impl;

import com.lnjecit.common.base.BaseServiceImpl;
import com.lnjecit.common.constants.MsgConstants;
import com.lnjecit.common.exception.ServiceException;
import com.lnjecit.common.result.Result;
import com.lnjecit.common.shiro.ShiroUtils;
import com.lnjecit.common.util.PasswordHash;
import com.lnjecit.common.util.PhoneFormatCheckUtil;
import com.lnjecit.common.util.StringUtil;
import com.lnjecit.dao.system.UserDao;
import com.lnjecit.dao.system.UserRoleDao;
import com.lnjecit.entity.system.User;
import com.lnjecit.entity.system.UserRole;
import com.lnjecit.service.IpAddressService;
import com.lnjecit.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Create by lnj
 * create time: 2018-05-13 22:34:10
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private IpAddressService ipAddressService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result insert(User user) {
        String password = user.getPassword();
        if (StringUtil.isBlank(password)) {
            return Result.error(MsgConstants.PASSWORD_CAN_NOT_NULL);
        }
        String mobile = user.getMobile();
        // 手机号格式校验
        if (StringUtil.isNotBlank(mobile) && !PhoneFormatCheckUtil.isPhoneLegal(mobile)) {
            return Result.error(MsgConstants.MOBILE_FORMAT_ERROR);
        }
        String username = user.getUsername();
        Result checkUsernameResult = checkUsername(username);
        if (null != checkUsernameResult) {
            return checkUsernameResult;
        }
        // 密码加密
        try {
            user.setPassword(PasswordHash.createHash(password));
        } catch (Exception e) {
            logger.error(MsgConstants.PASSWORD_HASH_ERROR, e);
            throw new ServiceException(MsgConstants.PASSWORD_HASH_ERROR);
        }
        return super.insert(user);
    }

    @Override
    public Result update(User user) {
        String mobile = user.getMobile();
        // 手机号格式校验
        if (StringUtil.isNotBlank(mobile) && !PhoneFormatCheckUtil.isPhoneLegal(mobile)) {
            return Result.error(MsgConstants.MOBILE_FORMAT_ERROR);
        }
        String username = user.getUsername();
        User oldUser = super.getById(user.getId());
        // 如果用户名被修改
        if (!username.equals(oldUser.getUsername())) {
            Result checkUsernameResult = checkUsername(username);
            if (null != checkUsernameResult) {
                return checkUsernameResult;
            }
        }
        oldUser.setUsername(user.getUsername());
        oldUser.setNickname(user.getNickname());
        oldUser.setRealname(user.getRealname());
        oldUser.setMobile(user.getMobile());
        oldUser.setEmail(user.getEmail());
        oldUser.setAvatar(user.getAvatar());
        return super.update(oldUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateLastLoginInfo() {
        Date lastLoginTime = new Date();
        Long userId = ShiroUtils.getUserId();
        String lastLoginIp = ShiroUtils.getLastLoginIp();

        User oldUser = super.getById(userId);
        oldUser.setLastLoginTime(lastLoginTime);
        oldUser.setLastLoginIp(lastLoginIp);
        oldUser.setLastLoginAddress(ipAddressService.getAddressByIp(lastLoginIp));

        super.update(oldUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void selectRoles(Long userId, Long[] roleIds) throws Exception {
        // 通过用户id删除用户与角色关联关系
        userRoleDao.deleteByUserId(userId);

        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setDefaultValueForFields();
            userRoleDao.insert(userRole);
        }
    }

    @Override
    public boolean existUsername(String username) {
        // 用户名是否已存在
        User userList = userDao.getByUsername(username);
        if (null != userList) {
            return true;
        }
        return false;
    }

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    /**
     * 校验用户名
     *
     * @param username 用户名
     * @return
     */
    private Result checkUsername(String username) {
        // 用户名不能为纯数字
        if (StringUtil.isNumeric(username)) {
            return Result.error(MsgConstants.USERNAME_CANNOT_BE_NUMERIC);
        }
        // 用户名格式校验
        if (!StringUtil.usernameFormatCheck(username)) {
            return Result.error(MsgConstants.USERNAME_FORMAT_ERROR);
        }
        // 用户名是否已存在
        if (existUsername(username)) {
            return Result.error(MsgConstants.USERNAME_HAS_EXIST);
        }
        return null;
    }
}
