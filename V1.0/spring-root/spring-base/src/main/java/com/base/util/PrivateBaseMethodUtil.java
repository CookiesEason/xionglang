package com.base.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.form.EasyUIDataGridForm;
import com.base.form.ViewInfoForm;
import com.base.generic.service.GenericService;
import com.base.interceptor.InitDataInterceptor;
import com.base.interfaces.MethodLog;
import com.base.one.entity.AdminUser;
import com.base.one.entity.Menu;
import com.base.one.entity.Role;
import com.base.one.entity.SysData;
import com.base.one.entity.SysTidData;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月7日 @此类作用：共同方法
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PrivateBaseMethodUtil {
	private static final Logger logger = LoggerFactory.getLogger(PrivateBaseMethodUtil.class);

	/**
	 * @param request
	 * @param searchParamList：查询按钮定义的查询字段
	 * @param basePath：访问的基础映射，一般为控制层的映射对象
	 * @param page：分页初始化页数
	 * @param path：返回的路径
	 * @param addObject：添加对象
	 * @param formId：添加对象用到的formId
	 * @param operateObject：操作的对象名称
	 * @param params：所需按钮和路径顺序不能错
	 * @ 路径以"/"开始表示不需要加上根路径，直接写全路径 否则以根路径basePath+"/"+params的路径
	 * @return 返回路径 路径
	 * @auto：熊浪
	 * @Time：2017年7月14日 @此方法的作用：初始化一定的值,默认有添加按钮
	 * @throws Exception
	 */
	@MethodLog(remark = "初始化button，路径")
	public static void goObjectViewList(HttpServletRequest request, ViewInfoForm viewInfoForm) throws Exception {
		try {
			request.setAttribute("searchParamList", viewInfoForm.getSearchParamList());
			request.setAttribute("baseUrl", viewInfoForm.getBaseUrl());
			request.setAttribute("basePage", viewInfoForm.getPage());
			request.setAttribute("addButton", viewInfoForm.getAddButton());
			request.setAttribute("operateObject", viewInfoForm.getObjectInfo());
			request.setAttribute("listBtns", viewInfoForm.getListButton());
			request.setAttribute("formId", viewInfoForm.getAddButton().getFormId());
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * @param request
	 * @param genericService
	 * @param flag
	 * @return
	 * @auto：熊浪
	 * @Time：2017年8月4日 @此方法的作用：返回JSON格式的EasyUIDataGridForm对象
	 */
	@MethodLog(remark = "当需要返回前台的JSON数据不含有死循环字段时")
	public static EasyUIDataGridForm getEasyUIDataGridJSON(HttpServletRequest request, GenericService genericService) {
		EasyUIDataGridForm form = new EasyUIDataGridForm();
		try {
			Sql sql = new Sql();
			int pageIndex = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));// 当前页
			int row = request.getParameter("rows") == null ? 1 : Integer.parseInt(request.getParameter("rows"));// 每页的条数
			long rowCount = getEasyUiTotal(genericService, sql);
			sql.setBegin((pageIndex - 1) * row).setEnd(row);
			List<?> rows = getEasyUiListObject(genericService, sql);
			form.setRows(rows);
			form.setTotal(rowCount);
			request.setAttribute("result", "200");
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			request.setAttribute("result", "300");
		}
		return form;
	}

	/**
	 * @param request
	 * @param genericService
	 * @return
	 * @auto：熊浪
	 * @Time：2017年8月4日 @此方法的作用：返回JSON格式的String字符串
	 */
	@MethodLog(remark = "当需要返回前台的JSON数据含有死循环时，返回的就是已经序列化的数据")
	public static String getEasyUIDataGridString(HttpServletRequest request, GenericService genericService, boolean... flag) {
		EasyUIDataGridForm form = new EasyUIDataGridForm();
		SimpleJsonFormat sjf = null;
		try {
			Sql sql = new Sql();
			int pageIndex = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));// 当前页
			int row = request.getParameter("rows") == null ? 1 : Integer.parseInt(request.getParameter("rows"));// 每页的条数
			long rowCount = getEasyUiTotal(genericService, sql);
			sql.setBegin((pageIndex - 1) * row).setEnd(row);
			List<?> rows = getEasyUiListObject(genericService, sql);
			if (flag.length > 0) {
				StackTraceElement[] stm = new Exception().getStackTrace();
				rows = SimpleJsonFormat.getNewListObject(rows, stm[1].getClassName(), stm[1].getMethodName(), flag[0]);
			}
			sjf = new SimpleJsonFormat(rows);
			form.setRows(rows);
			form.setTotal(rowCount);
			sjf = new SimpleJsonFormat(form);
			request.setAttribute("result", "200");
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			request.setAttribute("result", "300");
		}
		return sjf.serialize();
	}

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日
	 * @此方法的作用：返回数据总数
	 */
	@MethodLog(remark = "根据HQL语句返回数据总数")
	public static long getEasyUiTotal(GenericService genericService, Sql sql) {
		return genericService.count(genericService, sql);
	}

	/**
	 * @param genericService
	 * @param sql
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日
	 * @此方法的作用：返回数据集合
	 */
	@MethodLog(remark = "根据HQL语句返回数据集合")
	public static List<?> getEasyUiListObject(GenericService genericService, Sql sql) {
		return genericService.search(genericService, sql);
	}

	/**
	 * @param request
	 * @param genericService
	 * @param path
	 * @param id
	 * @param obj
	 * @return
	 * @auto：熊浪
	 * @Time：2017年8月4日 @此方法的作用：根据ID返回对象
	 */
	@MethodLog(remark = "根据ID返回对象")
	public static String viewObject(HttpServletRequest request, GenericService genericService, String path, int id, String obj) {
		try {
			Object o = genericService.find(genericService, id);
			request.setAttribute(obj, o);
			request.setAttribute("result", "200");
			return path;
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			request.setAttribute("result", "300");
		}
		return "private.error";
	}

	/**
	 * @param genericService
	 * @param obj对象
	 * @param info对象名
	 * @return
	 * @auto：熊浪 @Time：2017年7月27日
	 * @此方法的作用：添加对象
	 */
	@MethodLog(remark = "添加对象，注意自增ID，对象ID必须为null")
	public static JSON addObject(GenericService genericService, Object obj, String info) {
		JSON json = new JSON();
		try {
			genericService.create(genericService, obj);
			json.setSuccess(true);
			info = "添加" + info + "成功";
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + info + e.getMessage());
			json.setSuccess(false);
			info = "添加" + info + "失败" + e.getMessage();
		}
		json.setMsg(info);
		return json;
	}

	/**
	 * @param genericService
	 * @param id对象ID
	 * @param info对象名
	 * @return
	 * @auto：熊浪 @Time：2017年7月27日
	 * @此方法的作用：删除对象
	 */
	@MethodLog(remark = "根据ID，删除对象")
	public static JSON deleteObject(GenericService genericService, int id, String info) {
		JSON json = new JSON();
		try {
			genericService.delete(genericService, id);
			json.setSuccess(true);
			info = "删除" + info + "成功";
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + info + e.getMessage());
			json.setSuccess(false);
			info = "删除" + info + "失败" + e.getMessage();
		}
		json.setMsg(info);
		return json;
	}

	/**
	 * @param genericService
	 * @param obj对象
	 * @param info对象名
	 * @return
	 * @auto：熊浪 @Time：2017年7月27日
	 * @此方法的作用：修改对象
	 */
	@MethodLog(remark = "根据对象，修改对象，数据库必须包含此对象的ID,相同的不变，不同的修改")
	public static JSON updateObject(GenericService genericService, Object obj, String info) {
		JSON json = new JSON();
		try {
			genericService.update(genericService, obj);
			json.setSuccess(true);
			info = "修改" + info + "成功";
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			json.setSuccess(false);
			info = "修改" + info + "失败" + e.getMessage();
		}
		json.setMsg(info);
		return json;
	}

	/**
	 * 存储所有的系统来源信息
	 * 
	 * @param request
	 * @auto：熊浪 @Time：2017年8月8日 @此方法的作用：
	 */
	@MethodLog(remark = "获取所有的源系统信息")
	public static void requestSetSysTidData(HttpServletRequest request) {
		List<SysTidData> sysTidDatas = (List<SysTidData>) InitDataInterceptor.map.get("sysTidDataService");
		request.setAttribute("sysTidDatas", sysTidDatas);
	}

	/**
	 * 获取初始化信息
	 * 
	 * @param request
	 * @param project
	 *            指向的项目
	 * @param type
	 *            指向的位置
	 * @auto：熊浪 @Time：2017年8月8日 @此方法的作用：
	 */
	@MethodLog(remark = "获取所有的初始化信息")
	public static void requestSetSysData(HttpServletRequest request, String project, String type, String elName) {
		List<SysData> sysDatas = (List<SysData>) InitDataInterceptor.map.get("sysDataService");
		List<SysData> newSysDatas = new ArrayList<SysData>();
		if (sysDatas != null) {
			for (int i = 0, len = sysDatas.size(); i < len; i++) {
				if (sysDatas.get(i).getProject().equals(project) && sysDatas.get(i).getType().equals(type))
					newSysDatas.add(sysDatas.get(i));
			}
		}
		request.setAttribute(elName, newSysDatas);
	}

	/**
	 * 获取比当前角色级别更低的角色 复制一份数据， 避免对map进行修改操作，刷新内存降低代码运行效率
	 * 
	 * @param request
	 * @auto：熊浪
	 * @Time：2017年7月27日 @此方法的作用：获取等于或低于当前角色的角色信息
	 */
	public static void requestSetRole(HttpServletRequest request) {
		List<Role> roles = (List<Role>) InitDataInterceptor.map.get("roleService");
		Iterator<Role> r = roles.iterator();
		Role role = null;
		List<Role> listRole = new ArrayList<Role>();
		AdminUser adminUser = (AdminUser) request.getSession().getAttribute(WebKeys.ADMINUSER_KEY);
		int rank = adminUser.getRole().getRank();
		while (r.hasNext()) {
			role = r.next();
			if (role.getRank() >= rank)
				listRole.add(role);
		}
		request.setAttribute("roles", listRole);
	}

	/**
	 * 获取所有的根目录
	 * 
	 * @param request
	 * @auto：熊浪
	 * @Time：2017年7月27日 @此方法的作用：获取父级菜单
	 */
	public static void requestSetMenu(HttpServletRequest request) {
		List<Menu> menus = (List<Menu>) InitDataInterceptor.map.get("menuService");
		Iterator<Menu> m = menus.iterator();
		Menu menu = null;
		List<Menu> listMenus = new ArrayList<Menu>();
		while (m.hasNext()) {
			menu = m.next();
			if (menu.getFather() == null)
				listMenus.add(menu);
		}
		request.setAttribute("menus", listMenus);
	}
}
