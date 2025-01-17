package net.scit.spring7;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.scit.spring7.entity.BoardEntity;
import net.scit.spring7.repository.BoardRepository;

@SpringBootTest
class Spring7ApplicationTests {
	
	@Test
	void contextLoads() {	
	}
	
	@Autowired
	BoardRepository repo;
	
	@Test
	void testInsertBoard() {
		String[] w = {"김콩쥐", "김팥쥐", "박장화", "박홍련", "정햇님", "정달님", "조조", "조자룡","여포","동탁","차돌"};
		String[] c = {
				"나리나리 개나리 입에 따가 물고요",
				"송아지 송아지 얼룩송아지 엄마소도 얼룩소",
				"과수원길 아카시아 꽃이 활짝 폈네",
				"아기염소 여럿이 풀을 뜯고 놀아요",
				"옛날 옛적에 모두가 기억하기도 힘든 옛날 옛적에",
				"내 그대를 잊지 못하는 것은 아무리 힘들어도",
				"동해물과 백두산이 마르고 닳도록",
				"자세히 보면 사랑스럽다 오래 보아야 사랑스럽다",
				"둥근 해는 오늘도 미친 거 또 떴네",
				"이렇게 들어가야 하는데 왜 나 꽈찌쭈는 햄보칼 수가 업서",
				"너도 잘하는 거야 단지 그녀가 넘사벽이라서 그렇지"
				
				
		};
		
		for(int i=0; i<30; i++) {
			int idxW = (int)(Math.random() * w.length);
			int idxC = (int)(Math.random() * c.length);
			
			String writer = w[idxW];
			String content = c[idxC];
			String title = "제목 " + content.substring(0, 3) + "...";
			
			BoardEntity entity = new BoardEntity();
			entity.setBoardWriter(writer);
			entity.setBoardContent(content);
			entity.setBoardTitle(title);
			
			repo.save(entity);

		}
		System.out.println("저장 완료");
	}
}
