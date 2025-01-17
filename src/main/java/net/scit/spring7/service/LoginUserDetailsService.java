package net.scit.spring7.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.scit.spring7.dto.LoginUserDetails;
import net.scit.spring7.entity.UserEntity;
import net.scit.spring7.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
// @Service 안 써도 됨! 아래에 보면 이미 DetailsService를 오버라이딩 해서
public class LoginUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	
	// 오버라이드는 상속받은 메소드를 다시 재정의하는 행위
	// 매개변수명과 접근지정자보다 큰 지정자로 바꾸는 것만 가능
	// loadUserByUsername() 비밀번호 비교는 명시적으로 없다.
	@Override
	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException {
		// Optional로 반환하는 메소드! orElseThrow() 연결해서 사용 가능
		// 단, orElseThrow()를 쓰려면 반환되는 값이 무조건 Optional로 반환해야 한다.
		
		// 그런데 이렇게 하면 Optional로 받을 필요 없지롱: Entity로 받아버리면~
		UserEntity userEntity = userRepository.findById(userId)
			.orElseThrow(() -> {
				throw new UsernameNotFoundException("ID나 비밀번호가 틀렸습니다.");
			});
		
		return LoginUserDetails.toDTO(userEntity);
	

	}

}

