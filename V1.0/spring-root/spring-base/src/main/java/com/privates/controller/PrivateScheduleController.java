package com.privates.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.interceptor.InitDataInterceptor;
import com.base.interfaces.MethodLog;
import com.base.one.entity.AdminUser;
import com.base.util.FileUtil;
import com.base.util.JSON;
import com.base.util.SunXlProperties;
import com.base.util.WebKeys;
import com.taobao.pamirs.schedule.ConsoleManager;
import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月20日 @此类作用：数据调度类
 */
@Controller
@RequestMapping(value = "/private/schedule")
public class PrivateScheduleController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateScheduleController.class);

	@RequestMapping(value = "/ViewScheduleJson", method = RequestMethod.GET)
	@MethodLog(remark = "进入调度开启页面")
	public String goSchedule(HttpServletRequest request) {
		AdminUser adminUser = (AdminUser) request.getSession().getAttribute(WebKeys.ADMINUSER_KEY);
		if (adminUser.getRole().getName().equals("管理员") || adminUser.getRole().getName().equals("开发者"))
			request.setAttribute("isManager", true);
		return "private.schedule.view";
	}

	@RequestMapping(value = "/path", method = RequestMethod.GET)
	@MethodLog(remark = "进入调度开启页面,href从一个jsp跳转另外一个jsp回报拦截器错误，获取返回的jsp路径就可以了")
	public String goScheduleGetIndex(HttpServletRequest request) {
		AdminUser adminUser = (AdminUser) request.getSession().getAttribute(WebKeys.ADMINUSER_KEY);
		if (adminUser.getRole().getName().equals("管理员") || adminUser.getRole().getName().equals("开发者"))
			request.setAttribute("isManager", true);
		try {
			return request.getParameter("path");
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】" + e.getMessage());
			return "private.schedule.config";
		}
	}

	@RequestMapping(value = "/path", method = RequestMethod.POST)
	@MethodLog(remark = "Get的url长度有限制，兼容所有浏览器，长度最大2083个字符，导入太大，需要用")
	@ResponseBody
	public JSON goSchedulePostIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSON json = new JSON();
		String info = "";
		try {
			AdminUser adminUser = (AdminUser) request.getSession().getAttribute(WebKeys.ADMINUSER_KEY);
			if (adminUser.getRole().getName().equals("管理员") || adminUser.getRole().getName().equals("开发者"))
				request.setAttribute("isManager", true);
			if (request.getParameter("type") == null) {
				StringWriter writer = new StringWriter();
				boolean isUpdate = false;
				String configContent = null;
				try {
					configContent = request.getParameter("configContent");
					StringReader strReader = new StringReader(configContent);
					BufferedReader bufReader = new BufferedReader(strReader);
					String line = null;
					boolean isUploadConfig = false;
					isUpdate = Boolean.valueOf(request.getParameter("isUpdate"));
					while ((line = bufReader.readLine()) != null) {
						isUploadConfig = true;
						if (line.contains("strategy") || line.contains("baseTaskType")) {
							ConsoleManager.getScheduleStrategyManager().importConfig(line, writer, isUpdate);
						} else {
							writer.write("<h3><font color=\"red\">非法配置信息：\n\t\t</font>" + line + "</h3>");
						}
					}
					if (!isUploadConfig) {
						writer.append("<h3><font color=\"red\">错误信息：\n\t</font>没有选择导入的配置文件</h3>");
					}
				} catch (Exception e) {
					StringWriter strWriter = new StringWriter();
					PrintWriter printWriter = new PrintWriter(strWriter);
					e.printStackTrace(printWriter);
					logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】" + e.getMessage());
					writer.append("<h3><font color=\"red\">错误信息堆栈：\n\t\t</font>" + e.getMessage() + "\n" + strWriter.toString() + "</h3>");
				}
				request.setAttribute("configContentString", configContent);
				request.setAttribute("isUpdateString", isUpdate);
				request.setAttribute("writerString", writer);
			} else {
				StringWriter confWriter = new StringWriter();
				StringWriter errWriter = new StringWriter();
				String rootPath = ConsoleManager.getScheduleStrategyManager().getRootPath();
				String type = (String) request.getParameter("type");
				StringWriter tmpWriter = new StringWriter();
				try {
					StringBuffer buffer = null;
					if (rootPath != null && rootPath.length() > 0) {
						buffer = ConsoleManager.getScheduleStrategyManager().exportConfig(rootPath, confWriter);
					} else {
						tmpWriter.write("没有设置导出配置信息的路径");
					}
					// 导出文件
					if (type != null && type.equals("1")) {
						// 导出进行保存
						if (buffer != null) {
							response.setContentType("text/plain;charset=UTF-8");
							response.setHeader("Content-disposition", "attachment; filename=config.txt");
							/* PrintWriter out = response.getWriter(); */
							ServletOutputStream out = response.getOutputStream();
							out.print(buffer.toString());
							out.flush();
							out.close();
						}
					}
					if (tmpWriter != null && tmpWriter.toString().length() > 0) {
						errWriter.write("<font color=\"red\">错误信息：</font>\n\t");
						errWriter.write(tmpWriter.toString());
					}
				} catch (Exception e) {
					if (tmpWriter != null && tmpWriter.toString().length() > 0) {
						errWriter.write("<font color=\"red\">错误信息：</font>\n\t");
						errWriter.write(tmpWriter.toString());
					}
					StringWriter strWriter = new StringWriter();
					PrintWriter printWriter = new PrintWriter(strWriter);
					e.printStackTrace(printWriter);
					errWriter.write("<font color=\"red\">错误的堆栈信息:</font>\n\t" + e.getMessage() + "\n" + strWriter);
				}
				request.setAttribute("rootPathString", rootPath);
				request.setAttribute("errWriterString", errWriter);
				request.setAttribute("confWriterString", confWriter);
			}
			info = "成功";
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			info = e.getMessage();
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】" + info);
		}
		json.setMsg(info);
		return json;
	}

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@MethodLog(remark = "向zookeeper发起连接，注册当前机器，请求任务队列，最后根据调度配置执行job")
	@ResponseBody
	public JSON goToStartShedule() {
		JSON json = new JSON();
		String info = "";
		try {
			String webPath = FileUtil.getHomePath() + "META-INF" + File.separator;
			ApplicationContext ctx = new FileSystemXmlApplicationContext(webPath + "sunxl-scheduleContext.xml");
			TBScheduleManagerFactory scheduleManagerFactory = new TBScheduleManagerFactory();
			SunXlProperties sunXlProperties = (SunXlProperties) (InitDataInterceptor.map.get("META-INF/system.properties"));
			Properties p = new Properties();
			p.put("zkConnectString", sunXlProperties.getProperty("system.shedule.zkConnectString"));
			p.put("rootPath", sunXlProperties.getProperty("system.shedule.rootPath"));
			p.put("zkSessionTimeout", sunXlProperties.getProperty("system.shedule.zkSessionTimeout"));
			p.put("userName", sunXlProperties.getProperty("system.shedule.userName"));
			p.put("password", sunXlProperties.getProperty("system.shedule.password"));
			p.put("isCheckParentPath", sunXlProperties.getProperty("system.shedule.isCheckParentPath"));
			scheduleManagerFactory.setApplicationContext(ctx);
			scheduleManagerFactory.init(p);
			info = "注入成功";
			json.setSuccess(true);
		} catch (Exception e) {
			info = e.getMessage();
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】" + info);
			json.setSuccess(false);
		}
		json.setMsg(info);
		return json;
	}
}
