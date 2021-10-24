package com.example.todayflowers.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.todayflowers.User.User;
import com.example.todayflowers.config.PrincipalDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import com.auth0.jwt.JWT;
import static org.springframework.security.config.Elements.JWT;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
// /login 요청해서 username, password를 포스트로 전송하면
//UsernamePasswordAuthenticationFilter 필터 동작
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중 ");

        //1. username, password 받아서
        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while ((input = br.readLine()) != null) {
//                System.out.println(input);
//            }
            //json 데이터 파싱
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            //2. 정상인지 로그인 시도 authenticationManager로 로그인시도를 하면 PrincipalDetailsService 호출
            //   ->PrincipalDetailsService 의 loadUserByUsername() 함수 실행
            // DB에 있는 username과 password가 일치한다.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUseremail(), user.getPassword());
            //로그인 정보가 담김
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            //authentication 객체가 session 영역에 저장됨 => 로그인이 되었다는 뜻
            PrincipalDetail principalDetails = (PrincipalDetail) authentication.getPrincipal();
            System.out.println("로그인 완료 :" + principalDetails.getUser().getUseremail());

            //authentication 객체가 session 영역에 저장하고 그 방법이 return
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3. 리턴된 PrincipalDetail를 세션에 담고 (권한 관리를 위해서)
          
        //4. JWT 토큰을 만들어서 응답해주면 됨.
        return null;
    }
    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 함수 실행
    //JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 응답해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException{
        System.out.println("successfulAuthentication 실행됨 : 인증이 완료 되었다는 뜻임 ");

        PrincipalDetail principalDetail = (PrincipalDetail) authResult.getPrincipal();

        //RSA 방식은 아니고 Hash암호 방식
        String jwtToken = com.auth0.jwt.JWT.create()
                .withSubject("토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000*30)))
                .withClaim("id", principalDetail.getUser().getId())
                .withClaim("useremail", principalDetail.getUser().getUseremail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));


        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

    }


}
