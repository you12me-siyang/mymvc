package com.wbh.easyjob4trainee.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.wbh.easyjob4trainee.entity.Member;

public class MemberRegister implements java.io.Serializable {

	private static final long serialVersionUID = 559971017090874568L;

	@NotEmpty(message = "{memberUsername.empty}")
	@Length(min = 4, max = 20, message = "{memberUsername.lengthError}")
	private String memberUsername;
	
	@NotEmpty(message = "{memberPassword.empty}")
	@Length(min = 6, max = 16, message = "{memberPassword.lengthError}")
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
