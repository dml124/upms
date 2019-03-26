package com.perye.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.perye.common.result.PageUtils;
import com.perye.upms.entity.SysLog;

import java.util.List;
import java.util.Map;

/**
 * 系统日志表
 *
 * @Author: Perye
 * @Date: 2019-03-26
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 多表页面信息查询
     *
     * @param params
     * @return
     */
    PageUtils selectDataGrid(Map<String, Object> params);

    /**
     * 多表信息查询
     *
     * @param par
     * @return
     */
    List<SysLog> selectSysLogList(Map<String, Object> par);


}

