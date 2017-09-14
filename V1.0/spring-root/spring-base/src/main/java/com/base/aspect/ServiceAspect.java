package com.base.aspect;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.ExceptionInfoDao;
import com.base.filters.ReuestFilter;
import com.base.form.ClientInfoForm;
import com.base.generic.dao.impl.GenericDaoImpl;
import com.base.one.entity.AdminUser;
import com.base.one.entity.User;
import com.base.two.entity.ExceptionInfo;
import com.base.util.WebKeys;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月26日
 * @此类作用：AOP 切面顺序 @Around>@Before>切面方法>@Around>@After>(@AfterThrowing
 *           和 @AfterReturning)当前面的方法出现异常时只会执行@AfterThrowing，当没有抛异常则执行@AfterReturning，可以同时用一个或多个
 */

@Aspect
@Service
public class ServiceAspect {
	private final static Logger logger = LoggerFactory.getLogger(ServiceAspect.class);
	@Autowired
	private ExceptionInfoDao exceptionInfoDao;
	/* @Value("${sunxl.aspect.point}") */
	private final String POINT_CUT = "execution(* com.sunxl.*.service.*.*(..))";

	/*
	 * @Pointcut(POINT_CUT) public void pointcut() {
	 * 
	 * }
	 * 
	 * @Around(POINT_CUT) public void aroundAspect() {
	 * 
	 * }
	 * 
	 * 
	 * @After(POINT_CUT) public void afterAspect(JoinPoint point) { try {
	 * GenericDaoImpl.commitTransaction(Thread.currentThread()); } finally {
	 * GenericDaoImpl.closeEntityManager(Thread.currentThread()); } }
	 */
	/**
	 * @param point
	 * @此方法的作用：添加指定的缓存
	 */
	/*
	 * @Before(POINT_CUT) public void beforeAspect(JoinPoint joinPoint) {
	 * 
	 * }
	 */

	/**
	 * @param e
	 * @此方法的作用：当servic方法抛异常，则回滚开启的事物，之后关闭所有的连接，记录日志
	 */
	@AfterThrowing(value = POINT_CUT, throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Throwable e) {
		try {
			GenericDaoImpl.rollBack(Thread.currentThread());
		} finally {
			GenericDaoImpl.closeEntityManager(Thread.currentThread());
		}
		insertException(joinPoint, e);
	}

	/**
	 * @param obj
	 * @此方法的作用：没有报错，有正常返回值，则提交开启的事物,之后关闭连接(没有返回值，即void方法，也是正常返回,也会执行该方法)
	 */
	@AfterReturning(value = POINT_CUT, returning = "obj")
	public void afterReturning(Object obj) {
		try {
			GenericDaoImpl.commitTransaction(Thread.currentThread());
		} finally {
			GenericDaoImpl.closeEntityManager(Thread.currentThread());
		}
	}

	/**
	 * @param joinPoint
	 * @param e
	 * @此方法的作用：出现异常，添加日志
	 */
	private void insertException(JoinPoint joinPoint, Throwable e) {
		try {
			logger.error(e + "");
			ExceptionInfo info = new ExceptionInfo();
			Signature joinInfo = joinPoint.getSignature();
			HttpSession session = ReuestFilter.getServerInfo().getRequest().getSession();
			if (session.getAttribute(WebKeys.USER_KEY) != null)
				info.setUserCode(((User) session.getAttribute(WebKeys.USER_KEY)).getUserName());
			if (session
					
					.getAttribute(WebKeys.ADMINUSER_KEY) != null)
				info.setUserCode(((AdminUser) session.getAttribute(WebKeys.ADMINUSER_KEY)).getUserName());
			info.setClassName(joinInfo.getDeclaringTypeName() + "." + joinInfo.getName());
			info.setMethodName(joinInfo.getName());
			ClientInfoForm clientInfo = new ClientInfoForm(ReuestFilter.getServerInfo().getRequest());
			info.setIp(clientInfo.getIP());
			info.setUrlPath(clientInfo.getUrl());
			info.setAgent(clientInfo.getAgent());
			info.setInfo(e + "");
			info.setCreateTime(new Timestamp(System.currentTimeMillis()));
			exceptionInfoDao.create(info);
			GenericDaoImpl.commitTransaction(Thread.currentThread());
		} finally {
			GenericDaoImpl.closeEntityManager(Thread.currentThread());
		}

	}
}
