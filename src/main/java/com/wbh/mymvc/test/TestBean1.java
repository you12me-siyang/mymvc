package com.wbh.mymvc.test;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.Injection;

@Bean
public class TestBean1 
{
	@Injection
	public TestBean2 testbean2;
	
	public TestBean1() {
		System.out.println("----------------TestBean1------------------");
	}

}
