package com.wbh.easyjob4trainee.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wbh.easyjob4trainee.dao.MemberDao;
import com.wbh.easyjob4trainee.entity.Member;

@Repository
public class MemberDaoImpl extends BaseDaoImpl<Member, Integer> implements MemberDao {
	
	@SuppressWarnings("unchecked")
	public Member getMember(String username, String password){
		Criteria c = getSession()
				.createCriteria(Member.class)
				.add(Restrictions.eq( "memberUsername", username))
				.add(Restrictions.eq("memberPassword", password));
		List<Member> ms = c.list(); 
		return ms.isEmpty()?null:ms.get(0);
	}

	@SuppressWarnings("unchecked")
	public Member getMember(String username) {
		Criteria c = getSession()
				.createCriteria(Member.class)
				.add(Restrictions.eq( "memberUsername", username));
		List<Member> ms = c.list(); 
		return ms.isEmpty()?null:ms.get(0);
	}
	
	public Boolean isMemberExistByName(String username){
		Criteria c = getSession().createCriteria(Member.class)
				.add(Restrictions.eq( "memberUsername", username))
				.setProjection(Projections.rowCount());

			if(Integer.parseInt(c.uniqueResult().toString())>0){
				return true;
			}else{
				return false;
			}
	}
	
	public List<Member> getMembers() {
			return  getList();
	}

	public Member addAndGetMember(Member member) {
		return saveAndGet(member);
	}

	@Override
	public void updateMember(Member member) {
		getSession().update(member);
	}


}
