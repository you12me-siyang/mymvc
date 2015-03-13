package com.wbh.mymvc.test;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.InitMethod;
import com.wbh.mymvc.annotation.Injection;

@Bean(isLazyInit=false,isNeedCache=true)
public class TestBean {
	
	@Injection
	public TestBean1 testbean1;
	
	public TestBean() {
		System.out.println("----------------TestBean------------------");
	}

	@InitMethod
	public void initMethod(){
		System.out.println("----------------TestInitMethod------------------");
	}
}
