package com.wbh.mymvc.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.MyController;
import com.wbh.mymvc.annotation.MyRequestMapping;
import com.wbh.mymvc.annotation.MyRequestMethod;
import com.wbh.mymvc.ui.Model;
import com.wbh.mymvc.ui.MyModelAndView;

@MyController
public class TestController {

	@MyRequestMapping(value = "/test",method = MyRequestMethod.GET)
	public MyModelAndView test(Model model,HttpServletRequest req,HttpServletResponse resp ){
		System.out.println("=============invoke method:test()=================");
		req.setAttribute("msg", "5555555555555555555555");
		return new MyModelAndView("testview",model);
	}
	
	@MyRequestMapping(value = "/login",method = MyRequestMethod.GET)
	public MyModelAndView login(Model model){
		System.out.println("=============invoke method:login()=================");
		return null;
	}
}
