package com.sunxl.base.util;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：
 */
public class WebKeys {

	public static final String TOKEN = "my.powerful.token";
	public final static String MOBILE_PHONE_CHECK = "MOBILE_PHONE_CHECK";
	public final static String MOBILEPHONE_REGEXP = "^[1][3-8]+\\d{9}$";
	public final static String CHINESE_NAME_REGEXP = "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*";
	public final static String REAL_NAME_REGEXP = CHINESE_NAME_REGEXP;
	public final static String USER_KEY = "com.sunxl.base.entity.User";
	public final static String ADMINUSER_KEY = "com.sunxl.base.entity.AdminUser";
	public final static int CODE_TIME=60*1000;//验证码默认过期时间60秒
	/**
	 * 分页变量名
	 */

	public static final int DEFAULT_PAGE_SIZE = 15;
	public static final int QUERY_ALL = -1;

	/**
	 * 时间格式化
	 */
	public static final String TIME_FORMAT_DAY = "yyyy-MM-dd";

	public static final String TIME_FORMAT_TIME = "yyyy-MM-dd hh:mm:ss";

}
