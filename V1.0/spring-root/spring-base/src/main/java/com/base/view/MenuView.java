package com.base.view;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：
 */
public class MenuView {

	private String name;
	private String url;
	private List<MenuView> subMenus;

	public MenuView() {
		super();
	}

	public MenuView(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public MenuView(String name) {
		super();
		this.name = name;
		this.subMenus = new ArrayList<MenuView>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuView> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<MenuView> subMenus) {
		this.subMenus = subMenus;
	}

}
