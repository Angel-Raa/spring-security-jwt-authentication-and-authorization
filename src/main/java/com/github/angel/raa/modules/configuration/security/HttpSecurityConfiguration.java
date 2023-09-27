package com.github.angel.raa.modules.configuration.security;

import com.github.angel.raa.modules.configuration.filter.JwtAuthenticationFilter;
import com.github.angel.raa.modules.utils.enums.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableWebSecurity
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class HttpSecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
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
                .hasAuthority(Permission.CREATE_ONE_PRODUCT.name());
        authHttp.requestMatchers(HttpMethod.PUT, "/products/update/{productId}")
                //.hasAnyRole(Role.ROLE_ADMINISTRATOR.name(), Role.ROLE_ASSISTANT_ADMINISTRATOR.name());
               .hasAuthority(Permission.UPDATE_ONE_PRODUCT.name());

        authHttp.requestMatchers(HttpMethod.PUT, "/products/{productId}/disable")
                //.hasAnyRole(Role.ROLE_ADMINISTRATOR.name(), Role.ROLE_ASSISTANT_ADMINISTRATOR.name());
                   .hasAuthority(Permission.DISABLE_ONE_PRODUCT.name());
        authHttp.requestMatchers(HttpMethod.DELETE, "/products/delete/{productId}")
                //.hasAnyRole(Role.ROLE_ADMINISTRATOR.name(), Role.ROLE_ASSISTANT_ADMINISTRATOR.name());
                .hasAuthority(Permission.DELETE_ONE_PRODUCT.name());

        // Category
        authHttp.requestMatchers(HttpMethod.POST, "/category/create-category")
                .hasAuthority(Permission.CREATE_ONE_CATEGORIES.name());
        authHttp.requestMatchers(HttpMethod.PUT, "/category/update/{id}")
                .hasAuthority(Permission.UPDATE_ONE_CATEGORIES.name());
        authHttp.requestMatchers(HttpMethod.DELETE, "/category/delete/{id}")
                .hasAuthority(Permission.DELETE_ONE_CATEGORIES.name());
        authHttp.anyRequest().authenticated();
    }
}
