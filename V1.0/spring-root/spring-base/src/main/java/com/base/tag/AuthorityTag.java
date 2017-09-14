package com.base.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
@SuppressWarnings({ "static-access", "serial" })
public class AuthorityTag extends TagSupport {

	private String opeUrl;
	private String opeCode;

	@Override
	public int doStartTag() throws JspException {
		if ("".equals(this.getOpeCode())) {
			return this.EVAL_BODY_INCLUDE;
		}
		return this.EVAL_BODY_INCLUDE;
	}

	public String getOpeUrl() {
		return opeUrl;
	}

	public void setOpeUrl(String opeUrl) {
		this.opeUrl = opeUrl;
	}

	public String getOpeCode() {
		return opeCode;
	}

	public void setOpeCode(String opeCode) {
		this.opeCode = opeCode;
	}
}
