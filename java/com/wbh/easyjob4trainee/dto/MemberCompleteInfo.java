package com.wbh.easyjob4trainee.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.wbh.easyjob4trainee.entity.Major;
import com.wbh.easyjob4trainee.entity.Member;
import com.wbh.easyjob4trainee.entity.Univercity;

public class MemberCompleteInfo {
	
	@NotNull 
	private Integer major_id;
	@NotNull 
	private Integer univercity_id;
	@NotNull 
	@Range(min=0, max=200)
	private Integer memberAge;
	@NotEmpty
	private String memberSex;
	@NotEmpty
	private String memberGrade;
	@NotEmpty
	@Email
	private String memberEmail;
	@NotEmpty
	private String memberAddress;
	@NotEmpty
	private String memberPhone;
	
	public Integer getMajor_id() {
		return major_id;
	}
	public void setMajor_id(Integer major_id) {
		this.major_id = major_id;
	}
	public Integer getUnivercity_id() {
		return univercity_id;
	}
	public void setUnivercity_id(Integer univercity_id) {
		this.univercity_id = univercity_id;
	}
	public Integer getMemberAge() {
		return memberAge;
	}
	public void setMemberAge(Integer memberAge) {
		this.memberAge = memberAge;
	}
	public String getMemberSex() {
		return memberSex;
	}
	public void setMemberSex(String memberSex) {
		this.memberSex = memberSex;
	}
	public String getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberAddress() {
		return memberAddress;
	}
	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	
	public Member completeMemberInfo(Member m){
		m.setMajor(new Major(this.getMajor_id()));
		m.setUnivercity(new Univercity(this.getUnivercity_id()));
		m.setMemberAddress(this.getMemberAddress());
		m.setMemberAge(this.getMemberAge());
		m.setMemberEmail(this.getMemberEmail());
		m.setMemberGrade(this.getMemberGrade());
		m.setMemberPhone(this.getMemberPhone());
		m.setMemberSex(this.getMemberSex());
		m.setComplete(1);
		return m;
	}
}
