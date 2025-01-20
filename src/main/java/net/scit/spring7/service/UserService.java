package net.scit.spring7.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.scit.spring7.dto.UserDTO;
import net.scit.spring7.entity.UserEntity;
import net.scit.spring7.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * 전달받은 userId가 DB에 존재하는지 여부 확인
	 * @param userId
	 * @return
	 * */
	public boolean existId(String userId) {
		boolean result = userRepository.existsById(userId); // 존재하니, 존재하지 않니?
		log.info("아이디 존재여부: {}", result); // 가입하려면 false
		
		return !result;
	}
	/**
	 * 회원가입 처리
	 * @param userDTO
	 * @return
	 * */
	public boolean joinProc(UserDTO userDTO) {
		//비밀번호는 암호화해서 저장해야지?
		userDTO.setUserPwd(bCryptPasswordEncoder.encode(userDTO.getUserPwd()));
		
		UserEntity entity = UserEntity.toEntity(userDTO);
		userRepository.save(entity);	// 정보 저장 후 회원가입 완료
		
		boolean result = userRepository.existsById(userDTO.getUserId());	
		
		return result;
	}
	
	/**
	 * 입력한 비밀번호가 맞는지 확인
	 * @param userId
	 * @param userPwd
	 * @return
	 * */
	public UserDTO pwdCheck(String userId, String userPwd) {
		Optional<UserEntity> temp = userRepository.findById(userId);
		
		if (temp.isPresent()) {
			UserEntity entity = temp.get(); // 데이터 꺼내기
			String encodedPwd = entity.getUserPwd(); // 근데 이건 암호화된 비번
			// userPwd는 raw data. 저 둘을 비교하려면 아까 위에 정의해둔 bCrypt 어쩌고가 필요함. 거기 matches 메소드를 사용할 거야.
			
			boolean result = bCryptPasswordEncoder.matches(userPwd, encodedPwd); // 입력된 비번, DB 암호화된 비번이 같은 비번인지 확인
			
			if(result) {
				return UserDTO.toDTO(entity);
			}
		}
		
		return null;
	}

}






