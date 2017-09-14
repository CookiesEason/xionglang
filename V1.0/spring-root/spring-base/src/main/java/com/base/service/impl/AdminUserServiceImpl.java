package com.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.AdminUserDao;
import com.base.dao.MenuToRoleDao;
import com.base.dao.MenuTreeDao;
import com.base.form.Token;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.AdminUser;
import com.base.one.entity.Menu;
import com.base.one.entity.MenuToRole;
import com.base.one.entity.Role;
import com.base.service.AdminUserService;
import com.base.util.EncryptionUtil;
import com.base.util.Sql;
import com.base.view.MenuView;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
@Service
public class AdminUserServiceImpl extends GenericServiceImpl<AdminUser> implements AdminUserService {

	@Autowired
	private AdminUserDao adminUserDao;
	@Autowired
	private MenuTreeDao menusDao;
	@Autowired
	private MenuToRoleDao menuToRoleDao;

	@Override
	public void setGenericDao() {
		super.genericDao = adminUserDao;
	}

	@Override
	public void delete(AdminUser entity, Object... params) {
		adminUserDao.delete(entity.getId());
	}

	/**
	 * AdminUser 后台登陆
	 */
	@Override
	public AdminUser adminLogin(AdminUser au) {
		StringBuilder params = new StringBuilder("from AdminUser WHERE userName='" + au.getUserName() + "'");
		List<AdminUser> list = adminUserDao.search(params, 0, 0);
		if (list.size() > 0) {
			AdminUser adminUser = list.get(0);
			if (EncryptionUtil.byMD5(au.getPassWord()).toString().equals(adminUser.getPassWord().toString())) {
				// 验证密码
				return adminUser;
			}
		}
		return null;
	}

	@Override
	public Token createLoginToken(Token token, Role role) {
		Set<Menu> m = new LinkedHashSet<Menu>();
		Set<Menu> subM = new LinkedHashSet<Menu>();
		List<Menu> list = role.getLinkedListMenu();
		MenuToRole mtr = null;
		for (Menu menu : list) {
			if (menu.getType() == 2) {
				if (menu.getFather() == null)
					m.add(menu);
				else
					subM.add(menu);
			}
		}
		Map<Integer, MenuView> father = new LinkedHashMap<Integer, MenuView>();
		for (Menu menu : m)
			father.put(menu.getId(), new MenuView(menu.getName()));
		for (Menu menu : subM) {
			if (father.get(menu.getFather().getId()) == null) {// 只把子目录赋值给角色，此时需要默认把根目录也赋值给当前角色
				mtr = new MenuToRole();
				mtr.setMenu(menu);
				mtr.setRole(role);
				menuToRoleDao.create(mtr);
				father.put(menu.getFather().getId(), new MenuView(menu.getFather().getName()));
			}
		}
		for (Menu menu : subM) {
			Menu fm = menu.getFather();
			father.get(fm.getId()).getSubMenus().add(new MenuView(menu.getName(), menu.getUrl()));
		}
		token.setViews(new ArrayList<MenuView>(father.values()));
		return token;
	}

	@Override
	public Map<String, int[]> initRoleMapped() {
		Map<String, int[]> locked = new HashMap<String, int[]>();
		List<Menu> menus = menusDao.search(new Sql().add("function", null, " IS NOT NULL "));
		for (Menu m : menus) {
			int[] id = createRoleIds(m.getRoles());
			locked.put(m.getFunction(), id);
		}
		return locked;
	}

	private int[] createRoleIds(Set<Role> roles) {
		int[] ids = new int[roles.size()];
		int count = 0;
		for (Iterator<Role> it = roles.iterator(); it.hasNext();) {
			Role r = it.next();
			ids[count++] = r.getId();
		}
		return ids;
	}

}
