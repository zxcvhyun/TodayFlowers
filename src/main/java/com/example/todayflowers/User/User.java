package com.example.todayflowers.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Tables;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    private Integer id;

    private String useremail;
    @Size(min = 8)
    private String password;
    private String hpnumber;
    private String address;
    private String joindate;
    private char smsflag;

}
