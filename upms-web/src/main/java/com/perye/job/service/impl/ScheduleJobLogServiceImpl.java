package com.perye.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perye.common.result.PageUtils;
import com.perye.job.entity.ScheduleJobLogEntity;
import com.perye.job.mapper.ScheduleJobLogMapper;
import com.perye.job.service.IScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLogEntity> implements IScheduleJobLogService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String jobId = (String) params.get("jobId");
		Page<ScheduleJobLogEntity> page = new PageUtils<ScheduleJobLogEntity>(params).getPage();
		QueryWrapper<ScheduleJobLogEntity> queryWrapper = new QueryWrapper<ScheduleJobLogEntity>().like(StringUtils.isNotBlank(jobId), "job_id", jobId).orderByDesc("log_id");
		List<ScheduleJobLogEntity> list = this.page(page, queryWrapper).getRecords();
		page.setRecords(list);
		return new PageUtils<ScheduleJobLogEntity>(page);
	}

}
