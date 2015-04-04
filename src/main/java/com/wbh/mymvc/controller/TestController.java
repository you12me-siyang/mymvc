package com.wbh.mymvc.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.MyController;
import com.wbh.mymvc.annotation.MyRequestMapping;
import com.wbh.mymvc.annotation.Param;
import com.wbh.mymvc.annotation.validator.InLengthRange;
import com.wbh.mymvc.annotation.validator.NotBlank;
import com.wbh.mymvc.ui.RequestResult;

@Bean
@MyController
public class TestController {

	@MyRequestMapping(value = "/test",method = "GET")
	public RequestResult test(RequestResult requestResult,HttpServletRequest req,HttpServletResponse resp ){
		System.out.println("=============invoke method:test()=================");
		requestResult.addObject("msg", "555555555555");
		requestResult.setView("testview");
		return requestResult;
	}
	
	@MyRequestMapping(value = "/login",method = "POST")
	public RequestResult login(@Param(value = "username") @NotBlank @InLengthRange(minLengh = 1,maxLengh = 5) String name,HttpServletRequest req,HttpServletResponse resp){
		return null;
	}

}
