package com.zookeeper.util;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.form.ColumnBeanForm;
import com.base.form.TableInfoBeanForm;
import com.base.util.DatetimeUtil;
import com.base.util.ReadInfoUtil;
import com.base.util.StringUtil;
import com.base.util.SunxlConnectionFactory;
import com.sun.mail.iap.ConnectionException;
import com.zookeeper.entity.ImportDataColm;
import com.zookeeper.form.InsertBatchForm;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月11日 @此类的作用：
 */
public class DBSqlUtil {
	private static final Logger logger = LoggerFactory.getLogger(DBSqlUtil.class);

	private DBSqlUtil() {

	}

	private static class innerDBSqlUtil {
		private static final DBSqlUtil INSTANCE = new DBSqlUtil();
	}

	public static DBSqlUtil getInstance() {
		return innerDBSqlUtil.INSTANCE;
	}

	/**
	 * @param tbName表名
	 * @param values所有需要批添加的值
	 * @param colmnList此表的所有属性包含默认值
	 * @return 批处理成功还是失败
	 * @throws SQLException
	 * @throws ConnectionException
	 * @auto：熊浪 @Time：2017年8月9日
	 * @此方法的作用：批处理添加,注意值的顺序values必须和colmnList的顺序相同，否则添加出位置不正确
	 */
	public boolean insertBatch(String tableName, InsertBatchForm insertBatch) throws SQLException, ConnectionException {
		boolean flag = false;
		try {
			TableInfoBeanForm tbInfo = new TableInfoBeanForm(tableName);
			if (tbInfo != null && tbInfo.getColumnList() != null && tbInfo.getColumnList().size() > 0) {
				insertBatch.setTbInfo(tbInfo);
				executeAllAddBatch(insertBatch);
				flag = true;
			}
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException(e.getMessage());
		}
		return flag;

	}

	/**
	 * @param sql执行的insetSQL
	 * @param tbInfo分析的表结构，包括表字段，表名，表字段类型
	 * @param values添加的数据
	 * @param colmList表需要执行的insert的字段
	 * @return 返回执行失败还是成
	 * @throws SQLException
	 * @throws ConnectionException
	 * @auto：熊浪 @Time：2017年8月9日
	 * @此方法的作用：执行批处理，SQL语句的字段、colmList、values中的list三者的长度一致，顺序相同,此批处理的conn未关闭，事物结束后提交关闭
	 */
	private boolean executeAllAddBatch(InsertBatchForm insertBatch) throws SQLException, ConnectionException {
		boolean flag = false;
		PreparedStatement pst = null;
		if (insertBatch.getColmnList() != null) {
			String[] columns = new String[insertBatch.getColmnList().size()];
			for (int i = 0, len = insertBatch.getColmnList().size(); i < len; i++)
				columns[i] = insertBatch.getColmnList().get(i).getColuna();
			pst = SunxlConnectionFactory.getUserConnection(insertBatch.getJndiName())
					.prepareStatement(getStringSql(insertBatch.getTbInfo().getTableName(), 1, null, columns));
			try {
				if (insertBatch.getValues() != null) {
					for (int i = 0, len = insertBatch.getValues().size(); i < len; i++)
						executePrepareStatement(pst, insertBatch.getTbInfo(), insertBatch.getValues().get(i),
								insertBatch.getColmnList());
					pst.executeBatch();
				}
				flag = true;
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				throw new SQLException("批处理异常" + e.getMessage());
			} finally {
				SunxlConnectionFactory.closeUserConnection(pst, insertBatch.getJndiName());
			}
		}
		return flag;
	}

	/**
	 * @param pst
	 * @param tbInfo
	 * @param values
	 * @param colmList
	 * @return
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月9日
	 * @此方法的作用：执行单挑数据预编译
	 */
	private String executePrepareStatement(PreparedStatement pst, TableInfoBeanForm tbInfo, List<String> values,
			List<ImportDataColm> colmList) throws SQLException {
		String info = "";
		try {
			info = executeAddBatchObject(pst, colmList, values, tbInfo);
		} catch (SQLException e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			throw new SQLException("数据源获取异常" + e.getMessage());
		}
		return info;
	}

	/**
	 * 拼接pst
	 * 
	 * @param pst
	 * @param colmList
	 * @param fields
	 * @param tbInfo
	 * @return
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月9日 @此方法的作用：
	 */
	private String executeAddBatchObject(PreparedStatement pst, List<ImportDataColm> colmList, List<String> fields,
			TableInfoBeanForm tbInfo) throws SQLException {
		String exceptionMsg = "";
		if (fields.size() != colmList.size()) {
			exceptionMsg = "数据内容：" + ReadInfoUtil.replaceLowOrderASCIICharacters(Arrays.toString(fields.toArray()))
					+ ",数据序列个数[" + fields.size() + "]与导数配置字段个数[" + colmList.size() + "]不相同";
			throw new SQLException(exceptionMsg);
		} else {
			String coluna = "字段:[";
			String value = "数据值:[";
			for (int i = 0, len = fields.size(); i < len; i++) {
				// 根据colm的类型来设置参数
				String val = fields.get(i);
				String defaval = colmList.get(i).getDefava();// 顺序必须跟给定的数据顺序一致
				if (StringUtil.isEmpty(val)) {
					if (StringUtil.isNotEmpty(defaval))
						val = defaval;
				}
				setStatement(pst, colmList.get(i).getColuna(), i + 1, val, tbInfo);
				if (i < fields.size() - 1) {
					coluna += colmList.get(i).getColuna() + ",";
					value += val + ",";
				} else {
					coluna += colmList.get(i).getColuna() + "],";
					value += val + "],";
				}
			}
			exceptionMsg = value + coluna;
			logger.warn(exceptionMsg);
			try {
				pst.addBatch();
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				throw new SQLException(pst + "addBatch出错" + e.getMessage());
			}
		}
		return exceptionMsg;
	}

