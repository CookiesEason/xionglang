package com.sunxl.base.schedule;

import java.util.List;

import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月20日 
 * @此类作用：数据处理
 */
public class DataManagerExecuteTask extends ScheduleTask implements IScheduleTaskDealMulti<Object> {

	@Override
	public List<Object> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
		return null;
	}
	
	@Override
	public boolean execute(Object[] tasks, String ownSign) throws Exception {
		return false;
	}
	

}
