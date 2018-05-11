package com.lnjecit.entity.goods;

import com.lnjecit.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * author name: lnj
 * create time: 2018-04-27 13:06:03
 */
@ApiModel(value = "商品分类对象", description = "商品分类对象")
@Getter
@Setter
@ToString
public class GoodsCategory extends BaseEntity {

	@ApiModelProperty(value = "手机号", name = "mobile", required = true)
	@NotBlank(message = "分类名称不能为空")
	@Length(max = 64, message = "分类名称最大长度为64位")
	private String name;//分类名称

	@ApiModelProperty(value = "等级", name = "level", hidden = true)
	private Integer level;//等级

	@ApiModelProperty(value = "父类id", name = "parentId")
	private Long parentId;//父类id

	@ApiModelProperty(value = "排序", name = "sort", required = true)
	private Integer sort;//排序

	@ApiModelProperty(value = "子分类集合", name = "subGoodsCategories", hidden = true)
	private List<GoodsCategory> subGoodsCategories = new ArrayList<>();

}
