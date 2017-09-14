package com.base.form;

import java.util.List;

import com.base.one.entity.AdminUser;
import com.base.view.MenuView;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
public class Token {

	private int[] roles;
	private List<MenuView> views;

	public Token(AdminUser user) {
		this.roles = user.roleIds();
	}

	public int[] getRoles() {
		return roles;
	}

	public void setRoles(int[] roles) {
		this.roles = roles;
	}

	public List<MenuView> getViews() {
		return views;
	}

	public void setViews(List<MenuView> views) {
		this.views = views;
	}

	public boolean canAccess(int[] is) {
		for (int i = 0; i < is.length; i++) {
			for (int j = 0; j < roles.length; j++) {
				if (is[i] == roles[j])
					return true;
			}
		}
		return false;
	}

}
