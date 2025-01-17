package net.scit.spring7.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.scit.spring7.entity.UserEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LoginUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String roles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(roles));
	}
	
	@Override
	public String getPassword() {
		return this.userPwd;
	}

	@Override
	public String getUsername() {
		return this.userId;
	}
	
	// 사용자 정의 getter(뷰단에서 써보려고~)
	public String getUserName() {
		return this.userName;
	}	
	
	public static LoginUserDetails toDTO(UserEntity entity) {
		return LoginUserDetails.builder()
				.userId(entity.getUserId())
				.userPwd(entity.getUserPwd())
				.userName(entity.getUserName())
				.email(entity.getEmail())
				.roles(entity.getRoles())
				.build();
	}

}
