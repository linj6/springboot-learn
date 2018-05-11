package com.lnjecit.common.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnjecit.common.constants.Constants;
import com.lnjecit.common.constants.MsgConstants;
import com.lnjecit.common.exception.ServiceException;
import com.lnjecit.common.page.SimplePage;
import com.lnjecit.common.result.Result;
import com.lnjecit.common.util.SpringContextUtils;
import com.lnjecit.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseServiceImpl
 *
 * @author lnj
 * @create 2018-03-02 13:50
 **/
public class BaseServiceImpl<D extends BaseDao<E>, E extends BaseEntity> implements BaseService<E> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ORDER_BY = "orderBy";
    private D dao;
    private Class<E> entityClass;

    public BaseServiceImpl() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        Class<D> serviceClass = (Class<D>) pt.getActualTypeArguments()[0];
        entityClass = (Class<E>) pt.getActualTypeArguments()[1];
        dao = SpringContextUtils.getBean(serviceClass);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result insert(E entity) {
        if (null == entity) {
            return Result.error(MsgConstants.ENTITY_CAN_NOT_NULL);
        }
        try {
            beforeInsert(entity);
            dao.insert(entity);
            return Result.success(MsgConstants.SAVE_SUCCESS);
        } catch (Exception e) {
            logger.error(MsgConstants.SAVE_FAIL, e);
            throw new ServiceException(MsgConstants.SAVE_FAIL);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result update(E entity) {
        if (null == entity) {
            return Result.error(MsgConstants.ENTITY_CAN_NOT_NULL);
        }
        Long id = entity.getId();
        if (null == id) {
            return Result.error(MsgConstants.ID_CAN_NOT_NULL);
        }
        E oldEntity = dao.getById(id);
        if (null == oldEntity) {
            return Result.error("id为" + id + "的记录不存在");
        }
        try {
            beforeUpdate(entity);
            dao.update(entity);
            return Result.success(MsgConstants.UPDATE_SUCCESS);
        } catch (Exception e) {
            logger.error(MsgConstants.UPDATE_FAIL, e);
            throw new ServiceException(MsgConstants.UPDATE_FAIL);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result delete(Long id) {
        if (null == id) {
            return Result.error(MsgConstants.ID_CAN_NOT_NULL);
        }
        E oldEntity = dao.getById(id);
        if (null == oldEntity) {
            return Result.error("id为" + id + "的记录不存在");
        }
        try {
            dao.delete(id);
            return Result.success(MsgConstants.DELETE_SUCCESS);
        } catch (Exception e) {
            logger.error(MsgConstants.DELETE_FAIL, e);
            throw new ServiceException(MsgConstants.DELETE_FAIL);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result deleteLogical(Long id) {
        if (null == id) {
            return Result.error(MsgConstants.ID_CAN_NOT_NULL);
        }
        E oldEntity = dao.getById(id);
        if (null == oldEntity) {
            return Result.error("id为" + id + "的记录不存在");
        }
        try {
            dao.deleteLogical(id);
            return Result.success(MsgConstants.DELETE_SUCCESS);
        } catch (Exception e) {
            logger.error(MsgConstants.DELETE_FAIL, e);
            throw new ServiceException(MsgConstants.DELETE_FAIL);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result deleteLogical(E entity) {
        try {
            dao.deleteLogical(entity);
            return Result.success(MsgConstants.DELETE_SUCCESS);
        } catch (Exception e) {
            logger.error(MsgConstants.DELETE_FAIL, e);
            throw new ServiceException(MsgConstants.DELETE_FAIL);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result deleteLogicalBatch(Long[] ids) {
        try {
            for (Long id : ids) {
                dao.deleteLogical(id);
                return Result.success(MsgConstants.DELETE_SUCCESS);
            }
        } catch (Exception e) {
            logger.error(MsgConstants.DELETE_FAIL, e);
            throw new ServiceException(MsgConstants.DELETE_FAIL);
        }
        return null;
    }

    @Override
    public E getById(Long id) {
        try {
            return dao.getById(id);
        } catch (Exception e) {
            logger.error(MsgConstants.QUERY_FAIL, e);
            throw new ServiceException(MsgConstants.QUERY_FAIL);
        }
    }

    @Override
    public List<E> query(Map<String, Object> param) {
        if (!CollectionUtils.isEmpty(param)) {
            Object orderByParam = param.get(ORDER_BY);
            if (null != orderByParam && StringUtil.isNotBlank(String.valueOf(orderByParam))) {
                param.put(ORDER_BY, StringUtil.camel2Underline(String.valueOf(orderByParam)));
            }
        }
        try {
            return dao.query(param);
        } catch (Exception e) {
            logger.error(MsgConstants.QUERY_FAIL, e);
            throw new ServiceException(MsgConstants.QUERY_FAIL);
        }
    }

    @Override
    public List<E> queryAll() {
        try {
            return dao.query(null);
        } catch (Exception e) {
            logger.error(MsgConstants.QUERY_FAIL, e);
            throw new ServiceException(MsgConstants.QUERY_FAIL);
        }
    }

    @Override
    public Result queryPage(Map<String, Object> queryFilter, PageInfo<?> pageInfo) {
        try {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
            List<E> list = query(queryFilter);
            // 用PageInfo对结果进行包装
            PageInfo page = new PageInfo(list);
            // 分页信息封装
            SimplePage simplePage = new SimplePage();
            simplePage.setCurrentPageNum(page.getPageNum());
            simplePage.setPageSize(page.getPageSize());
            simplePage.setTotal(page.getTotal());
            simplePage.setTotalPages(page.getPages());
            // 返回结果封装
            Map<String, Object> map = new HashMap<>();
            map.put(Constants.LIST, page.getList());
            map.put(Constants.PAGE, simplePage);
            return Result.success(map);
        } catch (Exception e) {
            logger.error(MsgConstants.QUERY_FAIL, e);
            throw new ServiceException(MsgConstants.QUERY_FAIL);
        }
    }

    /**
     * 新增前
     *
     * @param entity
     */
    protected void beforeInsert(E entity) {
        Date date = new Date();
        //entity.setId(null);
        entity.setDel(Constants.YES);
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        // TODO 创建人 修改人 先写死，整合shiro后再获取
        entity.setCreateUserId(1L);
        entity.setUpdateUserId(1L);
    }

    /**
     * 修改前
     *
     * @param entity
     */
    protected void beforeUpdate(E entity) {
        Date date = new Date();
        entity.setUpdateTime(date);
        // TODO 修改人
    }

}
