package com.tesla.crowdfunding.component;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.tesla.crowdfunding.bean.TAdmin;

public class TSecurityAdmin extends User {

	TAdmin admin;

	public TSecurityAdmin(TAdmin admin, Set<GrantedAuthority> authorities) {
		// super(admin.getLoginacct(), admin.getUserpswd(), authorities);
		super(admin.getLoginacct(), admin.getUserpswd(), true, true, true, true, authorities);// 7
		this.admin = admin;
	}
}
