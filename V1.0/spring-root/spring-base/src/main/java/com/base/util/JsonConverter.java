package com.base.util;

import com.base.util.SimpleJsonFormat.JsonWrite;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：
 */
public interface JsonConverter {
	void convert(Object obj, JsonWrite jw);
}
