package com.tesla.crowdfunding.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CrowdFundingSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	// 后来
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// super.configure(auth);
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// super.configure(http);
		http.authorizeRequests().antMatchers("/static/**", "/welcome.jsp", "/toLogin").permitAll().anyRequest()
				.authenticated();// 剩下都需要认证

		// /login.jsp==POST 用户登陆请求发给Security
		http.formLogin().loginPage("/toLogin").usernameParameter("loginacct").passwordParameter("userpswd")
				.loginProcessingUrl("/login").defaultSuccessUrl("/main");

		http.csrf().disable();

		http.logout().logoutSuccessUrl("/index");

		http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {

			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {

				// 请求头里面会带着异步请求信息：X-Requested-With:XMLHttpRequest
				String type = request.getHeader("X-Requested-With");
				if ("XMLHttpRequest".equals(type)) {
					response.getWriter().print("403");
				} else {
					request.getRequestDispatcher("/WEB-INF/jsp/error/error403.jsp").forward(request, response);
				}
				// response.getWriter().print("403");// 拒绝访问，权限不够

			}

		});

		http.rememberMe();
	}
}
