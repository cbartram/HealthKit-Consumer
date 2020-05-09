package com.healthkit.consumer.HealthConsumer.configuration.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Validates that the JWT is intended for the CraftyYak API by checking the
 * aud claim of the JWT token.
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    @Setter
    @Getter
    private String audience;

    /**
     * Validates that the aud claim in the JWT token
     * is meant for this API
     * @param jwt Jwt Jwt token object
     * @return OAuth2TokenValidatorResult
     */
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        log.info("Validating JWT Token AUD: {}", jwt.getClaims());
        log.info("JWT Info: Subject = {}\n Headers = {}\n Token Value = {} \n Audience = {}", jwt.getSubject(), jwt.getHeaders(), jwt.getTokenValue(), jwt.getAudience());
        if (jwt.getAudience().contains(audience)) {
            return OAuth2TokenValidatorResult.success();
        }
        return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "The required audience is missing", null));
    }
}
