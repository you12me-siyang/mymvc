package com.wbh.easyjob4trainee.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wbh.easyjob4trainee.dao.MemberDao;
import com.wbh.easyjob4trainee.entity.Member;

@Service
public class MemberService {
	
	@Resource
	private MemberDao memberDao;
	
	public Member getMember(String username){
		return memberDao.getMember(username);
	}
	public boolean isMemberExist(String username){
		return memberDao.isMemberExistByName(username);
	}
	public Member getMember(String username, String password) {
		return memberDao.getMember(username, password);
	}
	public List<Member> getMembers(){
		return memberDao.getMembers();
	}

	public Member addAndGetMember(Member member) {
		return memberDao.addAndGetMember(member);
	}
	public void updateMember(Member member) {
		memberDao.updateMember(member);
	}
}
