package com.fastcampus.sns.configuration.filter;

import com.fastcampus.sns.model.User;
import com.fastcampus.sns.service.UserService;
import com.fastcampus.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter
// 요청시마다 필터 작동
{
    private final String key;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request가지고 인증
        //get header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer ") )//jwt token은 bearer안에 담겨있음으로 헤더에도 bear담겨야함
        {
            log.error("Error occurs while getting header. header is null or invalid");
            filterChain.doFilter(request, response); //마저 필터에 추가
            return;
        }
        try{
            final String token = header.split(" ")[1].trim();
            //complete : check token is valid(만료시간 이내)
            if(JwtTokenUtils.isExpired(token, key)){
                log.error("Error occurs bcs key is expired.");
                filterChain.doFilter(request, response); //마저 필터에 추가
                return;
            }

            //complete : implimatation get username from token
            String userName = JwtTokenUtils.getUserName(token, key);
            //complete : userName 유효성 valid check
            User user = userService.loadUserByUserName(userName);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    //complete principal, credentials, authorities
                    user, null, user.getAuthorities());
                    //enum.toString -> ADMIN(0) -> Admin 으로 변경
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //request 정보 넣어서 보내줌.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //유저네임 끼워서 보내줌
        }catch (RuntimeException e){
            log.error("Error occurs while validating.{}", e.toString());
            filterChain.doFilter(request, response); //마저 필터에 추가
            return;
        }
        filterChain.doFilter(request, response);
    }
}
