package com.sunxl.base.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年7月6日
 * @此类作用：过滤器解密url和其参数
 */
public class ReuestFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(ReuestFilter.class);

	@Override
	protected void doFilterInternal(final HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
		}
	}

}
