package com.lnjecit.service.system.impl;

import com.lnjecit.common.base.BaseServiceImpl;
import com.lnjecit.common.constants.Constants;
import com.lnjecit.common.constants.MsgConstants;
import com.lnjecit.common.result.Result;
import com.lnjecit.dao.system.PermissionDao;
import com.lnjecit.entity.system.Permission;
import com.lnjecit.service.system.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by lnj
 * create time: 2018-05-17 21:26:59
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDao, Permission> implements PermissionService {

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result insert(Permission permission) {
        Result checkResult = checkBeforeEdit(permission);
        if (null != checkResult) {
            return checkResult;
        }
        Long parentId = permission.getParentId();
        // parentId为空，说明是新增目录 设置默认值为 0
        if (null == parentId) {
            permission.setParentId(Constants.ROOT_CATEGORY_ID);
        }
        return super.insert(permission);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result update(Permission permission) {
        Result checkResult = checkBeforeEdit(permission);
        if (null != checkResult) {
            return checkResult;
        }
        Permission oldPermission = super.getById(permission.getId());
        oldPermission.setName(permission.getName());
        oldPermission.setParentId(permission.getParentId());
        oldPermission.setPermissions(permission.getPermissions());
        oldPermission.setUrl(permission.getUrl());
        oldPermission.setIcon(permission.getIcon());
        oldPermission.setSort(permission.getSort());
        oldPermission.setStatus(permission.getStatus());
        oldPermission.setType(permission.getType());
        return super.update(oldPermission);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result deleteLogical(Long id) {
        // 删除所有下级权限
        List<Long> allChildIds = getAllChildIds(id);
        for (Long childId : allChildIds) {
            super.deleteLogical(childId);
        }
        return super.deleteLogical(id);
    }

    @Override
    public List<Permission> getByParentId(Long parentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        return query(params);
    }

    /**
     * 通过id递归查询下级权限id集合
     *
     * @param id 权限id
     * @return
     */
    private List<Long> getAllChildIds(Long id) {
        List<Long> allChildIds = new ArrayList<>();
        List<Permission> allChildPermissions = new ArrayList<>();
        // 递归查询所有下级权限（包括直接下级权限和下级权限的下级权限）
        allChildPermissions = getAllChildsRecurse(id, allChildPermissions);
        for (Permission permission : allChildPermissions) {
            allChildIds.add(permission.getId());
        }
        return allChildIds;
    }

    @Override
    public List<Permission> getAllChildsRecurse(Long id, List<Permission> allChildPermissions) {
        List<Permission> permissions = getByParentId(id);
        if (!permissions.isEmpty()) {
            allChildPermissions.addAll(permissions);
            for (Permission permission : permissions) {
                getAllChildsRecurse(permission.getId(), allChildPermissions);
            }
        }
        return allChildPermissions;
    }

    @Override
    public List<Permission> getPermissionTreeList() {
        // 获取所有权限
        List<Permission> allPermissions = queryAll();

        List<Permission> permissionList = new ArrayList<>();
        for (Permission permission : allPermissions) {
            // 先找到所有的一级权限
            if (Constants.ROOT_CATEGORY_ID.equals(permission.getParentId())) {
                permissionList.add(permission);
            }
        }
        // 为一级权限设置下级权限，getSubPermissions是递归调用的
        for (Permission permission : permissionList) {
            getSubPermissions(permission.getId(), allPermissions, permission);
        }
        return permissionList;
    }

    /**
     * 新增或修改之前相关校验
     *
     * @param permission
     * @return
     */
    private Result checkBeforeEdit(Permission permission) {
        if (null == permission) {
            return Result.error(MsgConstants.ENTITY_CAN_NOT_NULL);
        }
        Long id = permission.getId();
        Long parentId = permission.getParentId();
        String name = permission.getName();
        // 新增
        if (null == id) {
            if (isExistNameByParentId(parentId, name)) {
                return Result.error("该权限下已有名为" + name + "的下级权限");
            }
        } else {
            // 修改
            Permission oldPermission = super.getById(id);
            if (null != oldPermission) {
                // 权限名称被修改
                if (!name.equals(oldPermission.getName())) {
                    if (isExistNameByParentId(parentId, name)) {
                        return Result.error("该权限下已有名为" + name + "的下级权限");
                    }
                }
            } else {
                return Result.error("id为" + parentId + "的权限不存在");
            }
        }
        return null;
    }

    /**
     * 父权限下是否存在同名下级权限
     *
     * @param parentId 父权限id
     * @param name     权限名称
     * @return
     */
    private boolean isExistNameByParentId(Long parentId, String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        params.put("permissionName", name);
        List<Permission> permissionList = query(params);
        return (null != permissionList && permissionList.size() > 0);
    }

    private List<Permission> getSubPermissions(Long prentId, List<Permission> allPermissions, Permission sourcePermission) {
        List<Permission> permissions = new ArrayList<>();
        for (Permission permission : allPermissions) {
            if (prentId.longValue() == permission.getParentId().longValue()) {
                permissions.add(permission);
            }
        }
        // 退出递归条件：该权限下无下级权限
        if (!permissions.isEmpty()) {
            sourcePermission.setSubPermissions(permissions);
            for (Permission permission : permissions) {
                getSubPermissions(permission.getId(), allPermissions, permission);
            }
        }
        return permissions;
    }

}
