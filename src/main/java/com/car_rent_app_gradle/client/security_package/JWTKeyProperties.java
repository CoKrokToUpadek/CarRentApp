package com.car_rent_app_gradle.client.security_package;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@ConfigurationProperties(prefix="keypair")
public record JWTKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
