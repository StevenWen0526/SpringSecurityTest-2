package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class MultiHttpSecurityConfig {
	
//	@Bean
//	PasswordEncoder passwordEncoder() {
////		return NoOpPasswordEncoder.getInstance();
//		return new BCryptPasswordEncoder(10);
//	}
//	@Autowired
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		//自定義帳號及角色
//		.withUser("admin").password("123").roles("ADMIN","USER")
//		.and()
//		.withUser("steven").password("123").roles("USER");
////		super.configure(auth);
//	}
//	@Configuration
//	@Order(1) //執行優先順序
//	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter{
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http.antMatcher("/admin/**")
//			.authorizeHttpRequests()
//			.anyRequest()
//			.hasRole("ADMIN");
//		}
//		
//	}
	
//	@Configuration
//	public static class OtherSecurityConfig extends WebSecurityConfigurerAdapter{
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http.authorizeHttpRequests()
//			.anyRequest().authenticated()
//			.and()
//			.formLogin()
//			.loginProcessingUrl("/login")
//			.permitAll()
//			.and()
//			.csrf()
//			.disable();
//		}
//		
//	}
}
