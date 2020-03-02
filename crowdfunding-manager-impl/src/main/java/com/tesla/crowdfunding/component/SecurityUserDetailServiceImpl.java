package com.tesla.crowdfunding.component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tesla.crowdfunding.bean.TAdmin;
import com.tesla.crowdfunding.bean.TAdminExample;
import com.tesla.crowdfunding.bean.TPermission;
import com.tesla.crowdfunding.bean.TRole;
import com.tesla.crowdfunding.mapper.TAdminMapper;
import com.tesla.crowdfunding.mapper.TPermissionMapper;
import com.tesla.crowdfunding.mapper.TRoleMapper;

@Component
public class SecurityUserDetailServiceImpl implements UserDetailsService {
	@Autowired
	TAdminMapper adminMapper;

	@Autowired
	TRoleMapper roleMapper;

	@Autowired
	TPermissionMapper permissionMapper;

	Logger log = LoggerFactory.getLogger(SecurityUserDetailServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 1查询用户对象
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(username);
		List<TAdmin> list = adminMapper.selectByExample(example);

		if (list != null && list.size() == 1) {
			TAdmin admin = list.get(0);
			Integer adminId = admin.getId();

			log.debug("用户信息:{}", admin);
			// 2查询角色集合
			List<TRole> roleList = roleMapper.listRoleByAdminId(adminId);

			log.debug("用户角色集合:{}", roleList);

			// 3.查询权限集合
			List<TPermission> permissionList = permissionMapper.listPermissionByAdminId(adminId);

			log.debug("用户权限集合:{}", permissionList);

			// 4.构建用户所有权限集合：ROLE_角色+权限
			Set<GrantedAuthority> authorities = new HashSet<>();
			for (TRole role : roleList) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));// 框架固定写法
			}

			for (TPermission permission : permissionList) {
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
			log.debug("用户权限总集合:{}", authorities);
			// return new User(admin.getLoginacct(), admin.getUserpswd(), authorities);
			return new TSecurityAdmin(admin, authorities);
		} else {
			return null;
		}

	}

}
