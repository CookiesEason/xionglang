package com.base.view;

import java.util.ArrayList;
import java.util.List;

import com.base.two.entity.NewInfomation;

public class PublicMenuView {
	private String name;
	private String url;
	private List<String> cssNames;
	private List<PublicMenuView> publicMenuView;
	private List<NewInfomation> infomations;

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

	public List<PublicMenuView> getPublicMenuView() {
		return publicMenuView;
	}

	public void setPublicMenuView(List<PublicMenuView> publicMenuView) {
		this.publicMenuView = publicMenuView;
	}

	public List<String> getCssNames() {
		return cssNames;
	}

	public void setCssNames(List<String> cssNames) {
		this.cssNames = cssNames;
	}

	public List<NewInfomation> getInfomations() {
		return infomations;
	}

	public void setInfomations(List<NewInfomation> infomations) {
		this.infomations = infomations;
	}

	public PublicMenuView(String name, String url) {
		super();
		this.name = name;
		this.url = url;
		this.publicMenuView = new ArrayList<PublicMenuView>();
	}

	public PublicMenuView(String name, String url, List<String> cssNames) {
		super();
		this.name = name;
		this.url = url;
		this.cssNames = cssNames;
		this.publicMenuView = new ArrayList<PublicMenuView>();
	}
}
