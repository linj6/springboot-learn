package com.lnjecit.common.aspect;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author lnj
 * @create 2018-05-07 21:00
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@Inherited
public @interface Log {

    String description();

}
