package net.scit.spring7.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.scit.spring7.dto.LoginUserDetails;
import net.scit.spring7.dto.ReplyDTO;
import net.scit.spring7.service.ReplyService;

@Slf4j
@RestController // 모든 메소드에 @ResponseBody가 붙는 기능~
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
	
	private final ReplyService replyService;
	
	/**
	 * 댓글 등록 메서드 
	 * @param replyDTO
	 * @param loginUserDetails
	 * @return
	 */
	@PostMapping("/replyInsert")
	public String replyInsert(
			@ModelAttribute ReplyDTO replyDTO
			, @AuthenticationPrincipal LoginUserDetails loginUser
			) {
		
		String loginId = loginUser.getUserId();
		replyDTO.setReplyWriter(loginId);
		
		replyService.replyInsert(replyDTO);
		
		return "success";
	}
	/**
	 * boardSeq에 해당하는 댓글 전체 조회 
	 * @param boardSeq
	 * @return
	 */
	@GetMapping("/replyAll")
	public List<ReplyDTO> replyAll(
			@RequestParam(name="boardSeq") Long boardSeq
			) {
		List<ReplyDTO> list = replyService.replyAll(boardSeq);
		
		return list;
		
	}
	/**
	 * replySeq 댓글 데이터 삭제 
	 * @param replySeq
	 * @return
	 */
	@GetMapping("/replyDelete")
	public String replyDelete(
			@RequestParam(name="replySeq") Long replySeq
			) {
		replyService.replyDelete(replySeq);
		
		return "success";
	}
	/**
	 * 수정을 위한 조회
	 * @return
	 */
	@GetMapping("/replyUpdate")
	public ReplyDTO replyUpdate(
			@RequestParam(name="replySeq") Long replySeq
			) {
		ReplyDTO replyDTO = replyService.replySelectOne(replySeq);
		
		return replyDTO;
		
	}
	/**
	 * 댓글 수정 처리
	 * @param replySeq
	 * @param updateReply
	 * @return
	 */
	@PostMapping("/replyUpdateProc")
	public String replyUpdateProc(
			@RequestParam(name="replySeq") Long replySeq,
			@RequestParam(name="updatedReply") String updateReply
			) {
		
		replyService.replyUpdateProc(replySeq, updateReply);
		
		return "Updated";
		
	}
	

}
