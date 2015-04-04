package com.wbh.mymvc.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.MyController;
import com.wbh.mymvc.annotation.MyRequestMapping;
import com.wbh.mymvc.annotation.RequestEntity;
import com.wbh.mymvc.test.User;
import com.wbh.mymvc.ui.Model;
import com.wbh.mymvc.ui.RequestResult;

@Bean
@MyController
public class TestController {

	@MyRequestMapping(value = "/test",method = "GET")
	public RequestResult test(Model model,HttpServletRequest req,HttpServletResponse resp ){
		System.out.println("=============invoke method:test()=================");
		model.put("msg", "555555555555");
		RequestResult rr = new RequestResult();
		rr.setView("testview");
		rr.setModel(model);
		return rr;
	}
	
	@MyRequestMapping(value = "/login",method = "POST")
	public RequestResult login(@RequestEntity User u,HttpServletRequest req,HttpServletResponse resp){
		System.out.println(u.getUsername()+"----"+u.getPassword());
		return null;
	}
}
