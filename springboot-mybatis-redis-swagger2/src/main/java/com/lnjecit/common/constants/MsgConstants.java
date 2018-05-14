package com.lnjecit.common.constants;

/**
 * MsgConstants 消息常量
 *
 * @author lnj
 * @create 2018-03-03 16:10
 **/
public interface MsgConstants {

    String SAVE_SUCCESS = "新增成功";
    String SAVE_FAIL = "新增失败";
    String UPDATE_SUCCESS = "修改成功";
    String UPDATE_FAIL = "修改失败";
    String DELETE_SUCCESS = "删除成功";
    String DELETE_FAIL = "删除失败";
    String QUERY_SUCCESS = "查询成功";
    String QUERY_FAIL = "查询失败";

    String ENTITY_CAN_NOT_NULL = "实体对象不能为空";
    String ID_CAN_NOT_NULL = "id不能为空";


    String NOT_LOGIN = "未登录";

    String USERNAME_OR_PASSWORD_ERROR = "用户名或密码错误";
    String USERNAME_CANNOT_BE_NUMERIC = "用户名不能为纯数字";
    String USERNAME_HAS_EXIST = "用户名已存在";
    String USERNAME_FORMAT_ERROR = "用户名格式错误";

    String PASSWORD_CAN_NOT_NULL = "密码不能为空";
    String PASSWORD_HASH_ERROR = "密码加密错误";

    String MOBILE_FORMAT_ERROR = "手机号格式错误";

}
