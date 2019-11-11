package org.giraffeb.authjava.auth;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.time.LocalTime;
import java.util.HashMap;

public class JwtTest {

    @Test
    public void jwtTest(){
        HashMap<String, Object> mypay = new HashMap<>();

        mypay.put("userId", "hello");
        mypay.put("password","hello");
        mypay.put("expired", LocalTime.now().toString());

        String jwt = Jwts.builder()
                .setClaims(mypay)
                .signWith(SignatureAlgorithm.HS256, "hello")
                .compact();

        System.out.println(jwt);


    }
}
