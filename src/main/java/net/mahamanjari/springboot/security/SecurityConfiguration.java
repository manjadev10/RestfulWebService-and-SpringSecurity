package net.mahamanjari.springboot.security;

import net.mahamanjari.springboot.repository.UserAccountRepository;
import org.hibernate.boot.model.internal.PkDrivenByDefaultMapsIdSecondPass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
//@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain1(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth ->
                auth
                        .requestMatchers("/","/welcome").permitAll()
                        .requestMatchers("/authenticated","/api/**").hasRole("ADMIN")
                        .anyRequest().denyAll()
                )
                .csrf(auth->auth.disable())
                //.formLogin(Customizer.withDefaults())
                .formLogin(login ->
                        login.loginPage("/login")
                                .permitAll()
                                .failureHandler(customAuthenticationFailureHandlerBean())
                )
                .logout(logout -> logout
                                .logoutSuccessUrl("/welcome")
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                                .permitAll()
                )
                .rememberMe(remember -> remember
                        .rememberMeParameter("remember-me")
                        .key("uniqueAndSecretKey")
                        .tokenValiditySeconds(500)
                        .rememberMeCookieName("remember-me-cookie")
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                .maximumSessions(1)
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    AuthenticationFailureHandler customAuthenticationFailureHandlerBean(){
        return new CustomAuthenticationFailedHandler();
    }


    UserDetailsService userService(UserAccountRepository userAccountRepository){
        return username ->   userAccountRepository.findByUsername(username).asUser();
    }


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(){

        UserDetails user = User
                .builder()
                .username("user")
                .password(passwordEncoder().encode("userpass"))
                .roles("USER")
                .build();

        UserDetails admin = User
                .builder()
                .username("admin")
                .password(passwordEncoder().encode("adminpass"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user,admin);
    }

}
