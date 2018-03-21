package com.ktds.community1.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ktds.member.constants.Member;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object controller)
			throws Exception {
		
		//context루트가 있으면 써주는게 좋다
		
		String contextPath = request.getContextPath();
		
		if( request.getSession().getAttribute(Member.USER) == null ) {
//			logger.info(request.getRequestURL()+"안돼, 돌아가.");
			System.out.println(request.getRequestURL()+"안돼, 돌아가.");
			response.sendRedirect(contextPath + "/login"); 
			return false;
		}		
		
		//궁극적으로 controller에게 요청을 하려고 하는건데 return이 false면 controller에게
		//요청을 하지 않음.. 그대로 browser에게 간다.
		return true;
	}

}
