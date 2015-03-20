package com.wbh.mymvc.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.MyController;
import com.wbh.mymvc.annotation.MyRequestMapping;
import com.wbh.mymvc.ui.Model;
import com.wbh.mymvc.ui.MyModelAndView;

@Bean
@MyController
public class TestController {

	@MyRequestMapping(value = "/test",method = "GET")
	public MyModelAndView test(Model model,HttpServletRequest req,HttpServletResponse resp ){
		System.out.println("=============invoke method:test()=================");
		model.put("msg", "555555555555");
		return new MyModelAndView("testview",model);
	}
	
	@MyRequestMapping(value = "/login",method = "GET")
	public MyModelAndView login(Model model,HttpServletRequest req,HttpServletResponse resp){
		System.out.println("=============invoke method:login()=================");
		return null;
	}
}
