package net.scit.spring7.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.scit.spring7.dto.UserDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name="board_user")
public class UserEntity {
	
	@Id
	@Column(name="user_id")
	private String userId;
	
	@Column(name="user_pwd", nullable = false)
	private String userPwd;
	
	@Column(name="user_name", nullable = false)
	private String userName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="roles")
	@Builder.Default
	private String roles = "ROLE_USER";
	
	@Column(name="enabled")
	@Builder.Default
	private Boolean enabled = true;
	
	public static UserEntity toEntity(UserDTO dto) {
		return UserEntity.builder()
				.userId(dto.getUserId())
				.userPwd(dto.getUserPwd())
				.userName(dto.getUserName())
				.email(dto.getEmail())
// 사용자가 자신의 role, enabled을 결정하는 것이 아니기 때문에 빌더에는 빌드하지 않음				
				.build();
	}
		

}
