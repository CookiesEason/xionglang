package com.base.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月15日
 * @此类作用：springMVC的注解定时器
 */
@Component
public class BaseTask {
	private static final Logger logger = LoggerFactory.getLogger(BaseTask.class);
	/*@Autowired
	private AdminUserService adminUserService;
*/
	/**
	 * 秒（0~59） 分钟（0~59） 小时（0~23） 天（月）（0~31，但是你需要考虑你月的天数） 月（0~11） 天（星期）（1~7 1=SUN
	 * 或 SUN，MON，TUE，WED，THU，FRI，SAT） 年份（1970－2099）
	 */
	@Scheduled(cron = "0/5 * * * * ? ")
	public void taskCycle() {
		try {

		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
		}
	}
}
