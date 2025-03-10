package com.project.salemanagement.congifurations;

import com.project.salemanagement.filter.JwtTokenFilter;
import com.project.salemanagement.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("salemanagement/v1/**")
                            .permitAll()
//                    .requestMatchers(HttpMethod.GET, "salemanagement/v1/company/assignedPerson/**").permitAll()
//                            .requestMatchers(HttpMethod.GET, "salemanagement/v1/tasks/company/**").permitAll()
                            // Roles
//                            .requestMatchers(HttpMethod.GET, "api/v1/roles/**").hasAnyRole(Role.ADMIN, Role.USER)
//                            // categories
//                            .requestMatchers(HttpMethod.GET, "api/v1/categories/**").permitAll()
//                            .requestMatchers(HttpMethod.POST, "api/v1/categories/**").hasRole(Role.ADMIN)
//                            .requestMatchers(HttpMethod.PUT, "api/v1/categories/**").hasRole(Role.ADMIN)
//                            .requestMatchers(HttpMethod.DELETE, "api/v1/categories/**").hasRole(Role.ADMIN)
                    // products
                    .anyRequest().authenticated();
                });
//        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
//                CorsConfiguration configuration = new CorsConfiguration();
//                configuration.setAllowedOrigins(List.of("*")); // URL nguồn gốc được phép
//                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Phương thức được phép
//                configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "x-auth-token")); // Header được phép
//                configuration.setExposedHeaders(List.of("x-auth-token")); // Header được hiển thị trong response
//                //configuration.setAllowCredentials(true); // Cho phép cookie
//
//                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                source.registerCorsConfiguration("/**", configuration); // Áp dụng cấu hình cho tất cả endpoint
//                httpSecurityCorsConfigurer.configurationSource(source); // Đăng ký cấu hình
//            }
//        });
        return http.build();

    }
}
