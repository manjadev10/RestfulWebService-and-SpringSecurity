package net.mahamanjari.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class UserAccount {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Transient
    private List<GrantedAuthority> authorityList = new ArrayList<>();

    public UserAccount(String username,String password,String authority){
        this.username = username;
        this.password = password;
        this.authorityList.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return authority;
            }
        });
    }

    public UserAccount(){}


    public UserDetails asUser(){
        return User
                .withDefaultPasswordEncoder()
                .username(getUsername())
                .password(getPassword())
                .roles("ADMIN")
                .build();
    }
}
