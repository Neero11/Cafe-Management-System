package com.inn.cafe.security;


import com.inn.cafe.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter authFilter;
    @Autowired
    private UserService userService;

    @Autowired
    private  PasswordEncoder passwordEncoder;


    public SecurityConfig() {
    }

    private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/auth/login",
            "/users/**",


    };

    // Password Encoding

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


//    @Bean
//    SecurityFilterChain MobileSecurityFilterChain(HttpSecurity http) throws Exception {
//
//        return  http
//                    .csrf(AbstractHttpConfigurer::disable)
//
//                .securityMatcher("/mobile/**")
//                    .authorizeHttpRequests(req -> {
//                        req.requestMatchers("/mobile/sign-up/**","/mobile/sign-in/**").permitAll();
//                        req.anyRequest().authenticated();
//                    })
//                    .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                    .addFilterBefore(mobileJwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
//                    .build();
//    }

    @Bean
    SecurityFilterChain AdminSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(AUTH_WHITE_LIST).permitAll();
                    req.anyRequest().authenticated();
                })
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exc->{
//  Meshan comment ayaan saray Log in broblem ihestay
//  exc.accessDeniedHandler(HttpStatus.UNAUTHORIZED);
                })
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        var configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://172.16.78.64:8080/","http://localhost:8080","http://localhost:3000","http://192.168.0.109:4001"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "content-type", "x-requested-with", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "x-auth-token", "x-app-id", "Origin", "Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
//        configuration.setAllowCredentials(true);
//        var source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}