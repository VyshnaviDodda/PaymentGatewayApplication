package com.payment.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public AuthenticationProvider authProvider() 
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }
    
    public Long getCurrentMerchantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        String username = authentication.getName();
        return Long.parseLong(username);
    }

    @SuppressWarnings({ "removal", "deprecation" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeRequests(requests -> requests
                .requestMatchers("/gateway/createOrder").permitAll() // Allow access to createOrder for all
                .requestMatchers("/gateway/createMerchant").permitAll() // Require ADMIN role for createMerchant
                .requestMatchers("/gateway/").permitAll()
//                .requestMatchers("/").authenticated() // Require authentication for other /gateway endpoints
                .anyRequest().authenticated())
        .formLogin()
        .successHandler(authenticationSuccessHandler()) // Redirect on successful authentication
        .and()
        .sessionManagement(management -> management 
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/login?expired=true") // Redirect to login page on session expiration
                .and()
                .sessionFixation().migrateSession())

        .logout(logout -> logout
                .logoutSuccessHandler(logoutSuccessHandler()) // Redirect on successful logout
                .addLogoutHandler((request, response, authentication) -> {
                    response.setHeader("Clear-Site-Data", "\"cookies\"");
                }));

        return http.build();
    }

    @Bean
    public SessionInformationExpiredStrategy expiredSessionStrategy() {
        return new CustomSessionInformationExpiredStrategy("/login?expired=true");
    }

    // Define your custom SessionInformationExpiredStrategy
    public static class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

        private final String redirectUrl;

        public CustomSessionInformationExpiredStrategy(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        @Override
        public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
            HttpServletResponse response = event.getResponse();
            response.sendRedirect(redirectUrl);
        }
    }   
    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    
    
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                String merchantId = String.valueOf(getCurrentMerchantId());
                String targetUrl = "http://localhost:5500/Card/MerchantLoginPortal.html?merchantId=" + merchantId;
                response.sendRedirect(targetUrl);
            }
        };
    }
    
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                response.sendRedirect("/login?logout=true");
            }
        };
    }
}
