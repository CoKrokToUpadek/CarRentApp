package com.car_rent_app_gradle.client.security_package;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@Aspect
@Component
public class TokenValidator {

    JwtDecoder jwtDecoder;
    @Autowired
    public TokenValidator(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Around("execution(* com.car_rent_app_gradle.controller.*.*(..))")
    public Object validateToken(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String jwt = bearerToken.substring(7);
                Jwt jwtToken=jwtDecoder.decode(jwt);
                Instant tokenExpireTime= jwtToken.getExpiresAt();
                long timerNow=Instant.now().getEpochSecond();
                long timerToken=tokenExpireTime.getEpochSecond();
                if (timerNow-(timerToken+3600)>=0){
                    return new ResponseEntity<>("Token expired", HttpStatus.UNAUTHORIZED);
              }
            }
      //  }
          return  pjp.proceed();
    }
}
