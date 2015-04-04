package com.wbh.mymvc.interceptor;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.MyInterceptor;
import com.wbh.mymvc.annotation.Param;
import com.wbh.mymvc.annotation.RequestEntity;
import com.wbh.mymvc.dataobj.Parameter;
import com.wbh.mymvc.servlet.Handler;
import com.wbh.mymvc.ui.Model;
import com.wbh.mymvc.ui.RequestResult;
import com.wbh.mymvc.util.MethodParameterUtil;
import com.wbh.mymvc.util.RequestParameterUtil;

/**
 * 
 * @author wbh
 * 
 */
@Bean
@MyInterceptor(index = -99)
public class FillParameterInterceptor extends InterceptorAdapt {

	public static String REQUESTRESULT = "requestResult";

	@Override
	public boolean beforeHandler(HttpServletRequest request,
			HttpServletResponse response, Handler h) throws Exception {

		System.out
				.println("===============ParameterProcessorInterceptor:beforeHandler=================");
		Method m = h.getMappingMethod();

		h.setParameters(MethodParameterUtil.getMethodParameters(m));

		initParameter(request, response, h);

		fillCustomParameter(request, h);

		return true;
	}

	private void initParameter(HttpServletRequest request,
			HttpServletResponse response, Handler h) throws Exception {

		for (Parameter p : h.getParameters()) {

			if (HttpServletRequest.class
					.isAssignableFrom(p.getParameterClass())) {
				p.setParameterValue(request);
			} else if (HttpServletResponse.class.isAssignableFrom(p
					.getParameterClass())) {
				p.setParameterValue(response);
			} else if (Model.class.isAssignableFrom(p.getParameterClass())) {
				if (null != request.getAttribute(REQUESTRESULT)) {
					p.setParameterValue(request.getAttribute(REQUESTRESULT));
				} else {
					p.setParameterValue(new RequestResult());
				}
			} else {
				p.setParameterValue(p.getParameterClass().newInstance());
			}

		}
	}

	private void fillCustomParameter(HttpServletRequest request, Handler h) {

		for (Parameter p : h.getParameters()) {
			if (p.isAnnotationPresent(RequestEntity.class)) {
				RequestParameterUtil.parameterToEntity(
						request.getParameterMap(),
						h.getParameters()[p.getParameterIndex()]);
			} else if (p.isAnnotationPresent(Param.class)) {
				h.getParameters()[p.getParameterIndex()]
						.setParameterValue(request.getParameter(((Param) p
								.getAnnotation(Param.class)).value()));
			}
		}
	}

	@Override
	public void afterHandler(HttpServletRequest request,
			HttpServletResponse response, RequestResult requestResult)
			throws Exception {
		// request.setAttribute(MODEL, requestResult.getModel());
	}
}