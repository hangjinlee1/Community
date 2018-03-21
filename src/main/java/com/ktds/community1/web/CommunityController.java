package com.ktds.community1.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community1.service.CommunityService;
import com.ktds.community1.util.DownloadUtil;
import com.ktds.community1.vo.CommunityVO;
import com.ktds.member.constants.Member;
import com.ktds.member.vo.MemberVO;

@Controller
public class CommunityController {
	
	private CommunityService communityService;
	
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	@RequestMapping(value="/modify/{id}", method = RequestMethod.GET)
	public ModelAndView viewModifyPage(@PathVariable int id,HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		
		CommunityVO communityVO = communityService.getOne(id);		
		
		int userId = member.getId();		
		
		if ( userId != communityVO.getId() ) {
			return new ModelAndView("/WEB-INF/view/error/404");
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("community/write");
		view.addObject("communityVO", communityVO);
		view.addObject("mode", "modify");
		return view;
	}
	
	@RequestMapping(value="/modify/{id}", method = RequestMethod.POST)
	public String doModifyAction(@PathVariable int id, HttpSession session,
									HttpServletRequest request, 
									@ModelAttribute("writeForm") 
									@Valid CommunityVO communityVO, Errors errors) {
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO originalVO = communityService.getOne(id);
		
		if( member.getId() != originalVO.getUserId() ) {
			return "error/404";
			
		}
		if( errors.hasErrors() ) {
			return "redirect:/modify/" + id;
		}
		
		CommunityVO newCommunity = new CommunityVO();
		newCommunity.setId(originalVO.getId());
		newCommunity.setUserId(member.getId());
		
		boolean isModify = false;
		
		//1. ip변경 확인
		String ip = request.getRemoteAddr();
		if ( !ip.equals( originalVO.getRequestIp() )) {
			newCommunity.setRequestIp(ip);
			isModify = true;
		}
		
		//2. 제목 변경확인
		if ( !originalVO.getTitle().equals(communityVO.getTitle()) ) {
			newCommunity.setTitle(communityVO.getTitle());
			isModify = true;
		}
		
		//3. 내용 변경확인
		if ( !originalVO.getBody().equals(communityVO.getBody()) ) {
			newCommunity.setBody(communityVO.getBody());
			isModify = true;
		}

		//4. 파일 변경확인
		if ( communityVO.getDisplayFilename().length() > 0 ) {
			File file = new File("D:\\uploadFiles/" + communityVO.getDisplayFilename());
			file.delete();
			communityVO.setDisplayFilename("");
		}
		else {
			communityVO.setDisplayFilename( originalVO.getDisplayFilename() );
		}
		
		communityVO.save();
		if ( !originalVO.getDisplayFilename().equals(communityVO.getDisplayFilename())) {
			newCommunity.setDisplayFilename(communityVO.getDisplayFilename());
			isModify = true;
		}
		
		//5. 변경이 없는지 확인
		if ( isModify ) {
			// 6. update하는 service code 호출
			communityService.updateCommunity(newCommunity);
		}
		
		
		return "redirect:/view/" + id;
	}
	
	
	// 제일 첫 화면이 list페이지가 될거다
	@RequestMapping("/")
	public ModelAndView viewListPage( HttpSession session) {
		
		if( session.getAttribute(Member.USER)==null) {
			return new ModelAndView("redirect:/login");
		}
		
		ModelAndView view = new ModelAndView();
		
		// /WEB-INF/view/community/list.jsp 가 만들어진다!
		view.setViewName("community/list");
		List<CommunityVO> communityList = communityService.getall();
		view.addObject("communityList", communityList);
		return view;
	}
	
	//@RequestMapping(value = "/write", method = RequestMethod.GET)
	@GetMapping("/write")
	public String writePage(HttpSession session) {
		
		if( session.getAttribute(Member.USER)==null) {
			return "redirect:/login";
		}
		
		return "community/write"; 
	}
	
	//@RequestMapping(value = "/write", method = RequestMethod.POST)
	@PostMapping("/write")
	// commend 객체=VO를 통해서 멤버변수를 자동적으로 가지고 온다.
	public ModelAndView doWrite(@ModelAttribute("writeForm") 
	@Valid CommunityVO communityVO, Errors errors, HttpSession session
	, HttpServletRequest request) {
		//ip는 request안에 있음
		
		
		//글을 쓰는데 30분 넘게 쓰는거야.... 그래서 session이 날라가...그럼 로그인을 다시하라구해...
		//임시저장하는게 실제로는 세션을 연장하는거래
		if( session.getAttribute(Member.USER)==null) {
			return new ModelAndView("redirect:/login");
		}
			
		ModelAndView view = new ModelAndView();
		
		//필수입력을 안햇을때 안했다면 처리
		//글이 잘 작성됐는지 확인하고 아니면 wirte page로 돌아가야함
		if( errors.hasErrors() ) {
			view.setViewName("community/write");
			view.addObject("communityVO", communityVO);
			return view;
		}
		
		//작성자의 ip를 얻어오는 코드
		String requestorIp = request.getRemoteAddr();
		communityVO.setRequestIp(requestorIp);
		
		//file upload
		communityVO.save();
		
		boolean isSuccess = communityService.createCoomunity(communityVO);
		if ( isSuccess ) {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("redirect:/write");
	}
	
	//String 은 보여주기만하는거고 데이터도 주고 보여준다 = ModelAndView
	///view/{id} id와 @PathVariable int id id가 같아야함
	@RequestMapping("/view/{id}")
	public ModelAndView viewViewPage(HttpSession session, @PathVariable int id) {
		
		//login check
		if( session.getAttribute(Member.USER) == null ) {
			return new ModelAndView("redirect:/login");
		}
		
		ModelAndView view = new ModelAndView();
		view.setViewName("community/view");
		
		//TODO id로 게시글 얻어오기
		CommunityVO community = communityService.getOne(id);		
		view.addObject("community", community);
		
		return view;
	}
	
	@RequestMapping("/viewcount/{id}")
	public String viewCount(HttpSession session, @PathVariable int id) {
		
		//login check
		if( session.getAttribute(Member.USER) == null ) {
			return "redirect:/login";
		}
		
		boolean isViewSuccess = communityService.getViewCount(id);

		if ( isViewSuccess ) {
			return "redirect:/view/"+id;
		}
		return "redirect:/";
		
	}
	
	@RequestMapping("/recommend/{id}")
	public String viewRecommend(HttpSession session,  @PathVariable int id) {
		//login check
		if( session.getAttribute(Member.USER) == null ) {
			return "redirect:/login";
		}		
		
		boolean isRecommendSuccess = communityService.getRecommendCount(id);
		
		if ( isRecommendSuccess ) {
			return "redirect:/view/"+id;
		}
		return "redirect:/";	
	}
	
	//return type이 void라도 response를 할수 있음
	@RequestMapping("/get/{id}")
	public void download(@PathVariable int id, 
			HttpServletRequest request, HttpServletResponse response) {
		
		CommunityVO community = communityService.getOne(id);
		String filename = community.getDisplayFilename();
		
		//생성자가 필요한데 파일의 경로를 적어줘야함
		DownloadUtil download = new DownloadUtil("D:/uploadFiles/"+filename);
		
		try {
			download.download(request, response, filename);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e); 
			//새로운 오류를 만들어주는것 오류가 발생했을때 runtime으로 바꿔주면 좀더 안전하게 처리 할 수 있음
		}
		
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String removeArticle(@PathVariable int id, HttpSession session) {
		
		int memberId = communityService.getOne(id).getMemberVO().getId();
		boolean isDelSuccess;
		
		System.out.println(session.getAttribute(Member.USER));
		MemberVO member = (MemberVO)session.getAttribute(Member.USER);
		if ( memberId == member.getId() ) {
			System.out.println("멤버아이디는 같아");
			isDelSuccess = communityService.removeOne(id);
			if( isDelSuccess ) {
				System.out.println("성공했어");
				return "redirect:/";			
			}
		}	
		return "redirect:/view/{id}";
		
	}
	

}
