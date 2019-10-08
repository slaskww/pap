package com.example.paginationandprojection.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/*
    Nie dodajemy adnotacji @EnableWebSecurity, bo to zostało już zrobione
    przez Spring Boot
 */
@Configuration
public class SecurityLayerConfiguration extends WebSecurityConfigurerAdapter {

    private final static String AUTHORITIES_BY_USERNAME_QUERY = "Select username, role_name From example_users_roles Where username = ?";
    private final static String USERS_BY_USERNAME_QUERY = "Select username, password, enabled From example_users Where username = ?";



    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private final DataSource dataSource;

    public SecurityLayerConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //dołączamy konfigurację uwierzytelniania użytkowników na podstawie danych z bazy (jdbc authentication)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


   /*     auth.inMemoryAuthentication()
                .withUser("user").password("{noop}pass").roles("USER")
                .and()
                .withUser("manager").password("{noop}pass").roles("USER", "MANAGER")
                .and()
                .withUser("admin").password("{noop}s3cr3t").roles("ADMIN");*/


        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY)
                .usersByUsernameQuery(USERS_BY_USERNAME_QUERY);
    }

    //konfiguracja dostępu do ścieżek na podstawie ról
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Zabezpieczenie endpointów z projektu Spring Boot Actuator
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/register", "/register/**").permitAll()
                .antMatchers("/login", "/logout").authenticated()
                .antMatchers("/user", "/user/**").hasRole("USER")
                .antMatchers("/manager", "/manager/**").hasRole("MANAGER")
                .antMatchers("/admin", "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
             // .loginPage()
                .and()
             // .logout().logoutUrl("/main/logout") <-- URL który wyzwala operację wylogowania (default is "/logout")
                .httpBasic();//Configures HTTP Basic authentication
    }

    /**
     * gdybyśmy korzystali z wbudowanej bazy danych H2, to moglibyśmy ustawić
     * wyłączenie z obsługi Spring Security zarządzanie bazą H2:
     */

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2c/**");    }
}
