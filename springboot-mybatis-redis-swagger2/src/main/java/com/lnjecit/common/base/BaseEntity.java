package com.lnjecit.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity 实体基类
 * 适用于主键id为int类型
 * @author lnj
 * @create 2018-03-02 10:16
 **/
@Getter
@Setter
public class BaseEntity implements Serializable {

    /**
     * 唯一标识
     */
    @ApiModelProperty(name = "id", value = "id")
    private Long id;
    /**
     * 创建人id
     */
    @ApiModelProperty(name = "createUserId", value = "创建人id", hidden = true)
    private Long createUserId;
    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime", value = "创建时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改人id
     */
    @ApiModelProperty(name = "updateUserId", value = "修改人id", hidden = true)
    private Long updateUserId;
    /**
     * 修改时间
     */
    @ApiModelProperty(name = "updateTime", value = "修改时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 删除标识 1:正常 0:已删除
     */
    @ApiModelProperty(name = "del", value = "删除标识", hidden = true)
    private Integer del;
}
