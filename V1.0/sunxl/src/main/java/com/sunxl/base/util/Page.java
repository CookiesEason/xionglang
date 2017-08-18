package com.sunxl.base.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：
 */
public abstract class Page {

	public static final int _PAGE_DISPLAY_SIZE = 5;

	public static final int _PAGE_SIZE = 10;

	public static Page create() {
		return new PageSupport(1, _PAGE_SIZE);
	}

	public static Page create(long rowCount, int pageNow) {
		return new PageSupport(rowCount, _PAGE_SIZE, pageNow, _PAGE_DISPLAY_SIZE, null);
	}

	public static Page create(long rowCount, HttpServletRequest request) {
		String pageNow = request.getParameter("pageNow");
		int now = 0;
		if (pageNow != null && !pageNow.isEmpty()) {
			now = Integer.parseInt(pageNow);
		}
		return create(rowCount, now);
	}

	public static Page adminCreate(long rowCount, int pageSize, HttpServletRequest request) {
		String pageNow = request.getParameter("pageNow");
		int now = 0;
		if (pageNow != null && !pageNow.isEmpty()) {
			now = Integer.parseInt(pageNow);
		}
		return create(rowCount, pageSize, now);
	}

	public static Page create(long rowCount, int pageSize, int pageNow, int pageDisplaySize) {
		return new PageSupport(rowCount, pageSize, pageNow, pageDisplaySize, null);
	}

	public static Page create(long rowCount, int pageSize, int pageNow) {
		return new PageSupport(rowCount, pageSize, pageNow, _PAGE_DISPLAY_SIZE, null);
	}

	public static Page create(long rowCount, int pageSize, int pageNow, String formId) {
		return new PageSupport(rowCount, pageSize, pageNow, _PAGE_DISPLAY_SIZE, formId);
	}

	public static Page create(long rowCount, int pageNow, String formId) {
		return new PageSupport(rowCount, _PAGE_SIZE, pageNow, _PAGE_DISPLAY_SIZE, formId);
	}

	public abstract long getRowCount();

	public abstract int getRowBegin();

	public abstract long getRowEnd();

	public abstract int getPageSize();

	public abstract int getPageNow();

	public abstract int getPageEnd();

	public abstract int getPageDisplaySize();

	public abstract boolean isHeadOmit();

	public abstract boolean isTailOmit();

	public abstract boolean isSelectPluginStarted();

	public abstract boolean isTextPluginStarted();

	public abstract void sendPage(HttpServletRequest httpServletRequest);

}
