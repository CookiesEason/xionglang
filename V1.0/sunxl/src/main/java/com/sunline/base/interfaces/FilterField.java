package com.sunline.base.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年8月3日 
 * @此类作用：JSON过滤的字段值防止出现JSON死循环注册的值为一个String数组，
 * 内容为需要显示的对象属性值，值的样式为查询对象_需要显示的对象值，如：查询AdminUser
 * AdminUser里面有Role，Role里面有AdminUser，出现了死循环，我们查询的时候
 * 需要断开它回调AdminUser写法
 * role_adminUsers
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FilterField {
	String[] value() default {};
}
