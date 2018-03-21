package com.ktds.community1.service;

import java.util.List;

import com.ktds.community1.vo.CommunityVO;

public interface CommunityService {
	public List<CommunityVO> getall();
	
	public boolean createCoomunity(CommunityVO communityVO);
	
	public CommunityVO getOne(int id);

	public boolean getRecommendCount(int id);
	
	public boolean getViewCount(int id);
	
	public boolean removeOne(int id);
	
	public boolean updateCommunity(CommunityVO communityVO);
	
	public int readMyCommunitiesCount(int userId);
	
	public List<CommunityVO> readMyCommunities(int userId);
	
	public boolean removeCommunities(List<Integer> ids, int userId);
	
}
