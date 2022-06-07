package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.Users;
import com.example.demo.mapper.UserMapper;
@Service
public class UserService  implements UserDetailsService{
	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users=userMapper.loadUserByUsername(username);
		if(users==null) {
			throw new UsernameNotFoundException("帳號不存在");
		}
		users.setRoles(userMapper.getUserRolesByUid(users.getId()));
		return users;
	}

}
