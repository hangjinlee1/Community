package com.ktds.member.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community1.service.CommunityService;
import com.ktds.member.constants.Member;
import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class MemberController {
	
	private MemberService memberService;
	private CommunityService communityService;
	
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLoginPage(HttpSession session) {

		if (session.getAttribute(Member.USER) != null) {

			return "redirect:/";
		}

		return "member/login";
	}
	
	@RequestMapping("/delete/process1")
	public String viewVerifyPage() {
		return "member/delete/process1";
	}
	
	@RequestMapping("/delete/process2")
	public ModelAndView viewVerifyMyCommunitiesPage
	(@RequestParam(required = false, defaultValue = "")
	String password, HttpSession session) {
		
		if( password.length()  == 0) {
			return new ModelAndView("error/404");
		}
		
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		member.setPassword(password);
		
		MemberVO verifyMember = memberService.readMember(member);
		if( verifyMember == null ) {
			return new ModelAndView("redirect:/delete/process1");
		}
		
		//TODO 내가 작성한 게시글의 개수 가져오기
		
		int myCommunitiesCount = communityService.readMyCommunitiesCount(verifyMember.getId());
		
		ModelAndView view = new ModelAndView();	
		view.setViewName("member/delete/process2");
		view.addObject("myCommunitiesCount", myCommunitiesCount);
		
		String uuid = UUID.randomUUID().toString();
		session.setAttribute("__TOKEN__", uuid);
		view.addObject("token", uuid);
		return view;
	}
	
	
	@RequestMapping(value = "/removemember/{deleteFlag}")
	public String removeMember(@PathVariable String deleteFlag, 
									@RequestParam(required=false, defaultValue="") String token, HttpSession session) {
		
		String sessionToken = (String) session.getAttribute("__TOKEN__");
		if( sessionToken == null || !sessionToken.equals(token) ) {
			return "errpr/404";
					
		}
		
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);	
		if( member == null ) {
			return "redirect:/login";
		}
		
		int id = member.getId();
		
		if( memberService.removeMember(id, deleteFlag) ) {
			session.invalidate();
		}

		return "member/delete/delete";
	}
	
	@RequestMapping(value="/regist", method = RequestMethod.POST )
	public ModelAndView doRegistAction(@ModelAttribute("registForm") @Valid MemberVO memberVO, 
								Errors errors) {
		
		if ( errors.hasErrors() ) {
			return new ModelAndView("member/regist");
		}
		
		if ( memberService.createMember(memberVO) ) {
			return new ModelAndView("redirect:/login");
		}
		
		return new ModelAndView("member/regist");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	// request에서 세션을 불러온다
	public String doLoginAction(MemberVO memberVO, HttpServletRequest request) {

		// request객체에서 세션을 꺼내오는 방법
		HttpSession session = request.getSession();
		
		MemberVO loginMember = memberService.readMember(memberVO);
		
		System.out.println(loginMember.getNickname());
		if ( loginMember != null ) {
			session.setAttribute(Member.USER, loginMember);
			return "redirect:/";
		}

		return "redirect:/login";

	}

	@RequestMapping("/logout")
	public String doLogoutAction(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping("/regist")
	public String viewRegistPage() {
	   return "member/regist";
	}

}
