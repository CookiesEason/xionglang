package com.base.service;

import java.util.Map;

import com.base.form.Token;
import com.base.generic.service.GenericService;
import com.base.one.entity.AdminUser;
import com.base.one.entity.Role;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
public interface AdminUserService extends GenericService<AdminUser> {

	Map<String, int[]> initRoleMapped();

	AdminUser adminLogin(AdminUser adminUser);

	Token createLoginToken(Token token, Role role);
}
