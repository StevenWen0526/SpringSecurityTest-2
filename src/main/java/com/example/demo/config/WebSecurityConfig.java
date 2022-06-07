package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//prePostEnabled = true 解鎖@preAuthorize @postAuthorize
//securedEnabled=true 解鎖@Secured註解
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.service.UserService;
@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserService userService;
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
//		super.configure(auth);
		auth.userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
//		super.configure(http);
		http.authorizeRequests()
		.antMatchers("/admin/**").hasAnyRole("admin")
		.antMatchers("/db/**").hasAnyRole("dba")
		.antMatchers("/user/**").hasAnyRole("user")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginProcessingUrl("/login").permitAll()
		.and()
		.csrf().disable();
	}
	
	
}
