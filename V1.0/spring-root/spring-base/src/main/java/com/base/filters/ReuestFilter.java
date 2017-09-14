package com.base.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.form.FilterInfoForm;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年7月6日
 * @此类作用：过滤器解密url和其参数
 */
public class ReuestFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(ReuestFilter.class);
	private static Map<Thread, FilterInfoForm> serverInfo = new HashMap<Thread, FilterInfoForm>();

	@Override
	protected void doFilterInternal(final HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			serverInfo.put(Thread.currentThread(), new FilterInfoForm(request, response, System.currentTimeMillis()));
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
	}

	public static FilterInfoForm getServerInfo() {
		return serverInfo.get(Thread.currentThread());
	}
}
