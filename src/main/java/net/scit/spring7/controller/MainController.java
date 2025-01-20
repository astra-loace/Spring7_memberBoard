package net.scit.spring7.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.scit.spring7.dto.LoginUserDetails;

@Controller
public class MainController {
	
	@GetMapping({"/", ""})
	public String index(
			@AuthenticationPrincipal LoginUserDetails loginUser
			, Model model
			) {
		
		// 로그인을 한 경우에는~
		if(loginUser != null) {
			String loginName = loginUser.getUserName();
			
			model.addAttribute("loginName", loginName); // 이건 클라이언트단에서 이 값이랑 일치되는 걸 출력해줄 거야. 그래서 정확히 정의해주는 게 중요함.
		}
		
		return "index";
	}

}
