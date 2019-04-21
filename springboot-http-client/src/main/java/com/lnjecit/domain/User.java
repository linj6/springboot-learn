package com.lnjecit.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lnj
 * @description 用户实体
 * @date 2019-04-20 22:42
 **/
@Builder
@Data
public class User implements Serializable {
    private Long userId;
    private String username;
}
