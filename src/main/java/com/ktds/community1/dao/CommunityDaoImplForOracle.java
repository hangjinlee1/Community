package com.ktds.community1.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.community1.vo.CommunityVO;

public class CommunityDaoImplForOracle extends SqlSessionDaoSupport implements CommunityDao {

	@Override
	public List<CommunityVO> selectAll() {
		return getSqlSession().selectList("CommunityDao.selectAll");
	}

	@Override
	public int insertCommunity(CommunityVO communityVO) {
		return getSqlSession().insert("CommunityDao.insertCommunity", communityVO);
	}

	@Override
	public CommunityVO selectOne(int id) {
		
		return getSqlSession().selectOne("CommunityDao.selectOne", id);
	}

	@Override
	public int updateViewCount(int id) {
		return getSqlSession().update("CommunityDao.updateViewCount", id);
	}

	@Override
	public int updateRecommendCount(int id) {
		return getSqlSession().update("CommunityDao.updateRecommendCount", id);
	}

	@Override
	public int deleteOne(int id) {
		return getSqlSession().delete("CommunityDao.deleteOne", id);
	}

	@Override
	public int deleteMyCommunities(int userId) {
		return getSqlSession().delete("CommunityDao.deleteMyCommunities", userId);
	}

	@Override
	public int updateCommunity(CommunityVO communityVO) {
		return getSqlSession().update("CommunityDao.updateCommunity", communityVO);
	}

	@Override
	public int selectMyCommunitiesCount(int userId) {
		return getSqlSession().selectOne("CommunityDao.selectMyCommunitiesCount", userId);
	}

	@Override
	public List<CommunityVO> selectMyCommunities(int userId) {
		System.out.println("요기능 다오!");
		return getSqlSession().selectList("CommunityDao.selectMyCommunities", userId);
	}

	@Override
	public int deleteCommunities(List<Integer> ids, int userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("userId", userId);
		
		
		return getSqlSession().delete("CommunityDao.deleteCommunities", params);
	}

	/*
	 * SqlSessionDaoSupport
	 * sqlSessionTemplate Bean 객체를 가지고 있음
	 */
	
	
	
	
}
