/**
 * 
 */
package com.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.form.ClientInfoForm;
import com.base.form.ImageInfo;
import com.base.one.entity.CheckCode;
import com.base.one.entity.User;
import com.base.service.CheckCodeService;
import com.base.service.UserService;
import com.base.util.CodeUtil;
import com.base.util.JSON;
import com.base.util.Sql;
import com.base.util.StringUtil;
import com.base.util.WebKeys;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月16日 @此类的作用：
 */
@Controller
@RequestMapping(value = "/code")
public class CodeControlller {
	private final static Logger logger = LoggerFactory.getLogger(CodeControlller.class);
	@Autowired
	private UserService userService;
	@Autowired
	private CheckCodeService checkCodeService;

	/**
	 * @param id
	 * @param request
	 * @param response
	 * @此方法的作用：创建图片验证码信息
	 */
	@RequestMapping(value = "/buildCode/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void buildCodeInfo(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) {
		ImageInfo imageInfo = CodeUtil.buildCodeRandomForJsp();
		request.setAttribute("validateCode", imageInfo.getInfo());
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 将图像输出到Servlet输出流中。
		try {
			ServletOutputStream sos = response.getOutputStream();
			ImageIO.write(imageInfo.getBuffImg(), "jpeg", sos);
			sos.close();
		} catch (IOException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @此方法的作用：获取手机验证码
	 */
	@RequestMapping(value = "/checkTelePhoneCode", method = RequestMethod.POST)
	@ResponseBody
	public JSON buildTelePhoneCodeInfo(HttpServletRequest request, HttpServletResponse response) {
		JSON json = new JSON();
		String info = "";
		String telephone = request.getParameter("telephone");
		try {
			User user = userService.find(userService, new Sql().add("telePhnoe", telephone));
			PrintWriter out;
			try {
				out = response.getWriter();
				if (user != null) {
					out.print(StringUtil.strEncodeUnUTF("此手机已注册用户，用户名为" + user.getUserName() + "请先登录，再在点击融资服务"));
				} else {
					CheckCode check = new CheckCode();
					check.setIp(new ClientInfoForm(request).getIP());
					check.setExpireAt(90 * 1000);
					check.setIsUse(0);
					long time = System.currentTimeMillis();
					check.setBegTime(new Timestamp(time));
					check.setEndTime(new Timestamp(time + WebKeys.CODE_TIME));
					check.setMobile(telephone);
					String code = CodeUtil.telePhone[(int) (Math.random() * 10)] + "" + CodeUtil.telePhone[(int) (Math.random() * 10)] + CodeUtil.telePhone[(int) (Math.random() * 10)] + CodeUtil.telePhone[(int) (Math.random() * 10)];
					check.setCheckCode(code);
					check.setStatus(0);
					checkCodeService.create(checkCodeService, check);
					String userName = StringUtil.strEncodeUnUTF("金科汇");
					String content = StringUtil.strEncodeUnUTF("您在金科汇申请的融资服务验证码为" + code + "也可搜索stffocus微信公众号【金科汇】");
					String location = "http://111.206.219.21/sms.aspx?action=send&userid=&account=" + userName + "&password=bjtf217bjtf&mobile=" + telephone + "&content=" + content + "&sendTime=&extno=";
					response.sendRedirect(location);
				}
			} catch (IOException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
			info = "发送成功";
			json.setSuccess(true);
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			info = e.getMessage();
			json.setSuccess(false);
		}
		json.setMsg(info);
		return json;
	}
}
