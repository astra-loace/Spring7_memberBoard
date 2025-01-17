package net.scit.spring7.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.scit.spring7.entity.ReplyEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Builder
public class ReplyDTO {
	private Long replySeq;
	private Long boardSeq;
	private String replyWriter;
	private String replyContent;
	private LocalDateTime createDate;
	
	// bSeq의 정체? 엔티티에서 DTO로 변환하면서 가져온 값
	public static ReplyDTO toDTO(ReplyEntity entity, Long bSeq) {
		return ReplyDTO.builder()
				.replySeq(entity.getReplySeq())
				.boardSeq(bSeq)
				.replyWriter(entity.getReplyWriter())
				.replyContent(entity.getReplyContent())
				.createDate(entity.getCreateDate())
				.build();
	}

}
