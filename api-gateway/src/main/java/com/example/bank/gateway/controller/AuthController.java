package com.example.bank.gateway.controller;

import com.example.bank.gateway.config.JwtConfig.JwtProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

/**
 * Architecture/Tech: Spring Security + JWT token issuer endpoint for demo integration.
 */
@Validated
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "JWT token issuance for secured gateway routes")
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;

    public AuthController(JwtEncoder jwtEncoder, JwtProperties jwtProperties) {
        this.jwtEncoder = jwtEncoder;
        this.jwtProperties = jwtProperties;
    }

    @PostMapping("/token")
    @Operation(summary = "Issue a signed JWT for gateway access")
    public Mono<ResponseEntity<Map<String, String>>> issueToken(@Valid @RequestBody TokenRequest request) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(jwtProperties.getExpirySeconds()))
                .subject(request.subject())
                .claim("scope", "platform.read platform.write")
                .build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build(), claims))
                .getTokenValue();
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(Map.of("access_token", token, "token_type", "Bearer")));
    }

    /**
     * Architecture/Tech: Jakarta Bean Validation request contract.
     */
    public record TokenRequest(@NotBlank String subject) {
    }
}
