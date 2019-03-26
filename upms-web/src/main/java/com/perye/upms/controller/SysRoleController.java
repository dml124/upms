package com.perye.upms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.perye.common.base.BaseController;
import com.perye.common.result.PageUtils;
import com.perye.common.result.ResultMap;
import com.perye.common.result.SelectTreeNode;
import com.perye.common.util.Constant;
import com.perye.common.validator.ValidatorUtils;
import com.perye.upms.entity.SysRole;
import com.perye.upms.service.ISysRoleResourceService;
import com.perye.upms.service.ISysRoleService;
import com.perye.upms.shiro.ShiroKit;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * @Author: Perye
 * @Date: 2019-03-26
 */
@Api(value = "角色管理接口", tags = {" 角色管理接口"})
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    @Reference
    private ISysRoleService sysRoleService;
    @Reference
    private ISysRoleResourceService sysRoleResourceService;

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys/role/list")
    public ResultMap list(@RequestParam Map<String, Object> params){
        //如果不是超级管理员，则只查询自己创建的角色列表
        if(ShiroKit.getUser().getId() != Constant.SUPER_ADMIN){
            params.put("createUserId", ShiroKit.getUser().getId());
        }
        PageUtils page = sysRoleService.selectDataGrid(params);
        return ResultMap.ok().put("page", page);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys/role/select")
    public ResultMap select(){
        Map<String, Object> map = new HashMap<>();
        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if(ShiroKit.getUser().getId() != Constant.SUPER_ADMIN){
            map.put("createUserId", ShiroKit.getUser().getId());
        }
        List<SysRole> list = sysRoleService.selectSysRoleList(map);

        return ResultMap.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys/role/info")
    public ResultMap info(@PathVariable("roleId") Long roleId){
        SysRole role = sysRoleService.getById(roleId);
        //查询角色对应的菜单
        List<Long> resourceIdList=sysRoleResourceService.selectResourceIdListByRoleId(roleId);
        role.setResourceIdList(resourceIdList);
        List<SelectTreeNode> resourceNodeList = sysRoleResourceService.selectResourceNodeListByRoleId(roleId);
        role.setResourceNodeList(resourceNodeList);
        return ResultMap.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @PostMapping("/save")
    @RequiresPermissions("sys/role/save")
    public ResultMap save(@RequestBody SysRole role){
        ValidatorUtils.validateEntity(role);
        role.setCreateTime(new Date());
        role.setCreateUserId(ShiroKit.getUser().getId());
        sysRoleService.saveByVo(role);

        return ResultMap.ok();
    }

    /**
     * 修改角色
     */
    @PostMapping("/update")
    @RequiresPermissions("sys/role/update")
    public ResultMap update(@RequestBody SysRole role){
        ValidatorUtils.validateEntity(role);
        role.setUpdateTime(new Date());
        role.setCreateUserId(ShiroKit.getUser().getId());
        sysRoleService.updateByVo(role);

        return ResultMap.ok();
    }

    /**
     * 删除角色
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys/role/delete")
    public ResultMap delete(@RequestBody Long[] roleIds){
        sysRoleService.deleteBatch(roleIds);
        return ResultMap.ok();
    }

}
