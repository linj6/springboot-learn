package com.lnjecit.service.goods.impl;

import com.lnjecit.common.base.BaseServiceImpl;
import com.lnjecit.common.constants.Constants;
import com.lnjecit.common.constants.MsgConstants;
import com.lnjecit.common.result.Result;
import com.lnjecit.dao.goods.GoodsCategoryDao;
import com.lnjecit.entity.goods.GoodsCategory;
import com.lnjecit.service.goods.GoodsCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lnj
 * @create 2018-04-27 13:18
 **/
@Service
public class GoodsCategoryServiceImpl extends BaseServiceImpl<GoodsCategoryDao, GoodsCategory> implements GoodsCategoryService {

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result insert(GoodsCategory goodsCategory) {
        Result checkResult = checkBeforeEdit(goodsCategory);
        if (null != checkResult) {
            return checkResult;
        }
        Long parentId = goodsCategory.getParentId();
        // 新增一级分类
        if (null == parentId || Constants.ROOT_CATEGORY_ID.equals(parentId) ) {
            goodsCategory.setParentId(Constants.ROOT_CATEGORY_ID);
            goodsCategory.setLevel(Constants.ONE);
        } else {
            // 新增非一级分类
            GoodsCategory parentGoodsCategory = getById(parentId);
            if (null != parentGoodsCategory) {
                Integer parentLevel = parentGoodsCategory.getLevel();
                if (null != parentLevel) {
                    goodsCategory.setLevel(parentLevel + 1);
                } else {
                    // TODO 重新设置父分类的level
                }
            } else {
                logger.error("id为" + parentId + "的商品分类不存在");
                return Result.error("id为" + parentId + "的商品分类不存在");
            }
        }
        return super.insert(goodsCategory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result update(GoodsCategory goodsCategory) {
        Result checkResult = checkBeforeEdit(goodsCategory);
        if (null != checkResult) {
            return checkResult;
        }
        Long id = goodsCategory.getId();
        Long parentId = goodsCategory.getParentId();
        GoodsCategory oldGoodsCategory = super.getById(id);
        oldGoodsCategory.setName(goodsCategory.getName());
        oldGoodsCategory.setSort(goodsCategory.getSort());
        if (!parentId.equals(oldGoodsCategory.getParentId())) {
            // TODO 如果父分类被修改,如何处理当前分类的level及分类的子分类的level

        }
        return super.update(oldGoodsCategory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result deleteLogical(Long id) {
        // 删除子分类和子分类下的子分类
        List<Long> allChildIds = getAllChildIds(id);
        for (Long childId : allChildIds) {
            super.deleteLogical(childId);
        }
        return super.deleteLogical(id);
    }

    @Override
    public List<GoodsCategory> getByParentId(Long parentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        return query(params);
    }

    @Override
    public List<GoodsCategory> getAllChildsRecurse(Long id, List<GoodsCategory> allChildGoodsCategories) {
        List<GoodsCategory> goodsCategories = getByParentId(id);
        if (!goodsCategories.isEmpty()) {
            allChildGoodsCategories.addAll(goodsCategories);
            for (GoodsCategory goodsCategory : goodsCategories) {
                getAllChildsRecurse(goodsCategory.getId(), allChildGoodsCategories);
            }
        }
        return allChildGoodsCategories;
    }

    @Override
    public List<GoodsCategory> getGoodsCategoryTreeList() {
        // 获取所有商品分类
        List<GoodsCategory> allGoodsCategories = queryAll();

        List<GoodsCategory> goodsCategoryList = new ArrayList<>();
        for (GoodsCategory goodsCategory : allGoodsCategories) {
            // 先找到所有的一级商品分类
            if (Constants.ROOT_CATEGORY_ID.equals(goodsCategory.getParentId()) ) {
                goodsCategoryList.add(goodsCategory);
            }
        }
        // 为一级商品分类设置子分类，getSubGoodsCategories是递归调用的
        for (GoodsCategory goodsCategory : goodsCategoryList) {
            getSubGoodsCategories(goodsCategory.getId(), allGoodsCategories, goodsCategory);
        }
        return goodsCategoryList;
    }

    /**
     * 新增或修改之前相关校验
     * @param goodsCategory
     * @return
     */
    private Result checkBeforeEdit(GoodsCategory goodsCategory) {
        if (null == goodsCategory) {
            return Result.error(MsgConstants.ENTITY_CAN_NOT_NULL);
        }
        Long id = goodsCategory.getId();
        Long parentId = goodsCategory.getParentId();
        String name = goodsCategory.getName();
        // 新增
        if (null == id) {
            if (isExistNameByParentId(parentId, name)) {
                return Result.error("该分类下已有名为" + name + "的子分类");
            }
        } else {
            // 修改
            GoodsCategory oldGoodsCategory = super.getById(id);
            if (null != oldGoodsCategory) {
                // 分类名称被修改
                if (!name.equals(oldGoodsCategory.getName())) {
                    if (isExistNameByParentId(parentId, name)) {
                        return Result.error("该分类下已有名为" + name + "的子分类");
                    }
                }
            } else {
                return Result.error("id为" + parentId + "的商品分类不存在");
            }
        }
        return null;
    }

    /**
     * 父分类下是否存在同名子分类
     *
     * @param parentId 父分类id
     * @param name     分类名称
     * @return
     */
    private boolean isExistNameByParentId(Long parentId, String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        params.put("goodsCategoryName", name);
        List<GoodsCategory> goodsCategoryList = query(params);
        return (null != goodsCategoryList && goodsCategoryList.size() > 0);
    }

    /**
     * 通过id递归查询子分类id集合
     *
     * @param id 商品分类id
     * @return
     */
    private List<Long> getAllChildIds(Long id) {
        List<Long> allChildIds = new ArrayList<>();
        List<GoodsCategory> allChildGoodsCategories = new ArrayList<>();
        // 递归查询所有子分类（包括直接子分类和子分类的子分类）
        allChildGoodsCategories = getAllChildsRecurse(id, allChildGoodsCategories);
        for (GoodsCategory goodsCategory : allChildGoodsCategories) {
            allChildIds.add(goodsCategory.getId());
        }
        return allChildIds;
    }

    private List<GoodsCategory> getSubGoodsCategories(Long prentId, List<GoodsCategory> allGoodsCategories, GoodsCategory sourceGoodsCategory) {
        List<GoodsCategory> goodsCategories = new ArrayList<>();
        for (GoodsCategory goodsCategory : allGoodsCategories) {
            if (prentId.longValue() == goodsCategory.getParentId().longValue()) {
                goodsCategories.add(goodsCategory);
            }
        }

        // 退出递归条件：该分类下无子分类，
        if (!goodsCategories.isEmpty()) {
            sourceGoodsCategory.setSubGoodsCategories(goodsCategories);
            for (GoodsCategory goodsCategory : goodsCategories) {
                getSubGoodsCategories(goodsCategory.getId(), allGoodsCategories, goodsCategory);
            }
        }
        return goodsCategories;
    }
    

}
