package com.wbh.mymvc.validator;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.validator.NotBlank;
import com.wbh.mymvc.dataobj.ElementValue;

@Bean
public class NotBlankValidator extends ValidatorAdapt {

	@Override
	public boolean validate(ElementValue ev) {

		if (!ev.isAnnotationPresent(NotBlank.class))
			return true;

		if (("").equals((String) ev.getValue())) {
			logger.info("参数" + ev.getElementName() + "不能为空");
			return false;
		}

		return true;
	}
}
