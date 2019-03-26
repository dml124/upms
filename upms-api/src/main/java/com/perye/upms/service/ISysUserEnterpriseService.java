package com.perye.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.perye.common.result.SelectTreeNode;
import com.perye.upms.entity.SysUserEnterprise;

import java.util.List;

/**
 * 监管用户与企业关联表
 *
 * @Author: Perye
 * @Date: 2019-03-26
 */
public interface ISysUserEnterpriseService extends IService<SysUserEnterprise> {

    List<String> selectEnterpriseIdByUserId(Long userId);

    /**
     * 保存或修改用户所监管的企业关系
     * @param userId
     * @param enterpriseIdList
     */
    void saveOrUpdateUserEnterprise(Long userId, List<String> enterpriseIdList);

    /**
     * 根据用户批量删除
     * @param userIds
     */
    void deleteBatchByUserIds(Long[] userIds);

    /**
     * 根据企业批量删除
     * @param enterpriseIds
     */
    void deleteBatchByEnterpriseIds(String[] enterpriseIds);

    /**
     * 根据用户ID查找监管的企业node
     *
     * @param userId
     * @return
     */
    List<SelectTreeNode> selectEnterpriseNodeListByUserId(Long userId);
}


