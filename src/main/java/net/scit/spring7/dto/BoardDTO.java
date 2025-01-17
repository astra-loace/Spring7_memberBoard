package net.scit.spring7.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.scit.spring7.entity.BoardEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class BoardDTO {
	private Long boardSeq;
	private String boardWriter;
	private String boardTitle;
	private String boardContent;
	private int hitCount;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	// 첨부파일을 받는 타입
	private MultipartFile uploadFile;
	// 업로드 파일이 있을 경우 View에서 사용하기 위해
	private String originalFileName;

	private String savedFileName;
	
	// 댓글 개수(BoardEntity에서 @Formula를 이용해서 조회)
	private int replyCount;
	
	// entity -> DTO
	public static BoardDTO toDTO(BoardEntity boardEntity) {
		return BoardDTO.builder()
				.boardSeq(boardEntity.getBoardSeq())
				.boardWriter(boardEntity.getBoardWriter())
				.boardTitle(boardEntity.getBoardTitle())
				.boardContent(boardEntity.getBoardContent())
				.hitCount(boardEntity.getHitCount())
				.createDate(boardEntity.getCreateDate())
				.updateDate(boardEntity.getUpdateDate())
				.originalFileName(boardEntity.getOriginalFileName())
				.savedFileName(boardEntity.getSavedFileName())
				.replyCount(boardEntity.getReplyCount())
				.build();	

	}
}
