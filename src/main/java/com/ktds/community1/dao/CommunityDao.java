package com.ktds.community1.dao;

import java.util.List;

import com.ktds.community1.vo.CommunityVO;

public interface CommunityDao {
	public List<CommunityVO> selectAll();

	public int insertCommunity(CommunityVO communityVO);

	//한개만 가지고 오는 method
	public CommunityVO selectOne(int id);
	
	public int selectMyCommunitiesCount(int userId);
	
	public int updateViewCount(int id);
	
	public int updateRecommendCount(int id);
	
	public int deleteOne(int id);
	
	//crud에서는 r빼고는 int로 선언
	
	public int updateCommunity(CommunityVO communityVO);
	
	
	public int deleteMyCommunities(int userId);
	
	public int deleteCommunities(List<Integer> ids, int userId);
	
	public List<CommunityVO> selectMyCommunities(int userId);
}
