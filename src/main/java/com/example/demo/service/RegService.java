package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegService {
//	public int reg(String username,String password) {
//		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
//		String encodePassword=encoder.encode(password);
//		return saveToDb(username,password);
//	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//root 1234
		//admin 12345
		//steven 123
		String password="123";
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodePassword=encoder.encode(password);
		System.out.println(password);
		System.out.println(encodePassword);
	}

}
