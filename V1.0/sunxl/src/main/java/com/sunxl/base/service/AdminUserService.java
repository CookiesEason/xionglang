package com.sunxl.base.service;

import java.util.Map;

import com.sunxl.base.entity.AdminUser;
import com.sunxl.base.entity.Role;
import com.sunxl.base.form.Token;
import com.sunxl.base.generic.service.GenericService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
public interface AdminUserService extends GenericService<AdminUser> {

	Map<String, int[]> initRoleMapped();

	AdminUser adminLogin(AdminUser adminUser);

	Token createLoginToken(Token token, Role role);

	AdminUser update(AdminUser adminUser);
}
