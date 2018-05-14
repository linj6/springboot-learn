package com.lnjecit.entity.system;

import com.lnjecit.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Create by lnj
 * create time: 2018-05-13 22:11:22
 */
@ApiModel(value = "用户", description = "用户")
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    @ApiModelProperty(value = "用户名", name = "username", required = true)
    @NotBlank(message = "用户名不能为空")
    @Length(max = 32, message = "用户名最大长度为32位")
    private String username;//用户名

    @ApiModelProperty(value = "昵称", name = "nickname")
    @Length(max = 32, message = "昵称最大长度为32")
    private String nickname;//昵称

    @ApiModelProperty(value = "密码", name = "password")
    @Length(max = 32, message = "密码最大长度为32")
    private String password;//密码

    @ApiModelProperty(value = "真实姓名", name = "realname")
    @Length(max = 32, message = "真实姓名最大长度为32")
    private String realname;//真实姓名

    @ApiModelProperty(value = "手机号码", name = "mobile")
    @Length(max = 11, message = "手机号码最大长度为11")
    private String mobile;//手机号码

    @ApiModelProperty(value = "邮箱", name = "email")
    @Length(max = 32, message = "邮箱最大长度为32")
    private String email;//邮箱

    @ApiModelProperty(value = "头像", name = "avatar")
    @Length(max = 255, message = "头像最大长度为255")
    private String avatar;//头像

    @ApiModelProperty(value = "注册ip", name = "registerIp")
    @Length(max = 64, message = "注册ip最大长度为64")
    private String registerIp;//注册ip

    @ApiModelProperty(value = "最后登录时间", name = "lastLoginTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;//最后登录时间

    @ApiModelProperty(value = "最后登录ip", name = "lastLoginIp")
    @Length(max = 64, message = "最后登录ip最大长度为64")
    private String lastLoginIp;//最后登录ip

    @ApiModelProperty(value = "最后登录地区", name = "lastLoginAddress")
    @Length(max = 64, message = "最后登录地区最大长度为64")
    private String lastLoginAddress;//最后登录地区
}
