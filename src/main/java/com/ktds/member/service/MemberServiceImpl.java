package com.ktds.member.service;

import com.ktds.member.dao.MemberDao;
import com.ktds.member.vo.MemberVO;

public class MemberServiceImpl implements MemberService {

	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Override
	public boolean createMember(MemberVO memberVO) {
		return memberDao.insertMember(memberVO) > 0;
	}

	@Override
	public MemberVO readMember(MemberVO memberVO) {
		return memberDao.selectMember(memberVO);
	}

	@Override
	public boolean removeMember(int id, String deleteFlag) {
		
		if( deleteFlag.equals("y") ) {
			memberDao.deleteMember(id);
		}
		
		return memberDao.deleteMember(id) > 0;
	}
	
	

}
