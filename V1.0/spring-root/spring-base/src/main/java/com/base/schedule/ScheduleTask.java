package com.base.schedule;

import java.util.Comparator;
import java.util.List;

import com.taobao.pamirs.schedule.IScheduleTaskDeal;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年7月6日
 * @此类作用 公共方法
 */
public class ScheduleTask implements IScheduleTaskDeal<Object>, IScheduleTaskDealMulti<Object> {
	/**
	 * 获取任务的比较器,只有在NotSleep模式下需要用到
	 */
	@Override
	public Comparator<Object> getComparator() {
		return null;
	}

	/**
	 * 获取执行该调度的时间
	 * @return 是否在调度时间中
	 */
	protected boolean getImortTime() {
		return false;
	}
	
	/**
	 * 根据条件，查询当前调度服务器可处理的任务	
	 * @param taskParameter 任务的自定义参数(任务管理->自定义参数)
	 * @param ownSign 当前环境名称
	 * @param taskQueueNum 当前任务类型的任务队列数量()
	 * @param taskQueueList 当前调度服务器，分配到的可处理队列(任务管理->任务项(","分隔))
	 * @param eachFetchDataNum 每次获取数据的数量(任务管理->每次获取数据量)
	 * @return 返回的list不为null，就表示找到了数据，之后执行execute方法
	 * @throws Exception
	 */
	@Override
	public List<Object> selectTasks(String taskParameter, String ownSign, int taskItemNum, List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
		return null;
	}

	/**
	 * @param task 任务数组
	 * @param ownSign 当前环境名称
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean execute(Object[] tasks, String ownSign) throws Exception {
		return false;
	}
	
}
