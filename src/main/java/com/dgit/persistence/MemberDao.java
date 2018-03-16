package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.MemberVO;

public interface MemberDao {
	public List<MemberVO> list() throws Exception;
	public void insert(MemberVO vo) throws Exception;
	public void update(MemberVO vo) throws Exception;
	public void delete(String userid) throws Exception;
}
