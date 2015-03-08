package com.wbh.easyjob4trainee.dao;

import java.util.List;

import com.wbh.easyjob4trainee.entity.Member;

public interface MemberDao extends BaseDao<Member, Integer> {
	
	public Member getMember(String name, String password);

	public Member getMember(String name);
	
	public Boolean isMemberExistByName(String name);
	
	public List<Member> getMembers();

	public Member addAndGetMember(Member member);

	public void updateMember(Member member);

}
