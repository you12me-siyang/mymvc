package com.wbh.easyjob4trainee.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.wbh.easyjob4trainee.entity.Member;
import com.wbh.easyjob4trainee.service.MemberService;
import com.wbh.easyjob4trainee.util.Constants;

@Controller
public class BaseMemberOperationController extends BaseController {

	@Resource
	protected MemberService memberService;

	protected String getReferer(){
		return super.referer;
	}
	
	// 获取当前会员
	public Member getCurMember() {
		return (Member) sessionGet(Constants.CURRENT_MEMBER);
	}

	// 删除当前会员
	public void delCurMember() {
		sessionDel(Constants.CURRENT_MEMBER);
	}

	// 添加当前会员
	public void addCurMember(Member m) {
		sessionPut(Constants.CURRENT_MEMBER, m);
	}

	// 判断会员是否登录
	public boolean isMemberExist() {
		return null == getCurMember() ? false : true;
	}

}
