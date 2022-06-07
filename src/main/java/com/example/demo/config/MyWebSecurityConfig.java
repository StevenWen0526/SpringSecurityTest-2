package com.example.demo.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
/**
 * 自定義使用者
 * @author StevenWen
 * @since  2022年6月5日
 */
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
//@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Bean
	PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder(10);//默認10 密鑰匙傳遞次數
	}
//	@Bean
//	public PasswordEncoder getPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		//自定義帳號及角色
		.withUser("root").password("123").roles("ADMIN","DBA")
		.and()
		.withUser("admin").password("123").roles("ADMIN","USER")
		.and()
		.withUser("steven").password("123").roles("USER");
//		super.configure(auth);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin/**")
		.hasRole("ADMIN")
		.antMatchers("/user/**")
		.access("hasAnyRole('ADMIN','USER')")
		.antMatchers("/db/**")
		.access("hasRole('ADMIN') and hasRole('DBA')")
		//其他都需要登入認證
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		//自定義的頁面
		.loginPage("/login_page")
		.loginProcessingUrl("/login")
		//自定義認證所需要的用戶名和密碼
		.usernameParameter("name")
		.passwordParameter("passwd")
		//成功頁面處理
		.successHandler(new AuthenticationSuccessHandler() {
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				//Authentication authentication 當前登陸的訊息
				//登陸成功返回json
				Object principal=authentication.getPrincipal();
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out=response.getWriter();
				response.setStatus(200);
				Map<String, Object>map=new HashMap<>();
				map.put("status", 200);
				map.put("msg",principal);
				ObjectMapper oMapper=new ObjectMapper();
				out.write(oMapper.writeValueAsString(map));
				out.flush();
				out.close();
			}
			//登陸失敗
		}).failureHandler(new AuthenticationFailureHandler() {
			
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				//AuthenticationException exception 登陸失敗原因
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out=response.getWriter();
				response.setStatus(401);
				Map<String,Object> map=new HashMap<>();
				map.put("status", 401);
				if(exception instanceof LockedException) {
					map.put("msg", "帳戶被鎖，登入失敗");
				}else if (exception instanceof BadCredentialsException) {
					map.put("msg", "帳戶名或密碼輸入錯誤，登入失敗");
				}else if (exception instanceof DisabledException) {
					map.put("msg", "帳戶被禁用，登入失敗");
				}else if (exception instanceof AccountExpiredException) {
					map.put("msg", "帳戶已過期，登入失敗");
				}else if (exception instanceof CredentialsExpiredException) {
					map.put("msg", "密碼已過期，登入失敗");
				}else {
					map.put("msg", "登入失敗");
				}
				ObjectMapper om=new ObjectMapper();
				out.write(om.writeValueAsString(map));
				out.flush();
				out.close();
			}
		})
		.permitAll()
		.and()
		//關閉CSRF
		.csrf()
		.disable();
//		super.configure(http);
		
		
		//登出
		http.logout()
		.logoutUrl("/logout")
		//是否清除身分認證訊息 默認true 表示清除
		.clearAuthentication(true)
		//是否使session失敗，默認true
		.invalidateHttpSession(true)
		.addLogoutHandler(new LogoutHandler() {
			
			@Override
			public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			{
				
			}
		}).logoutSuccessHandler(new LogoutSuccessHandler() {
			
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
					throws IOException, ServletException {
				//跳轉登入頁面
				response.sendRedirect("/login_page");
			}
		});
	}
	
	
	
	
}
