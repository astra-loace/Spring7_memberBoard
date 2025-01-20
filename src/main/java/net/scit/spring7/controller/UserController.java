package net.scit.spring7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.scit.spring7.dto.UserDTO;
import net.scit.spring7.service.UserService;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	/**
	 * 회원가입 화면 요청
	 * @return
	 * */
	@GetMapping("/join")
	public String join( ) {
		return "user/join";
	}
	
	@PostMapping("/idCheck")
	@ResponseBody
	public boolean idCheck(
			@RequestParam(name="userId") String userId) {
		
		boolean result = userService.existId(userId);
		
		return true;
	}
	
	@PostMapping("/joinProc")
	public String joinProc(@ModelAttribute UserDTO userDTO) {
		log.info("회원정보: {}", userDTO.toString());
		boolean result = userService.joinProc(userDTO);
		return "redirect:/";
	}
	
	/**
	 * 1) 로그인 화면 요청
	 * 2) 에러 발생시 이 로그인 페이지로 다시 돌아와(redirect)!
	 * @return
	 * */
	@GetMapping("/login")
	public String login(
			@RequestParam(name="error", required = false) String error,
			Model model
			) {
		model.addAttribute("error", error);
		model.addAttribute("errMessage", "아이디와 비밀번호를 확인해주세요.");
		
		return "user/login";
	}
	
	/**
	 * 회원 정보를 위한 페이지 요청
	 * @return
	 * */
	@GetMapping("/mypage")
	public String mypage() {
		return "user/mypage";
	}
	
	/**
	 * 개인정보 수정을 위한 아이디/비밀번호 요청
	 * @param userId
	 * @param userPwd
	 * @return
	 * */
	@PostMapping("/pwdCheck")
	public String pwdCheck(Model model,
			@RequestParam(name="userId") String userId,
			@RequestParam(name="userPwd") String userPwd
			) {
		
		// DB에 가서 아이디와 비밀번호가 맞는지 확인
		UserDTO userDTO = userService.pwdCheck(userId, userPwd);
		
		if (userDTO != null) {
			model.addAttribute("userDTO", userDTO);
			return "/user/myInfoUpdate";
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/updateProc")
	public String updateProc(@ModelAttribute UserDTO userDTO) {
		log.info("-- {}", userDTO.toString());
		
		// DB에서 수정처리
		userService.updateProc(userDTO);
		
		// 수정을 완료했다면 로그아웃 (>새로운 비밀번호로 로그인을 다시 하도록 함)
		return "redirect:/user/logout";
		
	}

}
