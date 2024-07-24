package com.beyond.board.common;

import com.beyond.board.author.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// 토큰 방식
@Configuration
@EnableWebSecurity // spring Security 설정을 customizing 하기 위함.
@EnableGlobalMethodSecurity(prePostEnabled = true) // pre : 사전 , post : 사후 인증검사
public class SecurityConfig {
    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                // csrf 공격에 대해서 설정은 하지 않겠다. 라는 의미
                .csrf().disable()
                .authorizeRequests()
                //아래 antMatchers는 인증 제외
                .antMatchers("/", "/author/register", "/author/login-screen")
                .permitAll()
                // 그 외 요청은 모두 인증 필요
                .anyRequest().authenticated()
                .and()
                // 만약 세션 로그인이 아니라 , 토큰 로그인인 경우
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .formLogin()
                .loginPage("/author/login-screen")
                // doLogin 메서드는 스프링에서 미리 구현
                .loginProcessingUrl("/doLogin")
                // 다면 , doLogin에 넘겨줄 email, password 속성명은 별도 지정
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
                // 시큐리티에서 구현된 doLogout 기능 그대로 사용
                .logoutUrl("/doLogout")
                .and()
                .build();
    }
}
