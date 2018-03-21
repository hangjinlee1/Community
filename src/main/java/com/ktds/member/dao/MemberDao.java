package com.ktds.member.dao;

import com.ktds.member.vo.MemberVO;

public interface MemberDao {

	public int insertMember(MemberVO memberVo);
	
	public MemberVO selectMember(MemberVO memberVO);
	
	public int deleteMember(int id);
	
}
