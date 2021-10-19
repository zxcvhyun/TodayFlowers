package com.example.todayflowers.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 토큰이 넘어오면 인증이 되게하고 아니면 필터를 못타게 해서 컨트롤러로 진입하지 못하게 한다.
        // id, pw가 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들고 응답을 해줌
        // 요청할 때마다 header 에 Authorization 에  value 값으로 토큰을 가져오고
        // 토큰이 넘어오면 내가 만든 토큰이 맞는지 검증한다.(RSA, HS256)
        if (req.getMethod().equals("POST")) {
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);
            System.out.println("필터1");
            if (headerAuth.equals("cos")) {
                chain.doFilter(req,res);
            }else {
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
        }


    }
}
