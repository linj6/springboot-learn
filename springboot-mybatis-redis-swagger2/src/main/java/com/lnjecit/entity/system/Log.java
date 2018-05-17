package com.lnjecit.entity.system;

import com.lnjecit.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Create by lnj
 * create time: 2018-05-15 13:29:20
 */
@ApiModel(value = "日志", description = "日志")
@Getter
@Setter
@ToString
public class Log extends BaseEntity {

    private String ip;//ip
    private String ipAddress;//ip所在地区
    private String description;//
    private String username;//操作用户
    private String requestUri;//URI
    private String requestUrl;//URL
    private String requestMethod;//请求类型
    private String className;//类名称
    private String methodName;//方法名称
    private String arguments;//参数
    private String result;//返回结果
    private Long startTime;//开始时间
    private Long endTime;//结束时间
    private Long spendTime;//消耗时间

}
