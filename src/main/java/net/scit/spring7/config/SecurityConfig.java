package net.scit.spring7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정파일임을 나타내는 Annotation
@EnableWebSecurity // 스프링 시큐리티로 관리됨을 나타내는 Annotation
public class SecurityConfig {

	// 요청 URL을 제어하는 메서드
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((auth) -> auth
			.requestMatchers(
				"/",
				"/user/join",
				"/user/login",
				"/user/joinProc",
				"/user/loginProc",
				"/user/idCheck",
				"/board/boardList",
				"/board/boardDetail",
				"/board/download",
				"/images/**",
				"/js/**",
				"/css/**").permitAll()								
			.requestMatchers("/admin").hasRole("ADMIN")				
			.requestMatchers("/user/mypage/**").hasAnyRole("ADMIN", "USER")	
			.anyRequest().authenticated());		
		
		
		http
			.formLogin((auth) -> auth
					.loginPage("/user/login")				
					.loginProcessingUrl("/user/loginProc")	
					.usernameParameter("userId")		
					.passwordParameter("userPwd")		
					.defaultSuccessUrl("/")				
					.failureUrl("/user/login?error=true")	
					.permitAll());
		
		http
			.logout((auth) -> auth
					.logoutUrl("/user/logout")				
					.logoutSuccessUrl("/")				
					.invalidateHttpSession(true)		
					);
		
		http
			.csrf((auth) -> auth.disable());
		
		
		return http.build();
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();	
	}
}
