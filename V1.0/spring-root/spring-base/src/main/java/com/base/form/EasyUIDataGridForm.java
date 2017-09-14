package com.base.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
@SuppressWarnings("rawtypes")
public class EasyUIDataGridForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private int page;
	private int row;
	private long total = 0L;
	private List rows = new ArrayList();
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
