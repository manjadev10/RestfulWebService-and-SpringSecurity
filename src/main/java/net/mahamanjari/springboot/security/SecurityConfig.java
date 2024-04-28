package net.mahamanjari.springboot.security;

import net.mahamanjari.springboot.entity.UserAccount;
import net.mahamanjari.springboot.repository.UserAccountRepository;
import net.mahamanjari.springboot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {

    CommandLineRunner initUsers(UserAccountRepository repository){
        return args -> {
            repository.save(new UserAccount("user","password","ROLE_USER"));
            repository.save(new UserAccount("admin","admin","ROLE_ADMIN"));
        };
    }

    @Bean
    UserDetailsService userService(UserAccountRepository userAccountRepository){
        return username -> userAccountRepository.findByUsername(username).asUser();

    }

      //Hard coded user accounts
//    public UserDetailsService userDetailsService(){
//
//        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build()
//                );
//
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("password")
//                        .roles("ADMIN")
//                        .build()
//                );
//
//        return userDetailsManager;
//    }

    @Bean
    SecurityFilterChain defaultSecurityChain(HttpSecurity http) throws Exception{
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin(formLogin -> formLogin.loginPage("/login").permitAll() );
        return http.build();
    }
}
