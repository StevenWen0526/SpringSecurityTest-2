package com.example.demo.filter;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.example.demo.dao.Menu;
import com.example.demo.dao.Role;
import com.example.demo.mapper.MenuMapper;
/**
 * 實現動態配置權限
 * @author StevenWen
 * @since  2022年6月7日
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource 
implements FilterInvocationSecurityMetadataSource{
	//實現ant風格 url匹配
	AntPathMatcher antPathMatcher=new AntPathMatcher();
	@Autowired
	MenuMapper menuMapper;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String requestUrl=((FilterInvocation)object).getRequestUrl();//當前url
		List<Menu> allMenu=menuMapper.getAllMenus();
		for(Menu menu :allMenu ) {
			if(antPathMatcher.match(menu.getPattern(), requestUrl)) {
				List<Role> roles=menu.getRoles();
				String[] roleArr=new String[roles.size()];
				for(int i=0;i<roleArr.length;i++) {
					roleArr[i]=roles.get(i).getName();
				}
				return SecurityConfig.createList("ROLE_LOGIN");
			}
		}
		return null;
	}
	/**
	 * 返回所有定義好的權限資源
	 * Spring Security 啟動時，會校驗相關配置是否正確
	 * 不需要校驗直接返回null即可
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 返回類對象是否支持校驗
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
