package net.scit.spring7.dto;

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
public class UserDTO {
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String roles;
	private Boolean enabled;

	
	// Entity -> DTO
	public static UserDTO toDTO(UserEntity entity) {
		return UserDTO.builder()
				.userId(entity.getUserId())
				.userPwd(entity.getUserPwd())
				.userName(entity.getUserName())
				.email(entity.getEmail())
// 사용자가 자신의 role, enabled을 결정하는 것이 아니기 때문에 빌더에는 빌드하지 않음				
				.build();
	}
	

}
