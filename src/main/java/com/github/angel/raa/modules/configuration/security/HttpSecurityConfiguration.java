package com.github.angel.raa.modules.configuration.security;

import com.github.angel.raa.modules.configuration.filter.JwtAuthenticationFilter;
import com.github.angel.raa.modules.configuration.handler.CustomAccessDeniedHandler;
import com.github.angel.raa.modules.configuration.handler.CustomAuthenticationEntryPoint;
import com.github.angel.raa.modules.utils.enums.PermissionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

//@EnableWebSecurity
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class HttpSecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exceptions) -> {
                    exceptions.authenticationEntryPoint(customAuthenticationEntryPoint);
                    exceptions.accessDeniedHandler(customAccessDeniedHandler);
                })
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http://www.google.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Accept", "Origin"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(300L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authHttp) {
        authHttp.requestMatchers("/error").permitAll();
        authHttp.requestMatchers(HttpMethod.GET, "/products/**").permitAll();
        authHttp.requestMatchers(HttpMethod.GET, "/category/**").permitAll();
        authHttp.requestMatchers(HttpMethod.POST, "/customer/register").permitAll();
        authHttp.requestMatchers(HttpMethod.POST, "/administrator/register").permitAll();
        authHttp.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
        authHttp.requestMatchers(HttpMethod.GET, "/auth/validate-token").permitAll();


        // Products
        authHttp.requestMatchers(HttpMethod.POST, "/products/create-product")
                //.hasAnyRole(Role.ROLE_ADMINISTRATOR.name(), Role.ROLE_ASSISTANT_ADMINISTRATOR.name());
                .hasAuthority(PermissionEnum.CREATE_ONE_PRODUCT.name());
        authHttp.requestMatchers(HttpMethod.PUT, "/products/update/{productId}")
                //.hasAnyRole(Role.ROLE_ADMINISTRATOR.name(), Role.ROLE_ASSISTANT_ADMINISTRATOR.name());
               .hasAuthority(PermissionEnum.UPDATE_ONE_PRODUCT.name());

        authHttp.requestMatchers(HttpMethod.PUT, "/products/{productId}/disable")
                //.hasAnyRole(Role.ROLE_ADMINISTRATOR.name(), Role.ROLE_ASSISTANT_ADMINISTRATOR.name());
                   .hasAuthority(PermissionEnum.DISABLE_ONE_PRODUCT.name());
        authHttp.requestMatchers(HttpMethod.DELETE, "/products/delete/{productId}")
                //.hasAnyRole(Role.ROLE_ADMINISTRATOR.name(), Role.ROLE_ASSISTANT_ADMINISTRATOR.name());
                .hasAuthority(PermissionEnum.DELETE_ONE_PRODUCT.name());

        // Category
        authHttp.requestMatchers(HttpMethod.POST, "/category/create-category")
                .hasAuthority(PermissionEnum.CREATE_ONE_CATEGORIES.name());
        authHttp.requestMatchers(HttpMethod.PUT, "/category/update/{id}")
                .hasAuthority(PermissionEnum.UPDATE_ONE_CATEGORIES.name());
        authHttp.requestMatchers(HttpMethod.DELETE, "/category/delete/{id}")
                .hasAuthority(PermissionEnum.DELETE_ONE_CATEGORIES.name());
        authHttp.anyRequest().authenticated();
    }
}
