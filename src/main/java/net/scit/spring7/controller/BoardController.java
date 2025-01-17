package net.scit.spring7.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.scit.spring7.dto.BoardDTO;
import net.scit.spring7.dto.LoginUserDetails;
import net.scit.spring7.entity.BoardEntity;
import net.scit.spring7.service.BoardService;
import net.scit.spring7.util.FileService;
import net.scit.spring7.util.PageNavigator;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;
	
	@Value("${user.board.pageLimit}")
	private int pageLimit;
	
	/**
	 * 게시글 목록 요청
	 * @
	 * @param model
	 * @return
	 */
	@GetMapping("/boardList")
	public String boardList(
			@AuthenticationPrincipal LoginUserDetails loginUser,
			@PageableDefault(page=1) Pageable pageable,
			@RequestParam(name="searchItem", defaultValue = "boardTitle") String searchItem,
			@RequestParam(name="searchWord", defaultValue = "") String searchWord,
			Model model) {
//		log.info("== searchWord: {}", searchWord);
//		log.info("== searchItem: {}", searchItem);
		
		// 2) 페이징 기능+ 검색 기능
		Page<BoardDTO> list = boardService.selectAll(pageable, searchItem, searchWord);
		
		int totalPages = list.getTotalPages();	// DB가 계산해준 총 페이지 수
		int page = pageable.getPageNumber();	// 현재 사용자가 요청한 페이지
		
		PageNavigator navi = new PageNavigator(pageLimit, page, totalPages);
		
		// 1) 검색 기능 추가
//   searchItem과 searchWord는 null인 상태로 service로 전달하면 안 된다.
		//selectAll을 수정		
		//List<BoardDTO> list = boardService.selectAll(searchItem, searchWord);
		
		model.addAttribute("list", list);
		model.addAttribute("searchItem", searchItem);
		model.addAttribute("searchWord", searchWord);
//		model.addAttribute("totalBoard", pageLimit * (page - 1));
		model.addAttribute("navi", navi);
		
		// 로그인 했니 안 했니 확인해서, 로그인하면 그 사람의 이름 출력
		if(loginUser != null) {
			model.addAttribute(loginUser.getUserName());
		}
		
		/*
		 * model.addAttribute("totalElements", list.getTotalElements();
		 * */
		
		return "board/boardList";
	}

	/**
	 * 게시글 쓰기 화면 요청
	 * 
	 * @return
	 */
	@GetMapping("/boardWrite")
	public String boardWrite() {
		return "board/boardWrite";
	}

	/**
	 * 게시글 등록 요청
	 * 
	 * @param boardDTO
	 * @return
	 */
	@PostMapping("/boardWrite")
	public String boardWrite(@ModelAttribute BoardDTO boardDTO) {
		// DB 등록
//		log.info("========= {}", boardDTO.toString());
/*		MultipartFile file = boardDTO.getUploadFile();
		log.info("===파일: {}", file);
		log.info("===파일: {}", file.getContentType());
		log.info("===파일: {}", file.getOriginalFilename());
		log.info("===파일: {}", file.getSize());
		log.info("===파일: {}", file.isEmpty());
*/		
		boardService.insertBoard(boardDTO);
		
		return "redirect:/board/boardList";
	}

	/**
	 * 게시글 상세 보기 화면 요청 & 조회수 증가
	 * 
	 * @param boardSeq
	 * @param model
	 * @return
	 */
	@GetMapping("/boardDetail")
	public String boardDetail(
			@RequestParam(name="searchItem", defaultValue = "boardTitle") String searchItem,
			@RequestParam(name="searchWord", defaultValue = "") String searchWord,
			@RequestParam(name = "boardSeq") Long boardSeq
			, Model model) {
		// DB에 boardSeq에 해당하는 하나의 게시글을 조회
		BoardDTO boardDTO = boardService.selectOne(boardSeq);
		boardService.incrementHitCount(boardSeq);

		model.addAttribute("board", boardDTO);
		model.addAttribute("searchItem", searchItem);
		model.addAttribute("searchWord", searchWord);
		
		return "board/boardDetail";
	}

	/**
	 * boardSeq 번호에 해당하는 게시글 데이터 삭제
	 * 
	 * @param boardSeq
	 * @return
	 */
	@GetMapping("/boardDelete")
	public String boardDelete(
			@RequestParam(name="searchItem", defaultValue = "boardTitle") String searchItem,
			@RequestParam(name="searchWord", defaultValue = "") String searchWord,
			@RequestParam(name = "boardSeq") Long boardSeq,
			RedirectAttributes reat) {
		boardService.deleteOne(boardSeq);
		reat.addAttribute("searchItem", searchItem);
		reat.addAttribute("searchWord", searchWord);
		return "redirect:/board/boardList";
	}

	/**
	 * 게시글 수정화면 요청
	 * boardSeq 번호에 해당하는 데이터 조회 후 수정화면에 반영
	 * 
	 * @param boardSeq
	 * @param model
	 * @return
	 */
	@GetMapping("/boardUpdate")
	public String boardUpdate(
			@RequestParam(name="searchItem", defaultValue = "boardTitle") String searchItem,
			@RequestParam(name="searchWord", defaultValue = "") String searchWord,
			@RequestParam(name = "boardSeq") Long boardSeq, Model model) {
		
		BoardDTO boardDTO = boardService.selectOne(boardSeq);
		
		model.addAttribute("board", boardDTO);
		model.addAttribute("searchItem", searchItem);
		model.addAttribute("searchWord", searchWord);
		
		return "board/boardUpdate";
	}

	/**
	 * 게시글 수정 처리 요청 
	 * @param boardDTO
	 * @return
	 */
	@PostMapping("/boardUpdate")
	public String boardUpdate(
			@ModelAttribute BoardDTO boardDTO,
			@RequestParam(name="searchItem", defaultValue = "boardTitle") String searchItem,
			@RequestParam(name="searchWord", defaultValue = "") String searchWord,
			RedirectAttributes rttr) {
		
		log.info("==== 수정데이터: {}", boardDTO.toString());
		
		boardService.updateBoard(boardDTO);
		
		rttr.addAttribute("searchItem", searchItem);
		rttr.addAttribute("searchWord", searchWord);
		
		return "redirect:/board/boardList";
	}
	/**
	 * 쓰레기통 아이콘을 클릭하여 파일만 삭제하는 작업 
	 * @param boardSeq
	 * @return
	 */
	@GetMapping("/deleteFile")
	public String deleteFile(
			@RequestParam(name="boardSeq") Long boardSeq,
			@RequestParam(name="searchItem", defaultValue = "boardTitle") String searchItem,
			@RequestParam(name="searchWord", defaultValue = "") String searchWord,
			RedirectAttributes rttr) {

		BoardDTO boardDTO = boardService.selectOne(boardSeq);
		
		String savedFileName = boardDTO.getSavedFileName();
		String fullpath = uploadPath + "/" + savedFileName;

		// 1) 물리적으로 파일을 삭제 (경로 그대로 따서)
		boolean result = FileService.deleteFile(fullpath);
		log.info("삭제결과: {}", result);
		
		// 2) DB도 수정 --> file컬럼 두 개의 값을 null로
		boardService.deleteFile(boardSeq);
		
		// 서치아이템, 서치워드도 보내줘야지...~
		rttr.addAttribute("boardSeq", boardSeq);
		rttr.addAttribute("searchItem", searchItem);
		rttr.addAttribute("searchWord", searchWord);
		
		// 클라이언트에서 보내야 함
		
		return "redirect:/board/boardDetail";
	}
	
	/**
	 * 파일 다운로드
	 * @param boardSeq
	 * @param response
	 * @return
	 * */
	@GetMapping("/download")
	public String download(
			@RequestParam(name="boardSeq") Long boardSeq
			, HttpServletResponse response ) {
		BoardDTO boardDTO = boardService.selectOne(boardSeq);
		
		String originalFileName = boardDTO.getOriginalFileName();
		String savedFileName = boardDTO.getSavedFileName();
		String fullpath = uploadPath + "/" + savedFileName;
		
		try {
			String tempName = URLEncoder.encode(originalFileName
					, StandardCharsets.UTF_8.toString());
			
			response.setHeader("Content-Disposition", "attachment;filename=" + tempName);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		FileInputStream fin = null; // 로컬에서  input : file
		ServletOutputStream fout = null;			// 네트워크로 output: servlet
		
		try {
			fin = new FileInputStream(fullpath);
			fout = response.getOutputStream();
			
			FileCopyUtils.copy(fin, fout);
			
			fout.close();
			fin.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

