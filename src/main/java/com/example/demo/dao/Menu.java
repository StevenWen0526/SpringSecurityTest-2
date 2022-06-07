package com.example.demo.dao;

import java.util.List;

import lombok.Data;

@Data
public class Menu {
	private Integer id;
	private String pattern;
	private List<Role> roles;
}
