package com.lnjecit.factory;

import com.lnjecit.domain.User;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lnj
 * @description 用户数据工厂
 * @date 2019-04-20 22:08
 **/
public class UserDataFacotry {

    private final static CopyOnWriteArrayList<User> LIST = new CopyOnWriteArrayList<>();

    static {
        LIST.add(User.builder().userId(1L).username("李白").build());
        LIST.add(User.builder().userId(2L).username("杜甫").build());
    }

    public static User getUserById(Long userId) {
        for (User user : LIST) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public static List<User> getUserList(Long userId, String username) {
        List<User> userList = new ArrayList<>();
        if (userId != null) {
            Stream<User> stream = LIST.stream();
            userList.addAll(stream.filter(user -> user.getUserId().equals(userId)).collect(Collectors.toList()));
        } else {
            userList = new ArrayList<>(LIST);
        }

        if (StringUtils.isNotBlank(username)) {
            userList.removeIf(user -> !user.getUsername().equals(username));
        }
        return userList;
    }

    public synchronized static Long getNextUserId() {
        List<User> list = getUserList(null, null);
        return (long) (list.size() + 1);
    }

    public static Long createUser(User user) {
        Long userId = getNextUserId();
        user.setUserId(userId);
        LIST.add(user);
        return userId;
    }

    public static Integer updateUser(User user) {
        Long userId = user.getUserId();
        User oldUser = getUserById(userId);
        if (oldUser != null) {
            oldUser.setUsername(user.getUsername());
            return 1;
        }
        return 0;
    }
}
