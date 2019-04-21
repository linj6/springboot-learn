package com.lnjecit.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author lnj
 * @description 获取用户请求的返回结果
 * @date 2019-04-20 21:16
 **/
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class GetUserListResponse extends AbstractResponse {

    private List<GetUserDTO> data;

    @Builder
    @Data
    public static class GetUserDTO {
        private Long userId;
        private String username;
    }
}
