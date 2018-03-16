package com.dgit.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MemberVO;

@Repository
public class MemberDaoImpl implements MemberDao {

	private static final String namespace="com.dgit.mappers.MemberMapper";
	
	@Autowired
	private SqlSession session;
	
	@Override
	public List<MemberVO> list() throws Exception {
		return session.selectList(namespace+".list");
	}

	@Override
	public void insert(MemberVO vo) throws Exception {
		session.insert(namespace+".insert",vo);
	}

	@Override
	public void update(MemberVO vo) throws Exception {
		session.update(namespace+".update",vo);
	}

	@Override
	public void delete(String userid) throws Exception {
		session.delete(namespace+".delete",userid);
	}

}
