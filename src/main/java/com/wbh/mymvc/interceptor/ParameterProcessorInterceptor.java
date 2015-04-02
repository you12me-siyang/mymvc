package com.wbh.mymvc.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.MyInterceptor;
import com.wbh.mymvc.annotation.Parameter;
import com.wbh.mymvc.annotation.RequestEntity;
import com.wbh.mymvc.servlet.Handler;
import com.wbh.mymvc.ui.Model;
import com.wbh.mymvc.ui.RequestResult;
import com.wbh.mymvc.util.RequestParameterUtil;

/**
 * 对requestmapping参数进行操作
 * 
 * @author wbh
 * 
 */
@Bean
@MyInterceptor(index = -1)
public class ParameterProcessorInterceptor extends InterceptorAdapt {

	public static String MODEL = "model";

	@Override
	public boolean beforeHandler(HttpServletRequest request,
			HttpServletResponse response, Handler h) throws Exception {
		
		System.out.println("===============ParameterProcessorInterceptor:beforeHandler=================");
		
		Method m = h.getMappingMethod();
		Class<?>[] cs = m.getParameterTypes();
		
		h.setMethodParameter(new Object[cs.length]);

		// 如果handler参数中包含request,response,model，便对这三个参数赋值，并将所有其他参数先置null
		for (int i = 0; i < cs.length; i++) {
			if (cs[i].equals(HttpServletRequest.class)) {
				h.getMethodParameter()[i] = request;
			} else if (cs[i].equals(HttpServletResponse.class)) {
				h.getMethodParameter()[i] = response;
			} else if (cs[i].equals(Model.class)) {
				if(null != request.getAttribute(MODEL)){
					h.getMethodParameter()[i] = request.getAttribute(MODEL);
				}else{
					h.getMethodParameter()[i] = new Model();
				}
			} else {
				h.getMethodParameter()[i] = new Object();
			}
		}

		doProcess(request, h);

		return super.beforeHandler(request, response, h);
	}
	@Override
	public void afterHandler(HttpServletRequest request,
			HttpServletResponse response, RequestResult requestResult)
			throws Exception {
		request.setAttribute(MODEL, requestResult.getModel());
		super.afterHandler(request, response, requestResult);
	}

	public void doProcess(HttpServletRequest request, Handler h) {
		
		Annotation[][] as = h.getMappingMethod().getParameterAnnotations();

		for (int i = 0; i < as.length; i++) {
			for (int j = 0; j < as[i].length; j++) {
				System.out.println(as[i][j].getClass().getName()+"====="+RequestEntity.class.getName());
				if (as[i][j] instanceof RequestEntity) {
					Class<?> c = h.getMappingMethod().getParameterTypes()[i];
					try {
						h.getMethodParameter()[i] = 
								RequestParameterUtil.parameterToEntity(
										request.getParameterMap(),
										c.newInstance());
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}else if(as[i][j] instanceof Parameter){
					h.getMethodParameter()[i] =request.getParameter(((Parameter)as[i][j]).value());
				}
			}
		}
	}
}