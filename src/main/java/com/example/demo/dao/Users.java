package com.example.demo.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

//@Data
public class Users implements UserDetails{
	private static final long serialVersionUID = 6328519048496665469L;
	private Integer id;
	private String username;
	private String password;
	
	private Boolean enabled;
	private Boolean locked;
	private List<Role> roles;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities =new ArrayList<>();
		for(Role role:roles ) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
//
//	public Boolean getEnabled() {
//		return enabled;
//	}
//
//	public void setEnabled(Boolean enabled) {
//		this.enabled = enabled;
//	}
//
//	public Boolean getLocked() {
//		return locked;
//	}
//
//	public void setLocked(Boolean locked) {
//		this.locked = locked;
//	}
//
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

}
