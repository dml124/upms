package com.perye.upms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perye.upms.entity.SysUserRole;
import com.perye.upms.mapper.SysUserRoleMapper;
import com.perye.upms.service.ISysUserRoleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户角色表
 *
 * @Author: Perye
 * @Date: 2019-03-26
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public List<Long> selectRoleIdListByUserId(Long userId) {
        return this.baseMapper.selectRoleIdListByUserId(userId);
    }

    @Override
    public void saveOrUpdateUserRole(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        Map<String,Object> params=new HashMap<>();
        params.put("user_id",userId);
        this.removeByMap(params);
        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }
        //保存用户与角色关系
        List<SysUserRole> list = new ArrayList<>(roleIdList.size());
        for(Long roleId : roleIdList){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            list.add(sysUserRole);
        }
        this.saveBatch(list);
    }

    @Override
    public void deleteBatchByUserIds(Long[] userIds) {
        this.baseMapper.deleteBatchByUserIds(userIds);
    }

    @Override
    public void deleteBatchByRoleIds(Long[] roleIds) {
        this.baseMapper.deleteBatchByRoleIds(roleIds);
    }
}
