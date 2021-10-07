package com.example.todayflowers.User;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Tables;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonFilter("UserInfo")
public class User {
    @Id
    private Integer id;
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String useremail;
    @Size(min = 8)
    private String password;
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String hpnumber;
    private String address;
    private String joindate;
    private char smsflag;
    private char emailflag;
}
