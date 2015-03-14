package com.wbh.mymvc.test;


public class TestBean {
	
	public TestBean1 testbean1;
	
	private String testconfigured;
	
	public TestBean() {
		System.out.println("----------------TestBean------------------");
	}

	public void initMethod(){
		System.out.println("----------------TestInitMethod------------------");
	}
	
	public void printTestconfigured(){
		System.out.println(testconfigured+"@@@@@@@@@@@@@@@@@@@@@@@2");
	}
}
