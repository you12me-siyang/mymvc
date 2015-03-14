package com.wbh.mymvc.test;

import com.wbh.mymvc.annotation.Bean;

@Bean
public class TestBean2 implements Test {
	
	public TestBean2() {
		System.out.println("----------------TestBean2------------------");
	}

	@Override
	public void TestGet() {
		System.out.println("---------------------TestGetBeanByAnnotation2---------------");
		
	}

}
