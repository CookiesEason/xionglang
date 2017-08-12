package com.sunxl.base.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：
 */
@SuppressWarnings("unused")
public class PageSupport extends Page {

	private long rowCount;

	private int rowBegin;

	private long rowEnd;

	private int pageSize;

	private int pageNow = 1;

	private int pageEnd;

	private int pageDisplaySize;

	private List<Integer> displayPages;

	private String formId;

	private boolean selectPluginStarted;
	private boolean textPluginStarted;

	public PageSupport(int pageNow, int pageSize) {
		this.pageNow = pageNow;
		this.pageSize = pageSize;
	}

	public PageSupport(long rowCount, int pageSize, int pageNow, int pageDisplaySize, String formId) {
		long pageCount = rowCount % pageSize == 0 ? rowCount / pageSize : (rowCount / pageSize) + 1;
		if (pageCount < 1) {
			pageCount = 1;
		}
		pageEnd = (int) pageCount;

		if (pageNow < 1) {
			pageNow = 1;
		}

		if (pageNow > pageEnd) {
			pageNow = pageEnd;
		}
		this.pageNow = pageNow;

		this.pageDisplaySize = pageDisplaySize;

		this.pageSize = pageSize;

		this.rowCount = rowCount;

		int pageNowPrev = pageNow - 1 == 0 ? 1 : pageNow - 1;
		// this.rowBegin
		// =pageNow==1?0:(pageNow*pageSize)-(pageNowPrev*pageSize);
		this.rowBegin = pageNow == 1 ? 0 : (pageNow * pageSize) - pageSize;

		long endRow = this.rowBegin + pageSize;
		this.rowEnd = endRow > this.rowCount ? this.rowCount : endRow;

		this.formId = formId;
		this.displayPages = createDidplayPage();
	}

	protected List<Integer> createDidplayPage() {
		int size = this.pageDisplaySize;

		int scale = size % 2 == 0 ? size / 2 : (size / 2) + 1;

		int leftOffset = scale - 1;

		List<Integer> pages = new ArrayList<Integer>();

		int pageDisplayStart = createDisplayStart(leftOffset, pageNow);
		int display = pageDisplayStart + size;
		if (display > pageEnd) {
			int newOffset = (pageEnd - pageDisplayStart) + 1;

			pageDisplayStart = pageDisplayStart - (size - newOffset);
			if (pageDisplayStart < 1) {
				pageDisplayStart = 1;
			}
			display = pageEnd + 1;
		}

		for (int i = pageDisplayStart; i < display; i++) {
			pages.add(i);
		}
		return pages;
	}

	private int createDisplayStart(int offset, int pageNow2) {
		int buf = pageNow2 - offset;
		if (buf <= 0) {
			return createDisplayStart(offset - 1, pageNow2);
		}
		return buf;
	}

	public long getRowCount() {
		return rowCount;
	}

	public int getRowBegin() {
		return rowBegin;
	}

	public long getRowEnd() {
		return rowEnd;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNow() {
		return pageNow;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public int getPageDisplaySize() {
		return pageDisplaySize;
	}

	@Override
	public boolean isHeadOmit() {
		return !this.displayPages.contains(1);
	}

	@Override
	public boolean isTailOmit() {
		return !this.displayPages.contains(this.pageEnd);
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Override
	public void sendPage(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("pageSupport", this);

	}

	public List<Integer> getDisplayPages() {
		return displayPages;
	}

	@Override
	public boolean isSelectPluginStarted() {

		return selectPluginStarted;
	}

	@Override
	public boolean isTextPluginStarted() {

		return textPluginStarted;
	}

	public void startSelectPlugin(boolean selectPluginStarted) {
		this.selectPluginStarted = selectPluginStarted;
	}

	public void startTextPlugin(boolean textPluginStarted) {
		this.textPluginStarted = textPluginStarted;
	}

	public int[] getPageArray() {
		int[] arr = new int[this.pageEnd];
		for (int i = 0; i < pageEnd; i++) {
			arr[i] = i + 1;
		}
		return arr;
	}
}
