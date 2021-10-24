package com.example.todayflowers.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.todayflowers.User.User;
import com.example.todayflowers.User.UserRepository;
import com.example.todayflowers.config.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 시큐리티가 filter를 가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는게 있음
// 권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 타게 됨
// 만약에 권한이나 인증이 필요한 주소가 아니라면 이 필터를 안탐

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    public JwtAuthorizationFilter (AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;

    }

    //인증이나 권한이 필요한 주소요청이 있을때 해당 필터를 타게 될 것
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소 요청이 됨 ");
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
        System.out.println("jwtHeader : " + jwtHeader);

        // header가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        //jwt 토큰을 검증해서 정상적인 사용자인지 확인
        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        //서명으로 username 들고옴
        String useremail = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token).getClaim("useremail").asString();

        //서명이 정상적으로 됨
        if (useremail != null) {
            System.out.println("useremail 정상");
            User userEntity = userRepository.findByUseremail(useremail);
            PrincipalDetail principalDetail = new PrincipalDetail(userEntity);
            System.out.println("principalDetails : " + principalDetail.getUsername() + "하하");
            //JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetail, null, principalDetail.getAuthorities());

            //강제로 시큐리티 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }
        chain.doFilter(request, response);


    }
}
