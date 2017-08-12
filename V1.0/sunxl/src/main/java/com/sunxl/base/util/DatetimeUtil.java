package com.sunxl.base.util;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.text.ParseException;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月13日
 * @此类作用：
 */
@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class DatetimeUtil {

	public static final String TIME_FORMAT_Y_M_D = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间格式(年-月-日）
	 */
	public static final String DATE_FORMAT_YMD_LONG = "yyyy-MM-dd";

	/**
	 * 时间格式(年月日）
	 */
	public static final String DATE_FORMAT_YMD = "yyyyMMdd";

	/**
	 * 时间格式（年月）
	 */
	public static final String DATE_FORMAT_YM = "yyyyMM";

	/**
	 * 时间格式（年）
	 */
	public static final String DATE_FORMAT_Y = "yyyy";

	public DatetimeUtil() {
	}

	/**
	 * 获取本季度第一天
	 * 
	 * @param endtrandt
	 * @return
	 */
	public static String getTrandtBeg(String endtrandt) {
		String begtrandt = endtrandt.substring(0, 4);
		String MM = endtrandt.substring(4, 6);
		if ("01,02,03".contains(MM)) {
			begtrandt += "0101";
		}
		if ("04,05,06".contains(MM)) {
			begtrandt += "0401";
		}
		if ("07,08,09".contains(MM)) {
			begtrandt += "0701";
		}
		if ("10,12,12".contains(MM)) {
			begtrandt += "1001";
		}
		return begtrandt;
	}

	/**
	 * 取得当前时间，返回Timestamp对象
	 * 
	 * @param
	 * @return Timestamp。
	 */
	public static Timestamp getTimestamp(java.util.Date value) {
		if (value == null)
			return null;
		return new Timestamp(value.getTime());
	}

	/**
	 * 取得当前时间，返回Timestamp对象
	 * 
	 * @param
	 * @return Timestamp。
	 */
	public static Timestamp getNow() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 根据给定格式，返回字符串格式的当前时间
	 * 
	 * @param format
	 *            给定格式，若给定格式为空，则格式默认为"yyyy-MM-dd HH:mm:ss"
	 * @return String。
	 */
	public static String getNow(String format) {
		if (null == format || "".equals(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String date = sdf.format(new Date());
		return date;
	}

	/**
	 * 截取日期部分
	 * <p>
	 * 如sDate为null,则返回当前日期
	 * 
	 * @param sDate
	 * @return
	 */
	public static Date getDate(Date sDate) {
		Date tDate = sDate == null ? new Date() : sDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tDate);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 取得某月天数
	 * 
	 * @param year
	 *            int 年（例2004）
	 * @param month
	 *            int 月（1-12）
	 * @return int 当月天数
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 取给定日期前一天
	 * 
	 * @param dateStr
	 *            String 给定日期(例2004-03-01)
	 * @return String
	 */
	public static String getPreDay(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(dateStr));
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
			return sdf.format(calendar.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * *************************************************************************
	 * *********************
	 */
	/**
	 * Description: 将java.util.Date对象转换为java.sql.Date对象
	 * 
	 * @param _date
	 *            待转化的java.util.Date 对象
	 * @return java.sql.Date对象
	 */
	public static java.sql.Date changeToDBDate(java.util.Date _date) {
		if (_date == null) {
			return null;
		}

		return new java.sql.Date(_date.getTime());
	}

	/**
	 * Description: 将字符串转换为java.sql.Date对象
	 * 
	 * @param _date
	 *            待转化的字符串
	 * @return java.sql.Date对象
	 */
	public static java.sql.Date changeToDBDate(String _date) {
		return changeToDBDate(changeToDate(_date));
	}

	/**
	 * Description: 将字符串转换为java.sql.Time对象
	 * 
	 * @param _date
	 *            待转化的字符串
	 * @return java.sql.Date对象
	 */
	public static java.sql.Time changeToDBTime(String _date) {
		java.sql.Date sdate = changeToDBDate(changeToDate(_date));

		return new java.sql.Time(sdate.getTime());
	}

	/**
	 * Description: 将字符串转换为java.sql.Timestamp对象
	 * 
	 * @param _date
	 *            待转化的字符串
	 * @return java.sql.Date对象
	 */
	public static java.sql.Timestamp changeToDBTimestamp(String _date) {
		java.sql.Date sdate = changeToDBDate(changeToDate(_date));

		return new java.sql.Timestamp(sdate.getTime());
	}

	/**
	 * Description: 将java.sql.Timestamp对象转换为java.util.Date对象
	 * 
	 * @param _date
	 *            待转化的java.sql.Timestamp 对象
	 * @return java.util.Date 对象
	 */
	public static java.util.Date changeToDate(java.sql.Timestamp _date) {
		return (java.util.Date) _date;
	}

	/**
	 * 将java.util.Date对象转换为java.sql.Timestamp对象
	 * 
	 * @param _date
	 *            待转化的java.util.Date 对象
	 * @return java.sql.Timestamp对象
	 */
	public static java.sql.Timestamp changeToTimestamp(java.util.Date _date) {
		if (_date == null)
			return null;
		return new java.sql.Timestamp(_date.getTime());
	}

	/**
	 * 将java.sql.Date对象转换为java.util.Date对象
	 * 
	 * @param _date
	 *            待转化的java.sql.Date对象
	 * @return java.util.Date对象
	 */
	public static java.util.Date changeToDate(java.sql.Date _date) {
		return (java.util.Date) _date;
	}

	/**
	 * 使用中文字符以复杂的形式（"年 月 日 上午 时 分 秒"）格式化时间串
	 * 
	 * @param _date
	 *            日期对象
	 * @return 格式化后的日期
	 */
	public static String complexFormatChineseDate(java.util.Date _date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(_date);

		String timeStr = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
		timeStr = timeStr + calendar.get(Calendar.HOUR_OF_DAY) + "时" + calendar.get(Calendar.MINUTE) + "分" + calendar.get(Calendar.SECOND) + "秒";
		calendar.clear();

		return timeStr;
	}

	/**
	 * 使用格式 <b>_pattern </b>格式化日期输出
	 * 
	 * @param _date
	 *            日期对象
	 * @param _pattern
	 *            日期格式
	 * @return 格式化后的日期
	 */
	public static String formatDate(java.util.Date _date, String _pattern) {
		if (_date == null) {
			return "";
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(_pattern);
		String stringDate = simpleDateFormat.format(_date);

		return stringDate;
	}

	/**
	 * 使用格式 {@link #DATE_FORMAT_YMD}格式化日期输出
	 * 
	 * @param _date
	 *            日期对象
	 * @return 格式化后的日期
	 */
	public static String formatDate(java.util.Date _date) {
		return formatDate(_date, DATE_FORMAT_YMD);
	}

	/**
	 * 将时间串转变为时间对象，输入参数 <b>_dateStr </b>必须遵循格式 {@link #DATE_FORMAT_YMD}
	 * 
	 * @param _dateStr
	 *            时间串
	 * @return 时间对象
	 */
	public static java.util.Date changeToDate(String _dateStr) {
		return changeToDate(_dateStr, DATE_FORMAT_YMD);
	}

	/**
	 * Description:将时间串转变为时间对象
	 * 
	 * @param _dateStr
	 *            时间串
	 * @param _pattern
	 *            时间串使用的模式
	 * @return 时间对象
	 */
	public static java.util.Date changeToDate(String _dateStr, String _pattern) {
		java.util.Date date = null;

		if ((_dateStr == null) || _dateStr.trim().equals("")) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat(_pattern);

		try {
			date = format.parse(_dateStr);
		} catch (java.text.ParseException pe) {
			throw new java.lang.IllegalArgumentException("不能使用模式:[" + _pattern + "]格式化时间串:[" + _dateStr + "]");
		}

		return date;
	}

	/**
	 * 
	 * @param _dateStr格式20161219
	 * @param _patternyyyy
	 *            -MM-dd
	 * @return
	 */
	public static java.sql.Date changeToSqlDate(String _dateStr, String _pattern) {
		java.sql.Date date = null;
		if ((_dateStr == null) || _dateStr.trim().equals("")) {
			return null;
		}
		_dateStr = _dateStr.substring(0, 4) + "-" + _dateStr.substring(4, 6) + "-" + _dateStr.substring(6, 8);
		long time = DatetimeUtil.changeToDate(_dateStr, DatetimeUtil.DATE_FORMAT_YMD_LONG).getTime();
		date = new java.sql.Date(time);
		return date;
	}

	/**
	 * 
	 * @param _dateStr格式20161219
	 * @param _patternyyyy
	 *            -MM-dd
	 * @return
	 */
	public static java.sql.Timestamp changeToSqlTimestamp(String _dateStr, String _pattern) {
		java.sql.Timestamp timeStamp = null;
		if ((_dateStr == null) || _dateStr.trim().equals("")) {
			return null;
		}
		_dateStr = _dateStr.substring(0, 4) + "-" + _dateStr.substring(4, 6) + "-" + _dateStr.substring(6, 8);
		long time = DatetimeUtil.changeToDate(_dateStr, DatetimeUtil.DATE_FORMAT_YMD_LONG).getTime();
		timeStamp = new java.sql.Timestamp(time);
		return timeStamp;
	}

	/**
	 * 获得以参数_fromDate为基数的年龄
	 * 
	 * @param _birthday
	 *            生日
	 * @param _fromDate
	 *            起算时间
	 * @return 年龄（起算年－出生年）
	 */
	public static int getAgeFromBirthday(java.util.Date _birthday, java.util.Date _fromDate) {

		if (_fromDate == null)
			_fromDate = new java.util.Date(System.currentTimeMillis());

		Calendar calendar = getCalendarInstance();
		calendar.setTime(_birthday);

		int birthdayYear = calendar.get(Calendar.YEAR);
		int birthdayMonth = calendar.get(Calendar.MONTH);
		int birthdayDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.clear();
		calendar.setTime(_fromDate);

		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
		int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.clear();

		int age = currentYear - birthdayYear;

		if (!((currentMonth >= birthdayMonth) && (currentDay >= birthdayDay))) {
			age--;
		}

		return age;
	}

	/**
	 * 获得当前年龄
	 * 
	 * @param _birthday
	 *            生日
	 * @return 年龄（起算年－出生年）
	 */
	public static int getAgeFromBirthday(java.util.Date _birthday) {
		return getAgeFromBirthday(_birthday, new java.util.Date(System.currentTimeMillis()));
	}

	/**
	 * 获得当前年龄
	 * 
	 * @param _birthday
	 *            生日
	 * @return 年龄（起算年－出生年）
	 */
	public static int getAgeFromBirthday(java.sql.Timestamp _birthday) {
		return getAgeFromBirthday(new java.util.Date(_birthday.getTime()), new java.util.Date(System.currentTimeMillis()));
	}

	/**
	 * 获得系统时间的当前月的最后一天，包括年月日
	 * 
	 * @return 返回值格式为：20030131
	 */
	public static String getCurMonthFirstDay() {
		return getCurMonthFirstDay(new java.util.Date(System.currentTimeMillis()));
	}

	/**
	 * 获得日期的当前月的最后一天，包括年月日
	 * 
	 * @param date
	 *            参考时间
	 * @return 返回值格式为：20030131
	 */
	public static String getCurMonthFirstDay(java.util.Date date) {
		return formatDate(date, DATE_FORMAT_YM) + "01";
	}

	/**
	 * 获得系统时间当前月的最后一天，包括年月日
	 * 
	 * @return 返回值格式为：20030131
	 */
	public static String getCurMonthLastDay() {
		return getCurMonthLastDay(new java.util.Date(System.currentTimeMillis()));
	}

	/**
	 * 获得日期的当前月的最后一天，包括年月日
	 * 
	 * @param date
	 *            参考时间
	 * @return 返回值格式为：20030131
	 */
	public static String getCurMonthLastDay(java.util.Date date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);

		java.util.Date currentMonthFirstDayDate = changeToDate(formatDate(calendar.getTime(), DATE_FORMAT_YM) + "01", DATE_FORMAT_YMD);
		calendar.clear();
		calendar.setTime(currentMonthFirstDayDate);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		return formatDate(calendar.getTime(), DATE_FORMAT_YMD);
	}

	/**
	 * 获得日期的天，以月为基
	 * 
	 * @param _date
	 *            日期对象
	 * @return 日期的天
	 */
	public static int getDay(java.util.Date _date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(_date);

		int day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.clear();

		return day;
	}

	/**
	 * 获得两个Date之间的天数
	 * 
	 * @param _startDate
	 *            开始时间
	 * @param _endDate
	 *            结束时间
	 * @return 两个Date间的天数
	 */
	public static int getDayAmount(java.sql.Date _startDate, java.sql.Date _endDate) {
		int nDayAmount = 0;
		Calendar cldStart = Calendar.getInstance();
		Calendar cldEnd = Calendar.getInstance();

		cldStart.setTime(_startDate);
		cldEnd.setTime(_endDate);

		int nStart = cldStart.get(Calendar.DAY_OF_YEAR);
		int nEnd = cldEnd.get(Calendar.DAY_OF_YEAR);

		if ((nEnd - nStart) > 0) {
			nDayAmount = nEnd - nStart;
		} else {
			nDayAmount = 365 - (nEnd - nStart);
		}

		return nDayAmount;
	}

	/**
	 * 计算两个日期间相隔的天数
	 * 
	 * @param _startDate
	 *            起始日期
	 * @param _endDate
	 *            终止日期
	 * @return 相隔天数, 如果结果为正表示 <b>_endDate </b>在 <b>_startDate </b>之后；如果结果为负表示
	 *         <b>_endDate </b>在 <b>_startDate </b>之前；如果结果为0表示 <b>_endDate </b>和
	 *         <b>_startDate </b>是同一天。
	 */
	public static int getDayCount(java.util.Date _startDate, java.util.Date _endDate) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(_startDate);

		int startDay = calendar.get(Calendar.DAY_OF_YEAR);
		int startYear = calendar.get(Calendar.YEAR);
		calendar.clear();
		calendar.setTime(_endDate);

		int endDay = calendar.get(Calendar.DAY_OF_YEAR);
		int endYear = calendar.get(Calendar.YEAR);
		calendar.clear();

		return ((endYear - startYear) * 365) + (endDay - startDay);
	}

	/**
	 * 获得日期的小时
	 * 
	 * @param _date
	 *            日期对象
	 * @return 日期的小时
	 */
	public static int getHours(java.util.Date _date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(_date);

		int value = calendar.get(Calendar.HOUR);
		calendar.clear();

		return value;
	}

	/**
	 * 获得系统时间的上个月的第一天，包括年月日
	 * 
	 * @return 返回值格式为：20030101
	 */
	public static String getLastMonthFirstDay() {
		return getLastMonthFirstDay(new java.util.Date(System.currentTimeMillis()));
	}

	/**
	 * 获得上个月的第一天，包括年月日
	 * 
	 * @param date
	 *            参考时间
	 * @return 返回值格式为：20030101
	 */
	public static String getLastMonthFirstDay(java.util.Date date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);

		return formatDate(calendar.getTime(), DATE_FORMAT_YM) + "01";
	}

	/**
	 * 获得系统时间的上个月的最后一天，包括年月日
	 * 
	 * @return 返回值格式为：20030131
	 */
	public static String getLastMonthLastDay() {
		return getLastMonthLastDay(new java.util.Date(System.currentTimeMillis()));
	}

	/**
	 * 获得日期的上个月的最后一天，包括年月日
	 * 
	 * @param date
	 *            参考时间
	 * @return 返回值格式为：20030131
	 */
	public static String getLastMonthLastDay(java.util.Date date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);

		java.util.Date currentMonthFirstDayDate = changeToDate(formatDate(calendar.getTime(), DATE_FORMAT_YM) + "01", DATE_FORMAT_YMD);
		calendar.clear();
		calendar.setTime(currentMonthFirstDayDate);
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		return formatDate(calendar.getTime(), DATE_FORMAT_YMD);
	}

	/**
	 * 获得日期的分钟
	 * 
	 * @param _date
	 *            日期对象
	 * @return 日期的分钟
	 */
	public static int getMinutes(java.util.Date _date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(_date);

		int value = calendar.get(Calendar.MINUTE);
		calendar.clear();

		return value;
	}

	/**
	 * 获得日期的月
	 * 
	 * @param _date
	 *            日期对象
	 * @return 日期的月
	 */
	public static int getMonth(java.util.Date _date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(_date);

		// 以0开始
		int month = calendar.get(Calendar.MONTH);
		calendar.clear();

		return (month + 1);
	}

	/**
	 * 获得两个Date间的月数, 天数超过14天算1个月
	 * 
	 * @param _startDate
	 *            开始时间
	 * @param _endDate
	 *            结束时间
	 * @return 两个Date间的月数
	 */
	public static int getMonthAmount(java.sql.Date _startDate, java.sql.Date _endDate) {
		int nYear = 0;
		int nMonth = 0;
		int nDay = 0;
		int nMonthAmount = 0;
		Calendar cldStart = Calendar.getInstance();
		Calendar cldEnd = Calendar.getInstance();

		cldStart.setTime(_startDate);
		cldEnd.setTime(_endDate);

		nYear = cldEnd.get(Calendar.YEAR) - cldStart.get(Calendar.YEAR);
		nMonth = cldEnd.get(Calendar.MONTH) - cldStart.get(Calendar.MONTH);
		nDay = cldEnd.get(Calendar.DATE) - cldStart.get(Calendar.DATE);

		if (nDay > 14) {
			nMonthAmount = (nYear * 12) + nMonth + 1;
		} else {
			nMonthAmount = (nYear * 12) + nMonth;
		}

		return nMonthAmount;
	}

	/**
	 * 计算两个日期间相隔的月数, 每隔一月，其相隔天数必>30
	 * 
	 * @param _startDate
	 *            起始日期
	 * @param _endDate
	 *            终止日期
	 * @return 相隔月数, 如果结果为正表示 <b>_endDate </b>在 <b>_startDate </b>之后；如果结果为负表示
	 *         <b>_endDate </b>在 <b>_startDate </b>之前；如果结果为0表示 <b>_endDate </b>和
	 *         <b>_startDate </b>是同一天。
	 */
	public static int getMonthCount(java.util.Date _startDate, java.util.Date _endDate) {
		java.util.Date startDate = _startDate;
		java.util.Date endDate = _endDate;
		boolean afterFlag = false;

		if (_startDate.after(_endDate)) {
			startDate = _endDate;
			endDate = _startDate;
			afterFlag = true;
		}

		Calendar calendar = getCalendarInstance();
		calendar.setTime(startDate);

		int startDay = calendar.get(Calendar.DAY_OF_MONTH);
		int startMonth = calendar.get(Calendar.MONTH);
		int startYear = calendar.get(Calendar.YEAR);
		int countDayOfStartMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.clear();
		calendar.setTime(endDate);

		int endDay = calendar.get(Calendar.DAY_OF_MONTH);
		int endMonth = calendar.get(Calendar.MONTH);
		int endYear = calendar.get(Calendar.YEAR);
		calendar.clear();

		int result = ((endYear - startYear) * 12) + (endMonth - (startMonth + 1)) + (int) ((endDay + (countDayOfStartMonth - startDay)) / countDayOfStartMonth);

		if (afterFlag) {
			return -result;
		} else {
			return result;
		}
	}

	/**
	 * 获得日期的小秒
	 * 
	 * @param _date
	 *            日期对象
	 * @return 日期的秒
	 */
	public static int getSeconds(java.util.Date _date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(_date);

		int value = calendar.get(Calendar.SECOND);
		calendar.clear();

		return value;
	}

	/**
	 * 获得日期的年
	 * 
	 * @param _date
	 *            日期对象
	 * @return 日期的年
	 */
	public static int getYear(java.util.Date _date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(_date);

		int year = calendar.get(Calendar.YEAR);
		calendar.clear();

		return year;
	}

	/**
	 * 使用中文字符以简单的形式（"年 月 日"）格式化时间串
	 * 
	 * @param _date
	 *            日期对象
	 * @return 格式化后的日期
	 */
	public static String simpleFormatChineseDate(java.util.Date _date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(_date);

		String timeStr = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
		calendar.clear();

		return timeStr;
	}

	/**
	 * <p>
	 * Title :雅普兰Web开发框架
	 * </p>
	 * <p>
	 * Description: 得到当前时间（Timestamp）
	 * </p>
	 */
	public static Timestamp getCurTime() {
		java.sql.Timestamp sdate = new java.sql.Timestamp(System.currentTimeMillis());

		return sdate;
	}

	/**
	 * Description: 在_dt的基础上加上_day天
	 * 
	 * @param _dt
	 *            日期
	 * @param _day
	 *            天数
	 * @return
	 */
	public static java.util.Date getDateByDay(java.sql.Date _dt, int _day) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(_dt);
		calendar.add(Calendar.DAY_OF_MONTH, _day);

		return calendar.getTime();
	}

	/**
	 * 取期末日期
	 * 
	 * @param _dt
	 * @param _period
	 *            <ul>
	 *            <li>M 月末
	 *            <li>Q 季末
	 *            <li>Y 年末
	 *            <li>H 半年末
	 *            <li>m 上月末
	 *            <li>q 上季末
	 *            <li>y 上年末
	 *            <li>h 上半年末
	 *            </ul>
	 * @return
	 */
	public static java.util.Date getPeriodEnd(java.util.Date _dt, char _period) {
		Calendar cal = getCalendarInstance();
		cal.setTime(_dt);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);// 1月=0,12月=11
		int week = cal.get(Calendar.DAY_OF_WEEK);
		switch (_period) {
		case 'M':
			cal.add(Calendar.MONTH, 1);// to next month
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
			break;
		case 'Q':
			cal.add(Calendar.MONTH, 3 - month % 3);// to next quarter
			// first month
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
			break;
		case 'Y':
			cal.add(Calendar.MONTH, 12 - month);// to next year first month
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
			break;
		case 'H':
			cal.add(Calendar.MONTH, 6 - (month) % 6);// to next halfyear
			// first month
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
			break;
		case 'm':
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
			break;
		case 'q':
			cal.add(Calendar.MONTH, 0 - (month) % 3);// to this quarter
			// first month
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
			break;
		case 'y':
			cal.add(Calendar.MONTH, 0 - month);// to this year first month
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
			break;
		case 'h':
			cal.add(Calendar.MONTH, 0 - (month) % 6);// to this halfyear
			// first month
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);// to last month end
			break;
		default:
			break;
		}
		return cal.getTime();
	}

	/**
	 * 获取指定类型日期
	 * 
	 * @param date
	 *            日期(字符型YYYYMMDD格式)
	 * @param period
	 *            期间类型 ''D'' 日 ''M'' 月 ''Q'' 季 ''H' 半年 ''Y'' 年 “MB”月初 “ME”月末
	 *            “QB”季初 “QE”季末“HB”半年初 “HE”半年末 “YB”月初 “YE”年末
	 * @param offset
	 *            负数为前，正数为后
	 * @return 指定类型日期
	 */
	public static String getSpeldate(String date, String period, int offset) {
		java.util.Date dt = changeToDate(date);
		Calendar cal = getCalendarInstance();
		cal.setTime(dt);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);// 1月=0,12月=11
		String upperiod = period.toUpperCase();
		// if("MB".equals(upperiod)){
		if ("MB".equalsIgnoreCase(upperiod)) {
			cal.add(Calendar.DAY_OF_MONTH, 1 - day);
		} else if ("ME".equalsIgnoreCase(upperiod)) {
			cal.add(Calendar.MONTH, 1);// to next month
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);
		} else if ("QB".equalsIgnoreCase(upperiod)) {
			cal.add(Calendar.MONTH, 0 - month % 3);
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 1 - day);
		} else if ("QE".equalsIgnoreCase(upperiod)) {
			cal.add(Calendar.MONTH, 3 - month % 3);
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);
		} else if ("HB".equalsIgnoreCase(upperiod)) {
			cal.add(Calendar.MONTH, 0 - (month) % 6);
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 1 - day);
		} else if ("HE".equalsIgnoreCase(upperiod)) {
			cal.add(Calendar.MONTH, 6 - (month) % 6);
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);
		} else if ("YB".equalsIgnoreCase(upperiod)) {
			cal.add(Calendar.MONTH, 0 - month);
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 1 - day);
		} else if ("YE".equalsIgnoreCase(upperiod)) {
			cal.add(Calendar.MONTH, 12 - month);
			day = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.DAY_OF_MONTH, 0 - day);
		}
		// 非当期
		if (offset != 0) {
			if ("M".equalsIgnoreCase(upperiod) || "MB".equalsIgnoreCase(upperiod) || "ME".equalsIgnoreCase(upperiod)) {
				cal.add(Calendar.MONTH, offset);
			} else if ("Q".equalsIgnoreCase(upperiod) || "QB".equalsIgnoreCase(upperiod) || "QE".equalsIgnoreCase(upperiod)) {
				cal.add(Calendar.MONTH, offset * 3);
			} else if ("H".equalsIgnoreCase(upperiod) || "HB".equalsIgnoreCase(upperiod) || "HE".equalsIgnoreCase(upperiod)) {
				cal.add(Calendar.MONTH, offset * 6);
			} else if ("Y".equalsIgnoreCase(upperiod) || "YB".equalsIgnoreCase(upperiod) || "YE".equalsIgnoreCase(upperiod)) {
				cal.add(Calendar.MONTH, offset * 12);
			}
		}
		return formatDate(cal.getTime(), DATE_FORMAT_YMD);
	}

	/**
	 * Description: 根据Timestamp得到Time
	 * 
	 * @param _timesmp
	 * @return
	 */
	public static java.sql.Time getTimeByTimestamp(Timestamp _timesmp) {
		Calendar cal = getCalendarInstance();
		cal.setTimeInMillis(_timesmp.getTime());
		String str = String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(cal.get(Calendar.MINUTE)) + ":" + String.valueOf(cal.get(Calendar.SECOND));

		return java.sql.Time.valueOf(str);
	}

	/**
	 * Description: 得到当前时间的前/后@offset天的时间
	 * 
	 * @param offset
	 *            负数为前，正数为后
	 * @param splitdate
	 *            日期分割符
	 * @param splittime
	 *            时间分割符
	 * @return
	 */
	public static String getPriorDay(int offset, String splitdate, String splittime) {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar theday = Calendar.getInstance();
		theday.add(Calendar.DATE, offset);

		df.applyPattern("yyyy" + splitdate + "MM" + splitdate + "dd" + " " + "HH" + splittime + "mm" + splittime + "ss");
		return df.format(theday.getTime());
	}

	public static Calendar getCalendarInstance() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.SIMPLIFIED_CHINESE);
		return cal;

	}

	/**
	 * 按精度比较日期. <br>
	 * 
	 * @param date1
	 * @param date2
	 * @param precision
	 *            精度=整数： <br>
	 *            Calendar.YEAR <br>
	 *            Calendar.MONTH <br>
	 *            Calendar.DAY_OF_MONTH <br>
	 *            Calendar.MINUTE <br>
	 *            Calendar.SECOND <br>
	 *            Calendar.MILLISECOND
	 * @return the value 0 if the argument Date is equal to this Date; a value
	 *         less than 0 if this Date is before the Date argument; and a value
	 *         greater than 0 if this Date is after the Date argument
	 */
	public static int compareDate(final Date date1, final Date date2, int precision) {
		Calendar c = Calendar.getInstance();

		List fields = new ArrayList();
		fields.add(new Integer(Calendar.YEAR));
		fields.add(new Integer(Calendar.MONTH));
		fields.add(new Integer(Calendar.DAY_OF_MONTH));
		fields.add(new Integer(Calendar.MINUTE));
		fields.add(new Integer(Calendar.SECOND));
		fields.add(new Integer(Calendar.MILLISECOND));

		Date d1 = date1;
		Date d2 = date2;
		if (fields.contains(new Integer(precision))) {
			c.setTime(date1);
			for (int i = 0; i < fields.size(); i++) {
				int field = ((Integer) fields.get(i)).intValue();
				if (field > precision)
					c.set(field, 0);
			}
			d1 = c.getTime();
			c.setTime(date2);
			for (int i = 0; i < fields.size(); i++) {
				int field = ((Integer) fields.get(i)).intValue();
				if (field > precision)
					c.set(field, 0);
			}
			d2 = c.getTime();
		}
		return d1.compareTo(d2);
	}

	public static Calendar convertTimeZone(Calendar cal, String zone) {
		Calendar ret = Calendar.getInstance();
		TimeZone toZone = TimeZone.getTimeZone(zone);
		long curmillis = cal.getTimeInMillis();
		ret.setTimeInMillis(curmillis + toZone.getRawOffset() - cal.getTimeZone().getRawOffset());

		return ret;
	}

	/**
	 * 日期字符串，返回一个Calendar的日期对象
	 * 
	 * @param datetime
	 *            日期字符串
	 * @param pattern
	 *            格式（yyyy-MM-dd 、yyyyMMdd）
	 * @return
	 * @throws Exception
	 */
	private static Calendar getDateCalendar(String datetime, String pattern) throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			Date date = df.parse(datetime);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			return cd;
		} catch (ParseException pe) {
			throw new Exception(datetime + "格式不对，必须是yyyy-MM-dd 、 yyyyMMdd 格式");
		} catch (Exception ex) {

			throw ex;
		}
	}

	/**
	 * 默认时间字符串格式为yyyyMMdd
	 * 
	 * @param datetime
	 * @return
	 * @throws Exception
	 */
	private static Calendar getDateCalendar(String datetime) throws Exception {
		return getDateCalendar(datetime, "yyyyMMdd");
	}

	/**
	 * 获取当前日期的上一天
	 * 
	 * @param dateOfChar
	 *            字符串格式
	 * @return
	 * @throws Exception
	 */
	public static String getPrirDay(String dateOfChar) throws Exception {
		Calendar calendar = getDateCalendar(dateOfChar);
		calendar.add(Calendar.DATE, -1);// 得到下一天
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}

	/**
	 * 获取当前日期的下一天
	 * 
	 * @param dateOfChar
	 *            字符串格式
	 * @return
	 * @throws Exception
	 */
	public static String getnextDay(String dateOfChar) throws Exception {
		Calendar calendar = getDateCalendar(dateOfChar);
		calendar.add(Calendar.DATE, 1);// 得到下一天
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}

	/**
	 * 获取当前日期的下一年
	 * 
	 * @param dateOfChar
	 *            字符串格式
	 * @return
	 * @throws Exception
	 */
	public static String getnextyear(String dateOfChar) throws Exception {
		Calendar calendar = getDateCalendar(dateOfChar);
		calendar.add(Calendar.YEAR, 1);// 得到明年
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}

	/**
	 * 获取当前日期的前一年
	 * 
	 * @param dateOfChar
	 *            字符串格式
	 * @return
	 * @throws Exception
	 */
	public static String getlastyear(String dateOfChar) throws Exception {
		Calendar calendar = getDateCalendar(dateOfChar);
		calendar.add(Calendar.YEAR, -1);// 得到去年
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}

	/**
	 * 是否当天是期末
	 * 
	 * @param datestr
	 *            需检查的日期
	 * @param format
	 *            可为空 默认日期格式:yyyyMMdd
	 * @param edtype
	 *            月末M 年末Y
	 * @return
	 */
	public static boolean isEndOfDay(String datestr, String format, String edtype) throws Exception {
		boolean result = false;
		ArrayList<String> formatlist = new ArrayList<String>();
		formatlist.add(DATE_FORMAT_YMD_LONG);
		formatlist.add(DATE_FORMAT_YMD);

		// 检查日期格式是否合法
		if ("".equals(format) || format == null) {
			format = DATE_FORMAT_YMD;
		} else if (!formatlist.contains(format)) {
			// System.out.println("date format is not corect");
			return false;
		}

		// 是否月末
		if ("M".equals(edtype)) {
			try {
				String nextdate_month = getnextDay(datestr).substring(4, 6);
				String todate_month = datestr.substring(4, 6);
				if (!todate_month.equals(nextdate_month)) {
					result = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 是否年末
			if ("1231".equals(getNow(format).substring(4, 8))) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 调用测试用例
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String dt = "20110101";
			// System.out.println(getPrirDay(dt));
			dt = "20080128";
			// System.out.println(getnextyear(dt));
			// System.out.println(isEndOfDay(dt,"","Y"));
			System.out.println(changeToSqlDate("20080128", "yyyyMMdd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}