package com.wbh.easyjob4trainee.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Major entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "major", catalog = "easyjob4trainee")
public class Major implements java.io.Serializable {


	private static final long serialVersionUID = -249464480857734957L;
	
	private Integer majorId;
	private String majorName;
	private Set<Member> members = new HashSet<Member>(0);

	// Constructors

	/** default constructor */
	public Major() {
	}

	/** minimal constructor */
	public Major(String majorName) {
		this.majorName = majorName;
	}

	/** full constructor */
	public Major(String majorName, Set<Member> members) {
		this.majorName = majorName;
		this.members = members;
	}

	public Major(Integer major_id) {
		this.majorId = major_id;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "major_id", unique = true, nullable = false)
	public Integer getMajorId() {
		return this.majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	@Column(name = "major_name", nullable = false, length = 45)
	public String getMajorName() {
		return this.majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "major")
	public Set<Member> getMembers() {
		return this.members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

}