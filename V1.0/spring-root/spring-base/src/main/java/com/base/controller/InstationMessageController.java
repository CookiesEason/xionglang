/**
 * 
 */
package com.base.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.one.entity.AdminUser;
import com.base.one.entity.InstationMessage;
import com.base.one.entity.InstationToPerson;
import com.base.one.entity.User;
import com.base.service.InstationMessageService;
import com.base.service.InstationToPersonService;
import com.base.util.JSON;
import com.base.util.Page;
import com.base.util.Sql;
import com.base.util.StringUtil;
import com.base.util.WebKeys;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月17日
 * @此类的作用：站内信
 */
@Controller
@RequestMapping(value = "/member/instation")
public class InstationMessageController {
	private final static Logger logger = LoggerFactory.getLogger(InstationMessageController.class);
	@Autowired
	private InstationMessageService instationMessageService;

	@Autowired
	private InstationToPersonService instationToPersonService;

	/**
	 * @param request
	 * @return
	 * @此方法的作用：显示在线留言
	 */
	@RequestMapping(value = "/view")
	public String view(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String type = request.getParameter("type");
		try {
			request.setAttribute("userName", StringUtil.strEncodeUTF(userName));
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.toString());
		}
		request.setAttribute("type", type);
		return "instation.view.instationView";
	}

	/**
	 * @param request
	 * @return
	 * @此方法的作用：反馈建议
	 */
	@RequestMapping(value = "/advise")
	public String advise(HttpServletRequest request) {
		return "instation.view.advise";
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @此方法的作用：发送邮件数据库添加数据 /send发送邮件，/forword转发邮件
	 */
	@RequestMapping(value = { "/send", "/forword" }, method = RequestMethod.POST)
	@ResponseBody
	public JSON add(HttpServletRequest request, InstationToPerson instationToPerson) {
		JSON json = new JSON();
		String info = "";
		try {
			InstationMessage ins = instationToPerson.getInstationMessage();
			InstationMessage in = instationMessageService.find(instationMessageService, new Sql().add("title", ins.getTitle()).add("count", ins.getCount()));
			long time = 0;
			if (in == null) {
				time = System.currentTimeMillis();
				ins.setCreateTime(new Timestamp(time));
				in = instationMessageService.create(instationMessageService, ins);
			}
			try {
				instationToPerson.setInstationMessage(in);
				if (time == 0)
					instationToPerson.setCreateTime(new Timestamp(System.currentTimeMillis()));
				else
					instationToPerson.setCreateTime(new Timestamp(time));
				instationToPerson.setSendHasDelete(false);
				instationToPerson.setReceiveHasDelete(false);
				instationToPerson.setReceiveHasRead(false);
				if (request.getSession().getAttribute(WebKeys.USER_KEY) != null) {
					User user = (User) request.getSession().getAttribute(WebKeys.USER_KEY);
					instationToPerson.setSendUser(user);
				}
				if (request.getSession().getAttribute(WebKeys.ADMINUSER_KEY) != null) {
					AdminUser adminUser = (AdminUser) request.getSession().getAttribute(WebKeys.ADMINUSER_KEY);
					instationToPerson.setSendAdminUser(adminUser);
				}
				if (instationToPerson.getUsers() != null) {
					for (int i = 0, len = instationToPerson.getUsers().size(); i < len; i++) {
						instationToPersonService.create(instationToPersonService, instationToPerson);
					}
				}
				info = "发送成功";
				json.setSuccess(true);
			} catch (Exception e) {
				info = "发送失败，请重新发送";
				json.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			info = e.getMessage();
			json.setSuccess(false);
		}
		json.setMsg(info);
		return json;
	}

	/**
	 * @param request
	 * @return
	 * @此方法的作用：根据邮件id查询邮件实体
	 */
	@RequestMapping(value = "/instationInfo/{id}")
	public String getInstation(@PathVariable int id, HttpServletRequest request) {
		request.setAttribute("instationMessage", instationToPersonService.find(instationToPersonService, new Sql().add("instationMessage.id", id)));
		return "";
	}

	/**
	 * @param request
	 * @param sql
	 * @param type
	 *            0/发件人列表 1/收件人列表 3/发件人未删除 4/接收人未删除 5/发件人伪删除 6/接收人伪删除 7/收件人未读
	 *            8/收件人已读
	 * @return
	 * @此方法的作用：邮件信息列表
	 */
	@RequestMapping(value = "/recevice/{type}")
	public String InstationMessage(@PathVariable int type, HttpServletRequest request, Sql sql) {
		List<InstationToPerson> listInstationMessage = new ArrayList<InstationToPerson>();
		long rowCount = 0;
		Page page = null;
		if (type == 3)
			sql.add("sendHasDelete", false);
		if (type == 4)
			sql.add("receiveHasDelete", false);
		if (type == 5)
			sql.add("sendHasDelete", true);
		if (type == 6)
			sql.add("receiveHasDelete", true);
		if (type == 7)
			sql.add("receiveHasRead", false);
		if (type == 8)
			sql.add("receiveHasRead", true);
		if (request.getSession().getAttribute(WebKeys.USER_KEY) != null) {
			User user = (User) request.getSession().getAttribute(WebKeys.USER_KEY);
			if (type == 1)
				sql.add("recieveUser.id", user.getId());
			if (type == 2)
				sql.add("sendUser.id", user.getId());
			rowCount = instationToPersonService.count(instationToPersonService, sql);
			page = Page.create(rowCount, request);
			sql.setBegin(page.getRowBegin()).setEnd(page.getPageSize());
			listInstationMessage = instationToPersonService.search(instationToPersonService, sql);
		}
		if (request.getSession().getAttribute(WebKeys.ADMINUSER_KEY) != null) {
			AdminUser adminUser = (AdminUser) request.getSession().getAttribute(WebKeys.ADMINUSER_KEY);
			if (type == 1)
				sql.add("sendAdminUser.id", adminUser.getId());
			if (type == 2)
				sql.add("recieveAdminUser.id", adminUser.getId());
			rowCount = instationToPersonService.count(instationToPersonService, sql);
			page = Page.create(rowCount, request);
			sql.setBegin(page.getRowBegin()).setEnd(page.getPageSize());
			listInstationMessage = instationToPersonService.search(instationToPersonService, sql);
		}
		request.setAttribute("listInstationMessage", listInstationMessage);
		page.sendPage(request);
		return "";
	}

	/**
	 * @param type
	 *            0/发送人伪删除1/接收人伪删除 2/直接删除
	 * @param request
	 * @param response
	 * @return @此方法的作用：删除邮件（伪删除和彻底删除）
	 */
	@RequestMapping(value = "/delete/{type}")
	public String del(@PathVariable int type, HttpServletRequest request, InstationToPerson instationToPerson) {
		if (type == 0 || type == 1)
			instationToPersonService.update(instationToPersonService, instationToPerson);
		if (type == 2)
			instationToPersonService.delete(instationToPerson);
		return "";
	}

	/**
	 * @param type
	 *            0/发件人邮件列表 1/收件人邮件列表 3/发件人邮件未删除 4/接收人邮件未删除 5/发件人邮件伪删除
	 *            6/接收人邮件伪删除 7/收件人邮件未读 8/收件人邮件已读
	 * @param request
	 * @param sql
	 * @return
	 * @此方法的作用：默认为7，收件人未读邮件
	 */
	@RequestMapping(value = "/getCount/{type}", method = RequestMethod.GET)
	@ResponseBody
	public long getCount(@PathVariable int type, HttpServletRequest request, Sql sql) {
		long rowCount = 0;
		if (type == 3)
			sql.add("sendHasDelete", false);
		if (type == 4)
			sql.add("receiveHasDelete", false);
		if (type == 5)
			sql.add("sendHasDelete", true);
		if (type == 6)
			sql.add("receiveHasDelete", true);
		if (type == 7)
			sql.add("receiveHasRead", false);
		if (type == 8)
			sql.add("receiveHasRead", true);
		if (request.getSession().getAttribute(WebKeys.USER_KEY) != null) {
			User user = (User) request.getSession().getAttribute(WebKeys.USER_KEY);
			if (type == 1)
				sql.add("recieveUser.id", user.getId());
			if (type == 2)
				sql.add("sendUser.id", user.getId());
			rowCount = instationToPersonService.count(instationToPersonService, sql);
		}
		if (request.getSession().getAttribute(WebKeys.ADMINUSER_KEY) != null) {
			AdminUser adminUser = (AdminUser) request.getSession().getAttribute(WebKeys.ADMINUSER_KEY);
			if (type == 1)
				sql.add("sendAdminUser.id", adminUser.getId());
			if (type == 2)
				sql.add("recieveAdminUser.id", adminUser.getId());
			rowCount = instationToPersonService.count(instationToPersonService, sql);
		}
		return rowCount;
	}
}
