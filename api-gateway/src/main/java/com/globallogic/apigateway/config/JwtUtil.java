package com.globallogic.apigateway.config;

import java.util.Date;   

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {


	private String secret = "dadsnrgsthghdjgmdhgxfxmgfdyjmhgtxgdcjbngkjghchhfjdlshvfishdvuhzsuhvujksdbvkub";

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();

    }
    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

}
