package com.fastcampus.sns.configuration;

import com.fastcampus.sns.configuration.filter.JwtTokenFilter;
import com.fastcampus.sns.exception.CustomAuthenticationEntryPoint;
import com.fastcampus.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    @Value("${jwt.secret-key}")
    private String key;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                        .antMatchers("/api/*/users/join", "/api/*/users/login").permitAll()
                        .antMatchers("/api/**").authenticated()
                        .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(key, userService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        ;
        //JWTTOkenFilter : 토큰을 분석해 유저의 정보 받아옴
        // addFilter : UsernamePasswordAuthenticationFilter.class 필터 대신 직접만든 인증로직을 가진 필터를 생성하고 사용한다.
        //.addFilterBefore : 지정된 필터 앞에 커스텀 필터를 추가 new JwtTokenFilter() 가 Username...보다 먼저 실행된다.
        //TODO : 에러처리-> 특정 엔트리 포인트로 가도록
        //          .exceptionHandling()
        //          .authenticationEntryPoint()

        super.configure(http);
    }
}
