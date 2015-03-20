package com.wbh.mymvc.context;

public class TestWebContextInitializer implements WebContextInitializer<ConfigurableWebContext> {

	@Override
	public void initWebContext(ConfigurableWebContext cwc) {
		System.out.println(cwc.toString()+"ttttttttttttttttttttttttttttt");
	}

}
