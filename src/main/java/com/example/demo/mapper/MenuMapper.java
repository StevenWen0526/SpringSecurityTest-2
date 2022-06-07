package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dao.Menu;
@Mapper
public interface MenuMapper {

	public List<Menu> getAllMenus();

}
