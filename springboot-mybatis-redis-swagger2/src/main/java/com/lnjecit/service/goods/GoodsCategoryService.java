package com.lnjecit.service.goods;

import com.lnjecit.common.base.BaseService;
import com.lnjecit.entity.goods.GoodsCategory;

import java.util.List;

public interface GoodsCategoryService extends BaseService<GoodsCategory> {

    /**
     * 根据父分类id查询子分类
     * @param parentId 父分类id
     * @return
     */
    List<GoodsCategory> getByParentId(Long parentId);

    /**
     * 通过分类id递归查询分类下的所有子分类（直接子分类和子分类的子分类）
     *
     * @param id 分类id
     * @param allChildGoodsCategories 所有子分类
     * @return
     */
    List<GoodsCategory> getAllChildsRecurse(Long id, List<GoodsCategory> allChildGoodsCategories);

    /**
     * 将商品分类列表以树形结构返回
     *
     * @return
     */
    List<GoodsCategory> getGoodsCategoryTreeList();

}
