package com.healthkit.consumer.HealthConsumer.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Manages Spring's OAuth security
 * configuration and restricts api routes to have a
 * valid bearer token
 */
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Bean
    protected JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuer);
        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                JwtValidators.createDefaultWithIssuer(issuer),
                new AudienceValidator(audience))
        );
        return jwtDecoder;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Sets Basic Http Auth when attempting to retrieve an access token
     * through the OAuth server endpoint i.e all paths that have /oauth/** in them.
     * This also permits all requests to /ws/** to allow websocket connectivity without authorization. (Fix this later)
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges ->
                    exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers("/ws/**").permitAll()
                        .pathMatchers("/oauth/**")
                            .authenticated().and().httpBasic()
                        .and()
                        .authorizeExchange()
                        .anyExchange().authenticated()

                )
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
                .build();
    }

    /**
     * Configures the username and password that is
     * used for Basic Auth on the /oauth/token endpoint
     * @param username String username injected by Spring Application context
     * @param password String password set it properties and injected by spring
     * @return MapReactiveUserDetailsService Object
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService(@Value("${spring.security.username}") final String username, @Value("${spring.security.pass}") final String password) {
        UserDetails user = User
                .withUsername(username)
                .password(passwordEncoder().encode("{noop}" + password))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }
}
