package com.base.util;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年8月2日 @此类作用：配合需要显示的按钮个数使用
 */
public class ButtonUtil {

	private String buttonName;// button名

	private String valueName;// valuename

	private String buttonUrl;// 访问路径

	private String formId;// 提交表单名

	private String type;// 提交方式

	private String dataType;// 返回值方式

	private boolean flag;// 此按钮是否显示

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getButtonUrl() {
		return buttonUrl;
	}

	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @param info顺序，第一个值为显示给用户的button名,第二个值为button的访问路径，第三个值为是否显示，第四个为button的提交Id名，第五个值为表单名,第六个值为访问方式，第七个值为返回方式，null和""不会赋值，顺序不能出错
	 * @param 当修改后可以通过set方法调整
	 */
	public ButtonUtil(String... info) {
		if (info.length != 0) {
			if (info.length > 0 && info[0] != null && !info[0].equals(""))
				this.buttonName = info[0];
			if (info.length > 1 && info[1] != null && !info[1].equals(""))
				this.buttonUrl = FileUtil.getHomePath() + info[1];
			if (info.length > 2 && info[2] != null && !info[2].equals(""))
				this.formId = info[2];
			if (info.length > 3 && info[3] != null && !info[3].equals(""))
				this.flag = Boolean.parseBoolean(info[4]);
			else
				this.flag = true;
			if (info.length > 4 && info[4] != null && !info[4].equals(""))
				this.valueName = info[4];
			else
				this.valueName = info[0];// 默认使用按钮名
			if (info.length > 5 && info[5] != null && !info[5].equals(""))
				this.type = info[5];
			else
				this.type = "POST";// 默认POST提交
			if (info.length > 6 && info[6] != null && !info[6].equals(""))
				this.dataType = info[6];
			else
				this.dataType = "JSON";// 默认返回JSON提交
		}
	}

}
