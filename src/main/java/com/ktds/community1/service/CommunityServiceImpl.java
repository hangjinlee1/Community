package com.ktds.community1.service;

import java.util.ArrayList;
import java.util.List;

import com.ktds.community1.dao.CommunityDao;
import com.ktds.community1.vo.CommunityVO;

public class CommunityServiceImpl implements CommunityService{
	// 멤버변수 추가
	private CommunityDao communityDao;
	// setter 추가 setCommunity + ctrl + space
	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}
	
	@Override
	public List<CommunityVO> getall() {
		return communityDao.selectAll();
	}

	@Override
	public boolean createCoomunity(CommunityVO communityVO) {
		String body = communityVO.getBody();
		//\n --> <br/>
		body = body.replace("\n", "<br/>");
		communityVO.setBody(body);
		
		if( filter(body) || filter(communityVO.getTitle() )) {
			return false;
		}
		
		int insertCount = communityDao.insertCommunity(communityVO);
		
		
		return insertCount > 0;
	}

	@Override
	public CommunityVO getOne(int id) {
		return communityDao.selectOne(id);
	}
	
	@Override
	public boolean getRecommendCount(int id) {
		
		if( communityDao.updateRecommendCount(id) > 0 ) {
			return true;
		}
		return false;
		
	}

	@Override
	public boolean getViewCount(int id) {
		if( communityDao.updateViewCount(id) > 0 ) {
			return true;
		}
		return false;
	}
	
	public boolean filter(String str) {
		
		List<String> blackList = new ArrayList<String>();
		
		blackList.add("욕");
		blackList.add("씨");
		blackList.add("발");
		blackList.add("종간나세끼");
		blackList.add("일식");
		blackList.add("이식");
		blackList.add("삼식");
		
		
		//str ==> 남편은 이식 이에요.
		String[] splitString = str.split(" ");
		
		for( String word : splitString ) {
			for( String blackString : blackList ) {
				if( word.contains(blackString) ) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean removeOne(int id) {
		if( communityDao.deleteOne(id) > 0 ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateCommunity(CommunityVO communityVO) {
		return communityDao.updateCommunity(communityVO) > 0;
	}

	@Override
	public int readMyCommunitiesCount(int userId) {
		return communityDao.selectMyCommunitiesCount(userId);
	}

	@Override
	public List<CommunityVO> readMyCommunities(int userId) {
		
		System.out.println("요기능 서비스!");
		return communityDao.selectMyCommunities(userId);
	}

	@Override
	public boolean removeCommunities(List<Integer> ids, int userId) {
		return communityDao.deleteCommunities(ids, userId) > 0;
	}
	
}
