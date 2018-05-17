package com.lnjecit.service.system;

import com.lnjecit.common.base.BaseService;
import com.lnjecit.entity.system.Permission;

import java.util.List;

/**
 * Create by lnj
 * create time: 2018-05-17 21:26:59
 */
public interface PermissionService extends BaseService<Permission> {

    /**
     * 根据权限id查询下级权限
     *
     * @param parentId 权限id
     * @return
     */
    List<Permission> getByParentId(Long parentId);

    /**
     * 通过权限id递归查询所有下级权限
     *
     * @param id                  权限id
     * @param allChildPermissions 所有权限
     * @return
     */
    List<Permission> getAllChildsRecurse(Long id, List<Permission> allChildPermissions);

    /**
     * 将权限列表以树形结构返回
     *
     * @return
     */
    List<Permission> getPermissionTreeList();
}
