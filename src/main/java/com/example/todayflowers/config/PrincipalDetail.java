package com.example.todayflowers.config;

import com.example.todayflowers.User.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetail  implements UserDetails {
    private User user;

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUseremail();
    }

    //계정이 만료되지 않았는지 리턴 (true: 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정이 잠겨있는지 않았는지 리턴 (true:잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 았는지 리턴 (true:만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화(사용가능)인지 리턴 (true:활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(r->{
            authorities.add(() -> r);
        });
        return authorities;
        //람다식을 풀어서쓰면 이런느낌
//        user.getRoleList().forEach(new User<String>() {
//            @Override
//            public void accept(String s) {
//                authorities.add(new GrantedAuthority() {
//                    @Override
//                    public String getAuthority() {
//                        return s;
//                    }
//                });
//            }
//        });

    }

}
