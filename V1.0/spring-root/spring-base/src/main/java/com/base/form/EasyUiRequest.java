package com.base.form;

import com.base.util.Sql;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
public class EasyUiRequest extends Sql {

	private int page;

	private int rows;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public int getBegin() {
		return page == 1 ? 0 : (page - 1) * rows;
	}

	@Override
	public int getEnd() {
		return rows;
	}

	public EasyUiResponse createResponse() {
		EasyUiResponse response = new EasyUiResponse();
		response.setPageNumber(page);
		response.setPageSize(rows);
		return response;
	}

}
