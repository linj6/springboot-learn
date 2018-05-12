package com.lnjecit.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * BaseDao dao父接口
 *
 * @author lnj
 * @create 2018-03-02 10:31
 **/
public interface BaseDao<E extends BaseEntity> {

    /**
     * 新增
     * @param entity
     * @return
     */
    int insert(E entity);

    /**
     * 修改
     * @param entity
     * @return
     */
    int update(E entity);

    /**
     * 通过id物理删除
     * @param id
     * @return
     */
    int delete(@Param("id") Long id);

    /**
     * 通过id逻辑删除 设置del字段值为0
     * @param id
     * @return
     */
    int deleteLogical(@Param("id") Long id);

    /**
     * 逻辑删除（适用于根据多个参数来删除记录）
     *
     * @param entity
     * @return
     */
    int deleteLogical(E entity);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    E getById(@Param("id") Long id);

    /**
     * 根据参数查询返回持久化对象集合
     * @param params 查询参数
     * @return
     */
    List<E> query(Map<String, Object> params);

    /**
     * 查询全部持久化对象
     * @return
     */
    List<E> queryAll();

    /**
     * 分页查询
     * @param params
     * @return
     */
    List<E> queryPage(Map<String, Object> params);

}
