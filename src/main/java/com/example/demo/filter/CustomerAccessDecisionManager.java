package com.example.demo.filter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
/**
 * 當CustomFilterInvocationSecurityMetadataSource  中的getAttribttes方法後
 * 接下會來到請求完會跑這裡
 * @author StevenWen
 * @since  2022年6月7日
 */
@Component
public class CustomerAccessDecisionManager implements AccessDecisionManager{
	/**
	 * 1.當前登入用戶訊息
	 * 2.參數是一個FilterInvoication 對象 ，可以獲得當前對象
	 * 3.CustomFilterInvocationSecurityMetadataSource  中的getAttribttes 返回值，當前請求url所需要的角色
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		Collection<? extends GrantedAuthority> auths=authentication.getAuthorities();
		for(ConfigAttribute configAttribute :configAttributes) {
			//如是ROLE_LOGIN 登入就可訪問
			if("ROLE_LOGIN".equals(configAttribute.getAttribute()) && authentication instanceof UsernamePasswordAuthenticationToken) {
				return;
			}
			for(GrantedAuthority authority :auths) {
				//判斷當前登入是否具有請求url所需要的角色訊息
				if(configAttribute.getAttribute().equals(authority.getAuthority())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("權限不足");
		
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
