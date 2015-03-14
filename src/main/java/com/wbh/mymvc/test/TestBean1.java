package com.wbh.mymvc.test;

import com.wbh.mymvc.annotation.Injection;

public class TestBean1 implements Test
{
	@Injection
	public TestBean2 testbean2;
	
	public TestBean1() {
		System.out.println("----------------TestBean1------------------");
	}
	
	@Override
	public void TestGet() {
		System.out.println("----------------TestGetBeanByAnnotation1------------------");
		
	}

}
