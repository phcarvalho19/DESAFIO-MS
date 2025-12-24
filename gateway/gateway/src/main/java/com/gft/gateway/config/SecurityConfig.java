package com.gft.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange

                        .pathMatchers(
                                "/actuator/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // 🟦 AGENDAMENTO
                        .pathMatchers(HttpMethod.POST,
                                "/pacientes",
                                "/consultas",
                                "/cadastro/exames"
                        ).hasAnyRole("USER", "ADMIN")

                        // 🟩 CLÍNICA — leitura
                        .pathMatchers(HttpMethod.GET,
                                "/clinica/consultas",
                                "/clinica/consultas/*"
                        ).hasAnyRole("USER", "MEDICO", "ADMIN")

                        // 🟩 CLÍNICA — médico
                        .pathMatchers("/clinica/**")
                        .hasAnyRole("MEDICO", "ADMIN")

                        // 🟧 LAB — marcar exame
                        .pathMatchers(HttpMethod.POST,
                                "/laboratorio/exames/marcar"
                        ).hasAnyRole("USER", "MEDICO", "ADMIN")

                        // 🟧 LAB — resto
                        .pathMatchers("/laboratorio/**")
                        .hasAnyRole("MEDICO", "ADMIN")

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        new ReactiveJwtAuthenticationConverterAdapter(
                                                new JwtAuthConverter()
                                        )
                                )
                        )
                );

        return http.build();
    }
}