package com.wbh.easyjob4trainee.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wbh.easyjob4trainee.dto.MemberCompleteInfo;
import com.wbh.easyjob4trainee.dto.MemberRegister;
import com.wbh.easyjob4trainee.dto.MemberLogin;
import com.wbh.easyjob4trainee.dto.RedirectPage;
import com.wbh.easyjob4trainee.entity.Member;
import com.wbh.easyjob4trainee.util.Constants;
import com.wbh.easyjob4trainee.util.RandomPasswordFactory;

/**
 * @author wbh
 * 用于游客访问和操作的控制器
 */
@Controller
public class MemberVisitController extends BaseMemberOperationController {

	@Resource
	private RandomPasswordFactory randomPasswordFactory;
	
	/**
	 * 主页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public ModelAndView indexPage(Model model) {
		return new ModelAndView("index", model.asMap());
	}

	/**
	 * 注册页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = { RequestMethod.GET })
	public ModelAndView memberRegister(Model model) {
		if (!model.containsAttribute("memberRegister")) {
			model.addAttribute("memberRegister", new MemberRegister());
		}
		return new ModelAndView("register", model.asMap());
	}

	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	public ModelAndView memberRegister(
			Model model,
			@Valid @ModelAttribute("memberRegister") MemberRegister memberRegister,
			BindingResult result) {
		if (result.hasErrors()) {
			return memberRegister(model);
		} else {
			if (!memberService
					.isMemberExist(memberRegister.getMemberUsername())) {
				Member m = memberService.addAndGetMember(memberRegister
						.toMember());
				addCurMember(m);
				sessionPut(Constants.CURRENT_MEMBER, m);
				return new ModelAndView("redirect:/completeinfo", model.asMap());
			} else {
				addPageMSG("error.member.username.exist");
				return memberRegister(model);
			}
		}
	}

	/**
	 * 用户信息完善
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/completeinfo", method = { RequestMethod.GET })
	public ModelAndView completeInfo(Model model) {
		if (!model.containsAttribute("memberCompleteInfo")) {
			model.addAttribute("memberCompleteInfo", new MemberCompleteInfo());
		}
		return new ModelAndView("completeinfo", model.asMap());
	}

	@RequestMapping(value = "/completeinfo", method = { RequestMethod.POST })
	public ModelAndView completeInfo(
			Model model,
			@Valid @ModelAttribute("memberCompleteInfo") MemberCompleteInfo memberCompleteInfo,
			BindingResult result) {
		if (result.hasErrors()) {
			return completeInfo(model);
		} else {
			Member m = memberCompleteInfo.completeMemberInfo(getCurMember());
			memberService.updateMember(m);
			addCurMember(m);
			return new ModelAndView("redirect:/member/membercenter",
					model.asMap());
		}
	}

	/**
	 * 登录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public ModelAndView memberLogin(Model model) {
		if (!model.containsAttribute("memberLogin")) {
			model.addAttribute("memberLogin", new MemberLogin());
		}
		return new ModelAndView("login", model.asMap());
	}

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public ModelAndView memberLogin(Model model,
			@Valid @ModelAttribute("memberLogin") MemberLogin memberLogin,
			BindingResult result) {
		if (result.hasErrors()) {
			return memberLogin(model);
		} else {
			Member m = memberService.getMember(memberLogin.getMemberUsername(),
					memberLogin.getMemberPassword());
			if (null == m) {
				addPageMSG("error.member.login.notExist");
				return memberLogin(model);
			} else {
				addCurMember(m);
				return new ModelAndView("redirect:/member/membercenter",
						model.asMap());
			}
		}
	}
	
	/**
	 * 注销
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public ModelAndView memberLogout(Model model) {
		delCurMember();
		return new ModelAndView("redirect:index", model.asMap());
	}

	@RequestMapping(value = "/forgetpassword", method = { RequestMethod.GET })
	public ModelAndView memberForgetPassword(Model model) {
		return new ModelAndView("forgetpassword", model.asMap());
	}

	/**
	 * @param model
	 * @param memberUsername
	 * @return
	 * 忘记密码功能 尚未完整实现
	 */
	@RequestMapping(value = "/forgetpassword", method = { RequestMethod.POST })
	public ModelAndView memberForgetPassword(Model model, String memberUsername) {
		if (null == memberUsername || ("").equals(memberUsername.trim())) {
			addPageMSG("error.member.username.empty");
			return memberForgetPassword(model);
		} else {
			Member m = memberService.getMember(memberUsername);
			if (null == m) {
				addPageMSG("error.member.username.notExist");
				return memberForgetPassword(model);
			} else {
				if (m.getComplete() == 0) {
					addPageMSG("error.member.forgetPassword.notCompleteInfo");
					return memberForgetPassword(model);
				} else {
					String newPassword = randomPasswordFactory.getResult();
					m.setMemberPassword(newPassword);
					memberService.updateMember(m);
					model.addAttribute(Constants.REDIRECT_PAGE, new RedirectPage("index", getMessage("success.member.forgetPassword")));
					return redirect(model);
				}
			}
		}
	}
	
	/**
	 * 跳转页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/redirect", method = { RequestMethod.GET })
	public ModelAndView redirect(Model model) {
		return new ModelAndView("redirect", model.asMap());
	}

	//================================AJAX 部分===============================
	
	/**
	 * 检查用户名是否存在
	 * @param memberUsername
	 * @return
	 */
	@RequestMapping("/checkMemberExist.json")
	public @ResponseBody
	boolean checkMemberExist(String memberUsername) {
		return memberService.isMemberExist(memberUsername);
	}

}
