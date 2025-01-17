package net.scit.spring7.service;

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

}






