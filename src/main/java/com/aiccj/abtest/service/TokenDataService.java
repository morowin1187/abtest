package com.aiccj.abtest.service;

import com.aiccj.abtest.common.enums.BusinessType;
import com.aiccj.abtest.common.exception.BusinessException;
import com.aiccj.abtest.common.exception.BusinessMsg;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Service
public class TokenDataService {

    private static final String USER_TOKEN_SECRET = "kyLnG5KD6OBxKnp7cUwLBn91ziXeutDxvi7BU33dJ0Ppif4i6wBSYcMLEJKVRdyI";

    private static final String USER_TOKEN_ISSUER = "ROXE";

    private static final String ITC = "itc";


    public String getUserIdToken(BusinessType businessType, Long userId, String itc, Integer expireMinutes) {
        return getUserToken(businessType, userId.toString(), itc, expireMinutes);
    }

    private String getUserToken(BusinessType businessType, String audience, String itc, Integer expireMinutes) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ITC, itc);

        DateTime now = DateTime.now(DateTimeZone.UTC);
        JwtBuilder jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuer(USER_TOKEN_ISSUER)
                .setAudience(audience)
                .setSubject(businessType.name())
                .setNotBefore(now.toDate())
                .signWith(Keys.hmacShaKeyFor(USER_TOKEN_SECRET.getBytes()), SignatureAlgorithm.HS256);

        if (expireMinutes != null) {
            jwt.setExpiration(now.plusMinutes(expireMinutes).toDate());
        }

        return jwt.compact();
    }

    public String getUserTokenAudience(String token) {
        Claims claims = getUserTokenClaims(token);
        return claims.getAudience();
    }

    public String getUserTokenItc(String token) {
        Claims claims = getUserTokenClaims(token);
        Object value = claims.get(ITC);

        return value == null ? null : value.toString();
    }

    private Claims getUserTokenClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(USER_TOKEN_SECRET.getBytes()))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.SYS_ERROR);
        }
    }

}
