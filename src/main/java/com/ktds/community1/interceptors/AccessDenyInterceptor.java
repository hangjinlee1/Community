package com.ktds.community1.interceptors;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ktds.member.constants.Member;

public class AccessDenyInterceptor extends HandlerInterceptorAdapter {

	//private List<String> denyCount;

//	public AccessDenyInterceptor() {
//		denyCount = new ArrayList<String>();
//
//	}

	private final Logger logger = LoggerFactory.getLogger(AccessDenyInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//String contextPath = request.getContextPath();

		// logger.info(request.getRequestURL()+"안돼, 돌아가.");
		logger.info((String) request.getParameter("email"));
		logger.info((String) request.getParameter("password"));
		logger.info(request.getMethod());

//		if (request.getMethod() == "POST") {
//			logger.info((String) request.getAttribute("id"));
//
//			try {
//				if (request.getParameter("id").equals("admin") && !request.getParameter("password").equals("1234")) {
//					denyCount.add(request.getParameter("id"));
//					System.out.println(denyCount.size());
//					if (denyCount.size() < 3) {
//						response.sendRedirect(contextPath + "/login");
//						return false;
//					} else {
//						RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/error/accessdeny.jsp");
//						rd.forward(request, response);
//						return false;
//					}
//
//				}
//			} catch (NullPointerException npe) {
//				return true;
//			}
//		}
		return true;

	}

}
