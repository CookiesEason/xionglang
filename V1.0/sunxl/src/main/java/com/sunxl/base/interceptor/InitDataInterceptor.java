package com.sunxl.base.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sunline.base.interfaces.MethodLog;
import com.sunxl.base.entity.AdminUser;
import com.sunxl.base.form.Token;
import com.sunxl.base.service.AdminUserService;
import com.sunxl.base.service.MenuService;
import com.sunxl.base.service.RoleService;
import com.sunxl.base.service.SysTidDataService;
import com.sunxl.base.service.SysDataService;
import com.sunxl.base.util.JSON;
import com.sunxl.base.util.Sql;
import com.sunxl.base.util.SunXlProperties;
import com.sunxl.base.util.WebKeys;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月15日
 * @此类作用：servelet执行完成之后初始化数据，可以刷新内存只需要执行一遍拦截的路径
 * @参考文献http://blog.csdn.net/xionglangs/article/details/72354227
 */
@Controller
@RequestMapping(value = "/initData")
public class InitDataInterceptor extends HandlerInterceptorAdapter implements InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(InitDataInterceptor.class);
	// 注意：因为map数据是加入内存中的，所以对他的添加和删除操作都需要重新刷新内存，Iterator移除需要重新刷新内存
	public static Map<String, Object> map = new HashMap<String, Object>();
	@Autowired
	private MenuService menuService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SysTidDataService sysTidDataService;
	@Autowired
	private SysDataService sysDataService;

	@Override
	public void afterPropertiesSet() throws Exception {
		map.clear();// 初始化清空map
		loadall();
	}

	private void loadall() {
		try {
			loadMeun();
			loadRoles();
			loadSysTidData();
			loadSysData();
			loadProPerties("META-INF/system.properties");
			loadProPerties("META-INF/dataSource.properties");
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+"初始化数据出错" + e.getMessage());
		}
	}
	/**
	 * 读取初始化表信息
	 * @throws Exception
	 * @auto：熊浪
	 * @Time：2017年8月8日
	 * @此方法的作用：
	 */
	private void loadSysData() throws Exception {
		try {
			map.put("sysDataService", sysDataService.search(sysDataService, new Sql()));
		} catch (Exception e) {
			throw new Exception("初始化SysData出错" + e.getMessage());
		}
	}

	/**
	 * 读取系统信息
	 * 
	 * @throws Exception
	 * @auto：熊浪 @Time：2017年8月8日 @此方法的作用：
	 */
	private void loadSysTidData() throws Exception {
		try {
			map.put("sysTidDataService", sysTidDataService.search(sysTidDataService, new Sql()));
		} catch (Exception e) {
			throw new Exception("初始化SysTidData出错" + e.getMessage());
		}
	}

	/**
	 * 初始化模块
	 * 
	 * @throws Exception
	 */
	private void loadMeun() throws Exception {
		try {
			map.put("menuService", menuService.search(menuService, new Sql()));
		} catch (Exception e) {
			throw new Exception("初始化loadMeun出错" + e.getMessage());
		}
	}

	/**
	 * 初始化角色
	 * 
	 * @throws Exception
	 */
	private void loadRoles() throws Exception {
		try {
			map.put("roleService", roleService.search(roleService, new Sql()));
		} catch (Exception e) {
			throw new Exception("初始化loadRoles出错" + e.getMessage());
		}
	}

	/**
	 * 初始化项目路径
	 * 
	 * @param path
	 * @throws Exception
	 * @auto：熊浪 @Time：2017年7月25日 @此方法的作用：
	 */
	private void loadProPerties(String path) throws Exception {
		try {
			SunXlProperties sunXlProperties = new SunXlProperties(path);
			map.put(path, sunXlProperties);
		} catch (Exception e) {
			throw new Exception("初始化loadProPerties出错" + e.getMessage());
		}
	}

	@RequestMapping(value = "/refreshData", method = RequestMethod.POST)
	@MethodLog(remark = "刷新Session和内存信息")
	@ResponseBody
	public JSON reFreshData(HttpServletRequest request, HttpSession session) {
		JSON json = new JSON();
		try {
			afterPropertiesSet();
			AdminUser adminUser = (AdminUser) session.getAttribute(WebKeys.ADMINUSER_KEY);
			adminUser = adminUserService.find(adminUserService, adminUser.getId());
			Token token = new Token(adminUser);
			token = adminUserService.createLoginToken(token, adminUser.getRole());
			request.getSession().setAttribute(WebKeys.TOKEN, token);
			json.setMsg("刷新Session和内存信息成功");
			json.setSuccess(true);
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
			json.setSuccess(false);
			json.setMsg("刷新Session和内存信息失败" + e.getMessage());
		}
		return json;
	}
}
