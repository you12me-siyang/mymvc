package com.wbh.easyjob4trainee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Member entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "member", catalog = "easyjob4trainee")
public class Member implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4540807675980478838L;
	
	private Integer memberId;
	private Major major;
	private Univercity univercity;
	private String memberUsername;
	private String memberPassword;
	private Integer memberAge;
	private String memberSex;
	private String memberGrade;
	private String memberEmail;
	private String memberAddress;
	private String memberPhone;
	private Integer complete;

	// Constructors

	/** default constructor */
	public Member() {
	}

	/** minimal constructor */
	public Member(String memberUsername, String memberPassword, Integer complete) {
		this.memberUsername = memberUsername;
		this.memberPassword = memberPassword;
		this.complete = complete;
	}

	/** full constructor */
	public Member(Major major, Univercity univercity, String memberUsername,
			String memberPassword, Integer memberAge, String memberSex,
			String memberGrade, String memberEmail, String memberAddress,
			String memberPhone, Integer complete) {
		this.major = major;
		this.univercity = univercity;
		this.memberUsername = memberUsername;
		this.memberPassword = memberPassword;
		this.memberAge = memberAge;
		this.memberSex = memberSex;
		this.memberGrade = memberGrade;
		this.memberEmail = memberEmail;
		this.memberAddress = memberAddress;
		this.memberPhone = memberPhone;
		this.complete = complete;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "member_id", unique = true, nullable = false)
	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "major_id")
	public Major getMajor() {
		return this.major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "univercity_id")
	public Univercity getUnivercity() {
		return this.univercity;
	}

	public void setUnivercity(Univercity univercity) {
		this.univercity = univercity;
	}

	@Column(name = "member_username", nullable = false, length = 45)
	public String getMemberUsername() {
		return this.memberUsername;
	}

	public void setMemberUsername(String memberUsername) {
		this.memberUsername = memberUsername;
	}

	@Column(name = "member_password", nullable = false, length = 45)
	public String getMemberPassword() {
		return this.memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	@Column(name = "member_age")
	public Integer getMemberAge() {
		return this.memberAge;
	}

	public void setMemberAge(Integer memberAge) {
		this.memberAge = memberAge;
	}

	@Column(name = "member_sex", length = 5)
	public String getMemberSex() {
		return this.memberSex;
	}

	public void setMemberSex(String memberSex) {
		this.memberSex = memberSex;
	}

	@Column(name = "member_grade", length = 10)
	public String getMemberGrade() {
		return this.memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

	@Column(name = "member_email", length = 60)
	public String getMemberEmail() {
		return this.memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	@Column(name = "member_address", length = 100)
	public String getMemberAddress() {
		return this.memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	@Column(name = "member_phone", length = 20)
	public String getMemberPhone() {
		return this.memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	@Column(name = "complete", nullable = false)
	public Integer getComplete() {
		return this.complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}

}