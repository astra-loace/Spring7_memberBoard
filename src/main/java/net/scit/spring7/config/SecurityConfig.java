package net.scit.spring7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import net.scit.spring7.handler.CustomLogoutSuccessHandler;
import net.scit.spring7.handler.LoginFailureHandler;
import net.scit.spring7.handler.LoginSuccessHandler;

@Configuration // 설정파일임을 나타내는 Annotation
@EnableWebSecurity // 스프링 시큐리티로 관리됨을 나타내는 Annotation
@RequiredArgsConstructor
public class SecurityConfig {

	private final LoginSuccessHandler loginSuccessHandler; // 로그인 성공 처리 핸들러
	private final LoginFailureHandler loginFailureHandler; // 로그인 실패 처리 핸들러
	private final CustomLogoutSuccessHandler logoutHandler;

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
				.successHandler(loginSuccessHandler)
//				바로 위 코드를 아래의 주석 코드로 대체 가능. 
//				.successHandler((request, response, authentication) -> {
//					authentication.getAuthorities().forEach(auth -> {
//			            if (auth.getAuthority().equals("ROLE_ADMIN")) {
//			                response.sendRedirect("/admin/adminpage");
//			            } else if (auth.getAuthority().equals("ROLE_USER")) {
//			                response.sendRedirect("/");
//			            }
//			        });
//				})
//				.defaultSuccessUrl("/")				
				.failureHandler(loginFailureHandler)
//				.failureUrl("/user/login?error=true")	// failureHandler가 있어서 필요없는 코드 
				.permitAll());
	
	// logout 설정 
	http
		.logout((auth) -> auth
				.logoutUrl("/user/logout")				
//				.logoutSuccessUrl("/")		
				.logoutSuccessHandler(logoutHandler)
//		        .logoutSuccessHandler((request, response, authentication) -> {
//		            String refererUrl = request.getHeader("Referer");
//		            String redirectUrl = (refererUrl != null && refererUrl.startsWith(request.getContextPath()))
//		                    ? refererUrl : "/"; // Referer가 동일한 도메인인지 확인
//		            response.sendRedirect(redirectUrl); // 리다이렉트 처리
//		        })
				.invalidateHttpSession(true)	
				.clearAuthentication(true));
			
		
		http
			.csrf((auth) -> auth.disable());
		
		
		return http.build();
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();	
	}
}
