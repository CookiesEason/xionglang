package com.sunxl.base.form;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.iap.ConnectionException;
import com.sunxl.base.util.SunxlConnectionFactory;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年8月8日 @此类作用：获取表字段和字段类型
 */
public class TableInfoBeanForm {
	private static final Logger logger = LoggerFactory.getLogger(TableInfoBeanForm.class);
	private String tableName;
	private List<ColumnBeanForm> columnList = new ArrayList<ColumnBeanForm>();

	public String getTableName() {
		return tableName;
	}

	public List<ColumnBeanForm> getColumnList() {
		return columnList;
	}

	public TableInfoBeanForm(String tbName) throws SQLException, ConnectionException {
		this.tableName = tbName.toUpperCase();
		ResultSet rs = null;
		try {
			DatabaseMetaData meta = SunxlConnectionFactory.getCurrentConnection().getMetaData();
			rs = meta.getColumns(null, null, tableName, null);
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");// COLUMN_SIZE,DECIMAL_DIGITS,NULLABLE
				String sqlType = rs.getString("TYPE_NAME");
				ColumnBeanForm column = new ColumnBeanForm();
				column.setColumnName(columnName);
				column.setSqlType(sqlType);
				columnList.add(column);
			}
		} catch (SQLException e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
			throw new SQLException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
			}
		}
	}
}
