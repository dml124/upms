package com.perye.enterprise.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.perye.common.base.BaseController;
import com.perye.common.result.ResultMap;
import com.perye.common.result.TreeNode;
import com.perye.common.util.UUIDGenerator;
import com.perye.enterprise.entity.EnterpriseDepartment;
import com.perye.enterprise.service.IEnterpriseDepartmentService;
import com.perye.enterprise.service.IEnterpriseService;
import com.perye.upms.shiro.ShiroKit;
import com.perye.upms.shiro.ShiroUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 企业部门
 * @Author: Perye
 * @Date: 2019-03-26
 */
@Api(value = "企业部门表接口", tags = {"企业部门表接口"})
@RestController
@RequestMapping("enterprise/enterpriseDepartment")
public class EnterpriseDepartmentController extends BaseController {
    @Reference
    private IEnterpriseDepartmentService enterpriseDepartmentService;

    @Reference
    private IEnterpriseService enterpriseService;


    /**
     * 列表
     */
    @ApiOperation(value = "企业部门表列表", notes = "企业部门表列表")
    @GetMapping("/list")
    @RequiresPermissions("enterprise/enterpriseDepartment/list")
    public Object treeGrid(@RequestParam Map<String, Object> params) {
        ShiroUser shiroUser = ShiroKit.getUser();
        // 不是管理员
        if (shiroUser.getUserType() != 0) {
            params.put("userId", ShiroKit.getUser().getId());
        }
        List<Map<String, Object>> list=  enterpriseDepartmentService.selectTreeGrid(params);
        return ResultMap.ok().put("list",list);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "获取企业部门表", notes = "获取企业部门表")
    @GetMapping("/info/{id}")
    @RequiresPermissions("enterprise/enterpriseDepartment/info")
    public Object info(@PathVariable("id") String id) {
        EnterpriseDepartment enterpriseDepartment = enterpriseDepartmentService.getById(id);
        return ResultMap.ok().put("enterpriseDepartment", enterpriseDepartment);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存企业部门表", notes = "保存企业部门表")
    @PostMapping("/save")
    @RequiresPermissions("enterprise/enterpriseDepartment/save")
    public Object save(@Valid @RequestBody EnterpriseDepartment enterpriseDepartment) {
        try {
            //获取企业ID前缀，生成UUID
            ShiroUser shiroUser = ShiroKit.getUser();
            enterpriseDepartment.setId(UUIDGenerator.getUUID());
            enterpriseDepartment.setCreateTime(new Date());
            enterpriseDepartment.setCreateUser(ShiroKit.getUser().getLoginName());
            enterpriseDepartmentService.save(enterpriseDepartment);
            return ResultMap.ok("添加成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultMap.error("运行异常，请联系管理员");
        }
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改企业部门表", notes = "修改企业部门表")
    @PostMapping("/update")
    @RequiresPermissions("enterprise/enterpriseDepartment/update")
    public Object update(@Valid @RequestBody EnterpriseDepartment enterpriseDepartment) {
        try {
            enterpriseDepartment.setUpdateUser(ShiroKit.getUser().getLoginName());
            enterpriseDepartment.setUpdateTime(new Date());
            enterpriseDepartmentService.updateById(enterpriseDepartment);
            return ResultMap.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultMap.error("运行异常，请联系管理员");
        }

    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除企业部门表", notes = "删除企业部门表")
    @PostMapping("/delete")
    @RequiresPermissions("enterprise/enterpriseDepartment/delete")
    public ResultMap deleteBatchIds(@RequestBody String[] ids) {
        try {
            List<String> idList = new ArrayList<>();
            Collections.addAll(idList, ids);
            if (idList != null && !idList.isEmpty()) {
                enterpriseDepartmentService.removeByIds(Arrays.asList(ids));
                for (String id : idList) {
                    QueryWrapper<EnterpriseDepartment> wrapper = new QueryWrapper<>();
                    wrapper.eq("parent_id", id);
                    enterpriseDepartmentService.remove(wrapper);
                }
                return ResultMap.ok("删除成功！");
            } else {
                return ResultMap.error("删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultMap.error("批量删除失败，请联系管理员");
        }
    }

    /**
     * 选择企业部门（添加、修改）
     *
     * @return
     */
    @ApiOperation(value = "选择企业部门（添加、修改）", notes = "选择企业部门（添加、修改）")
    @GetMapping("/select/{enterpriseId}")
    public ResultMap select(@PathVariable("enterpriseId") String enterpriseId) {
        Map<String, Object> par = new HashMap<>();
        if (StringUtils.isNotBlank(enterpriseId) && !"0".equals(enterpriseId) && !"undefined".equals(enterpriseId)) {
            par.put("enterpriseId", enterpriseId);
        }
        List<TreeNode> tree = enterpriseDepartmentService.selectTree(par);
        tree.add(TreeNode.createParent());
        return ResultMap.ok().put("deptList", tree);
    }

}