	/**
	 * 根据指定的类型拼接pst
	 * 
	 * @param pst
	 * @param columnName
	 * @param idx
	 * @param val
	 * @param tbInfo
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月9日 @此方法的作用：
	 */
	private void setStatement(PreparedStatement pst, String columnName, int idx, String val, TableInfoBeanForm tbInfo)
			throws SQLException {
		String colmType = "";
		try {
			colmType = getColumnType(columnName, tbInfo);
		} catch (SQLException e1) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e1.getMessage());
			throw new SQLException(e1.getMessage());
		}
		if (colmType.indexOf("INT") >= 0 || colmType.indexOf("INTEGER") >= 0) {
			try {
				if (val == null || "".equals(val))
					pst.setNull(idx, java.sql.Types.INTEGER);
				else
					pst.setInt(idx, Integer.parseInt(val));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("CHAR") >= 0) {
			try {
				pst.setString(idx, val);
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("VARCHAR") >= 0) {
			try {
				pst.setString(idx, val);
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("TIMESTAMP") >= 0) {
			long time = DatetimeUtil.changeToDate(val, DatetimeUtil.TIME_FORMAT_Y_M_D).getTime();
			try {
				pst.setTimestamp(idx, new Timestamp(time));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("CLOB") >= 0) {
			try {
				pst.setString(idx, val);
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("NUMBER") >= 0) {
			try {
				if (val == null || val.equals("")) {
					pst.setNull(idx, java.sql.Types.NUMERIC);
				} else {
					BigDecimal bigDecimal = new BigDecimal(val);
					pst.setBigDecimal(idx, bigDecimal);
				}
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("BOOLEAN") >= 0) {
			try {
				pst.setBoolean(idx, Boolean.valueOf(val));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("DATE") >= 0) {
			long time = DatetimeUtil.changeToDate(val, DatetimeUtil.TIME_FORMAT_Y_M_D).getTime();
			try {
				pst.setDate(idx, new java.sql.Date(time));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else if (colmType.indexOf("DECIMAL") >= 0) {
			try {
				pst.setBigDecimal(idx, new BigDecimal(val));
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		} else {
			try {
				pst.setString(idx, val);
			} catch (SQLException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		}
	}

	/**
	 * @param tbName表名
	 * @param type需要拼接的sql为1：增、2：删、3：改中的哪一种
	 * @param values改的set字段值
	 * @param columnswhere条件和insert的添加字段
	 * @auto：熊浪
	 * @Time：2017年8月11日 @此方法的作用：根据提供的列集合创建添加Sql，最简单的增删改
	 */
	public String getStringSql(String tbName, int type, String[] values, String... columns) {
		StringBuffer sbOne = new StringBuffer();
		String sql = null;
		if (type == 1) {
			StringBuffer sbTwo = new StringBuffer();
			sbOne.append("INSERT INTO " + tbName + " (");
			sbTwo.append(" VALUES (");
			for (int i = 0, len = columns.length; i < len; i++) {
				sbOne.append(columns[i] + ",");
				sbTwo.append("?" + ",");
			}
			sbOne.replace(sbOne.length() - 1, sbOne.length(), ")");
			sbTwo.replace(sbTwo.length() - 1, sbTwo.length(), ")");
			sql = sbOne.append(sbTwo).toString();
		}
		if (type == 2) {
			sbOne.append("DELETE FROM " + tbName);
			if (columns.length > 0)
				sbOne.append(" WHERE ");
			for (int i = 0, len = columns.length; i < len; i++) {
				if (i == len - 1)
					sbOne.append(columns[i] + " = ? ");
				else
					sbOne.append(columns[i] + " = ? AND ");
			}
			sql = sbOne.toString();
		}
		if (type == 3 && values != null && values.length > 0) {
			sbOne.append("UPDATE " + tbName + " SET ");
			for (int i = 0, len = values.length; i < len; i++) {
				if (i != len - 1)
					sbOne.append(values[i] + " = ? ");
				else
					sbOne.append(values[i] + " = ? ,");
			}
			for (int i = 0, len = columns.length; i < len; i++) {
				if (i == len - 1)
					sbOne.append(columns[i] + " = ? ");
				else
					sbOne.append(columns[i] + " = ? AND ");
			}
			sql = sbOne.toString();
		}
		return sql;
	}

	/**
	 * @param columnName字段名
	 * @param tbInfo初始化信息数据
	 * @param conn连接池
	 * @param tbName表名
	 * @return
	 * @throws SQLException
	 * @auto：熊浪 @Time：2017年8月8日
	 * @此方法的作用：获取对应字段的数据类型
	 */
	public String getColumnType(String columnName, TableInfoBeanForm tbInfo) throws SQLException {
		List<ColumnBeanForm> columnList = tbInfo.getColumnList();
		for (Iterator<ColumnBeanForm> iterator = columnList.iterator(); iterator.hasNext();) {
			ColumnBeanForm columnBean = (ColumnBeanForm) iterator.next();
			if (columnBean.getColumnName().equalsIgnoreCase(columnName)) {
				return columnBean.getSqlType();
			}
		}
		return "";
	}
}
