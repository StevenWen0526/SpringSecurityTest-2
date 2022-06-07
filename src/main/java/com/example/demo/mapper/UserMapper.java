package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dao.Role;
import com.example.demo.dao.Users;
@Mapper
public interface UserMapper {

	Users loadUserByUsername(String username);

	List<Role> getUserRolesByUid(Integer id);

}
