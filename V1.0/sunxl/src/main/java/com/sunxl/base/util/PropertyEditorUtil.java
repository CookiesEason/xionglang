package com.sunxl.base.util;

import java.beans.PropertyEditor;

import org.springframework.beans.PropertyEditorRegistrySupport;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：
 */
public class PropertyEditorUtil extends PropertyEditorRegistrySupport {

	private static PropertyEditorUtil mySelf;

	@Override
	protected void registerDefaultEditors() {
		super.registerDefaultEditors();
	}

	public static PropertyEditorUtil getInstance() {
		if (mySelf == null) {
			mySelf = new PropertyEditorUtil();
		}
		return mySelf;
	}

	private PropertyEditorUtil() {
		this.registerDefaultEditors();
	}

	/**
	 * 常用 基本类型 传换 八大 基本类型， 集合 ，数组 ，流，大数类。
	 * 
	 * @param obj
	 * @param tagetType
	 * @return
	 */
	public Object turnValue(Object obj, Class<?> tagetType) {
		if (obj == null) {
			return obj;
		}
		if (tagetType.equals(String.class)) {
			return String.valueOf(obj);
		}
		PropertyEditor editor = mySelf.getDefaultEditor(tagetType);

		if (obj instanceof CharSequence) {
			editor.setAsText(String.valueOf(obj));
		} else {
			editor.setValue(obj);
		}
		return editor.getValue();
	}

}
