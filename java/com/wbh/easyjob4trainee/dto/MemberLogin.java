package com.wbh.easyjob4trainee.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.wbh.easyjob4trainee.entity.Member;

public class MemberLogin implements java.io.Serializable {

	private static final long serialVersionUID = 559971017090874568L;

	@NotEmpty(message = "{memberUsername.empty}")
	private String memberUsername;
	
	@NotEmpty(message = "{memberPassword.empty}")
	private String memberPassword;

	public String getMemberUsername() {
		return memberUsername;
	}

	public void setMemberUsername(String memberUsername) {
		this.memberUsername = memberUsername;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}
	
	public Member toMember(){
		return new Member(this.getMemberUsername(),this.getMemberPassword(),0);
	}

}
