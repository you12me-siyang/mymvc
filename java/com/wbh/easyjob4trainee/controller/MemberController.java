package com.wbh.easyjob4trainee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseMemberOperationController {
	
	@RequestMapping("/membercenter")
	public ModelAndView memberCenter(Model model) {
		return new ModelAndView("/member/membercenter",model.asMap());
	}

}
