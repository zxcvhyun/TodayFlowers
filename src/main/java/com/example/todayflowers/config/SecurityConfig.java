package com.example.todayflowers.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) {
//
//    }

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable() //csrf 토큰 비활성화
                .authorizeRequests() //인가 요청이 오면
                .antMatchers("/**") //해당 경로들은
                                    .permitAll() //접근을 허용한다.
                                    .anyRequest() //다른 모든 요청은
                                    .authenticated() //인증이 되야 들어갈 수 있다.

                                .and()
                                    .formLogin() //로그인 폼은
                                    .loginPage("/user/login") //로그인 페이지를 우리가 만든 페이지로 등록한다.
                                    .loginProcessingUrl("/user/login") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해줌(서비스의 loadUserByName로 알아서)
                                    .defaultSuccessUrl("/"); //정상일떄
        //중복 로그인
        http.sessionManagement()
                .maximumSessions(1)  //세션 최대 허용 수
                .maxSessionsPreventsLogin(false); // false이면 중복 로그인하면 이전 로그인이 풀린다.
    }
}
