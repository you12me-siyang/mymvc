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
 * Univercity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "univercity", catalog = "easyjob4trainee")
public class Univercity implements java.io.Serializable {

	private static final long serialVersionUID = 1731406500961768287L;
	
	private Integer univercityId;
	private String univercityName;
	private Set<Member> members = new HashSet<Member>(0);

	// Constructors

	/** default constructor */
	public Univercity() {
	}

	/** minimal constructor */
	public Univercity(String univercityName) {
		this.univercityName = univercityName;
	}

	/** full constructor */
	public Univercity(String univercityName, Set<Member> members) {
		this.univercityName = univercityName;
		this.members = members;
	}

	public Univercity(Integer univercity_id) {
		this.univercityId = univercity_id;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "univercity_id", unique = true, nullable = false)
	public Integer getUnivercityId() {
		return this.univercityId;
	}

	public void setUnivercityId(Integer univercityId) {
		this.univercityId = univercityId;
	}

	@Column(name = "univercity_name", nullable = false, length = 45)
	public String getUnivercityName() {
		return this.univercityName;
	}

	public void setUnivercityName(String univercityName) {
		this.univercityName = univercityName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "univercity")
	public Set<Member> getMembers() {
		return this.members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

}