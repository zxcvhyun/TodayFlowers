package com.example.todayflowers.config;


import com.example.todayflowers.User.UserRepository;
import com.example.todayflowers.config.jwt.JwtAuthenticationFilter;
import com.example.todayflowers.config.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CorsFilter corsFilter;
    private final UserRepository userRepository;
//    // 시큐리티가 대신 로그인해주는데 password를 가로채는데
//    // 해당 password가 뭘로 해쉬화해서 회원가입이 되었는지 알아야
//    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교가능
    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
      // http.addFilterBefore(new MyFilter1(), SecurityContextPersistenceFilter.class);
        http
                .addFilter(corsFilter)
                .csrf().disable() //csrf 토큰 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션 사용 X
            .and()//인가 요청이 오면
                .formLogin()
                .disable()
                .httpBasic().disable() //

                .addFilter(new JwtAuthenticationFilter(authenticationManager())) //AuthenticationManger(파라미터)
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
                .authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/user/**")
                .permitAll()
                //.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/manager/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();

//                .loginProcessingUrl("/loginProc");
//                .antMatchers("/**","*/*") //해당 경로들은
//                                    .permitAll() //접근을 허용한다.
//                                    .anyRequest() //다른 모든 요청은
//                                    .permitAll() //인증이 되야 들어갈 수 있다.
//
//                                .and()
//                                    .formLogin() //로그인 폼은
//                                    .loginPage("/user/login") //로그인 페이지를 우리가 만든 페이지로 등록한다.
//                                    .loginProcessingUrl("/user/login") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해줌(서비스의 loadUserByName로 알아서)
//                                    .defaultSuccessUrl("/")//정상일떄
//                                    .permitAll()
//                                .and()
//                                    .logout() // 8
//                                    .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
//                                    .invalidateHttpSession(true) ;// 세션 날리;



        //중복 로그인
//        http.sessionManagement()
//                .maximumSessions(1)  //세션 최대 허용 수
//                .maxSessionsPreventsLogin(false); // false이면 중복 로그인하면 이전 로그인이 풀린다.
    }
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


}
