package org.giraffeb.authjava.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TokenGenerator {

    /**
     *
     * @param userId
     * @return
     */
    public static String generateToken(String userId){
        Map<String, Object> tempCalims = new HashMap<>();
        tempCalims.put("userId", userId);
        tempCalims.put("expired", LocalDate.now().plusDays(7).toString());


        String token = Jwts.builder()
                            .addClaims(tempCalims)
                            .signWith(SignatureAlgorithm.HS256, "hello")
                            .compact();

        return token;
    }

}
