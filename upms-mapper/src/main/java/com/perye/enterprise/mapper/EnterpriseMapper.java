package com.perye.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perye.enterprise.entity.Enterprise;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 企业信息表
 * @Author: Perye
 * @Date: 2019-03-26
 */
public interface EnterpriseMapper extends BaseMapper<Enterprise> {

    /**
     * 多表页面信息查询
     * @param page
     * @param params
     * @return
     */
    IPage<Map<String, Object>> selectEnterprisePage(Page page, @Param("params") Map<String, Object> params);

    /**
     * 多表信息查询
     * @param params
     * @return
     */
    List<Map<String, Object>> selectEnterpriseList(@Param("params") Map<String, Object> params);

}
