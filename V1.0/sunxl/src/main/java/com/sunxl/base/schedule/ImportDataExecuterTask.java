package com.sunxl.base.schedule;

import java.util.List;

import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月20日 
 * @此类作用：导数
 */
public class ImportDataExecuterTask extends ScheduleTask implements IScheduleTaskDealSingle<Object> {

	@Override
	public List<Object> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
		return null;
	}

	@Override
	public boolean execute(Object task, String ownSign) throws Exception {
		return false;
	}
}
