package com.wbh.easyjob4trainee.interceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wbh
 * 对/member/**的请求进行登录校验
 */
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wbh.easyjob4trainee.entity.Member;
import com.wbh.easyjob4trainee.util.Constants;

public class ConciergeInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession s = request.getSession();

		Member m = (Member) s.getAttribute(Constants.CURRENT_MEMBER);

		if (null == m) {
			response.sendRedirect("/easyjob4trainee/redirect");
		} else if (m.getComplete() == 0) {
			response.sendRedirect("/easyjob4trainee/completeinfo");
		}
		return true;
	}

}
