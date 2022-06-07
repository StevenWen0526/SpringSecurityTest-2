package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestComtroller {

	@GetMapping("hello")
	public String hello() {
		return "hello";
	}
	@ResponseBody
	@GetMapping("/admin/hello")
	public String admin() {
		return "admin hello";
	}
	@ResponseBody
	@GetMapping("/user/hello")
	public String user() {
		return "user hello";
	}
	@ResponseBody
	@GetMapping("/db/hello")
	public String db() {
		return "db hello";
	}
}
