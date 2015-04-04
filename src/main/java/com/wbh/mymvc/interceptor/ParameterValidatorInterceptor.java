package com.wbh.mymvc.interceptor;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.MyInterceptor;
import com.wbh.mymvc.annotation.Param;
import com.wbh.mymvc.annotation.validator.Validator;
import com.wbh.mymvc.context.DefaultWebContext;
import com.wbh.mymvc.context.WebContext;
import com.wbh.mymvc.dataobj.ElementValue;
import com.wbh.mymvc.dataobj.FieldBody;
import com.wbh.mymvc.dataobj.Parameter;
import com.wbh.mymvc.servlet.Handler;
import com.wbh.mymvc.servlet.MyDispatcherServlet;
import com.wbh.mymvc.validator.BaseValidator;

@Bean
@MyInterceptor(index = -98)
public class ParameterValidatorInterceptor extends PreProcessor implements
		ParameterValidator {
	
	@Override
	protected boolean doProcess(HttpServletRequest request, Handler h) {
		
		WebContext wc = (DefaultWebContext) request.getServletContext().getAttribute(
				MyDispatcherServlet.WEBCONTEXT);
		
		List<Object>  vs= wc.getBeanByClass(BaseValidator.class);

		for (Parameter p : h.getParameters()) {
			if (p.isAnnotationPresent(Validator.class)) {
				
				Field[] fs = p.getParameterClass().getDeclaredFields();
				for (Field f : fs) {
					if (!doValidate(
							vs,
							new FieldBody(f, p.getParameterValue()))) {
						return false;
					}
				}
			} else if (p.isAnnotationPresent(Param.class)) {
				if (!doValidate(
						vs,
						p)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean doValidate(List<Object> vs, ElementValue ev) {
		for(Object o:vs){
			if(!((BaseValidator)o).validate(ev)){
				return false;
			}
		}
		return true;
	}

}
