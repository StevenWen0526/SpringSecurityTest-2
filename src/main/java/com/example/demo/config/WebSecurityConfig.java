package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//prePostEnabled = true 解鎖@preAuthorize @postAuthorize
//securedEnabled=true 解鎖@Secured註解
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.example.demo.filter.CustomFilterInvocationSecurityMetadataSource;
import com.example.demo.filter.CustomerAccessDecisionManager;
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
	
	@Bean
	RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy=new RoleHierarchyImpl();
		String hierarchy="ROLE_dba >ROLE_admin ROLE_admin > ROLE_user";
		roleHierarchy.setHierarchy(hierarchy);
		return roleHierarchy;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
//		super.configure(auth);
		auth.userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.antMatchers("/admin/**").hasAnyRole("admin")
//		.antMatchers("/db/**").hasAnyRole("dba")
//		.antMatchers("/user/**").hasAnyRole("user")
//		.anyRequest().authenticated()
//		.and()
//		.formLogin()
//		.loginProcessingUrl("/login").permitAll()
//		.and()
//		.csrf().disable();
		http.authorizeRequests()
		.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor> () {

			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O object) {
				object.setSecurityMetadataSource(cfisms());
				object.setAccessDecisionManager(cadm());
				return object;
			}
			
		}).and()
		.formLogin()
		.loginProcessingUrl("/login").permitAll()
		.and()
		.csrf().disable();
		
	}
	@Bean
	CustomFilterInvocationSecurityMetadataSource cfisms() {
		return new CustomFilterInvocationSecurityMetadataSource();
	}
	@Bean
	CustomerAccessDecisionManager cadm() {
		return new CustomerAccessDecisionManager();
	}
	
	
}
