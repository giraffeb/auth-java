//package org.giraffeb.authjava.auth;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.giraffeb.authjava.user.User;
//import org.giraffeb.authjava.user.UserRepository;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthContoller {
//
//    private UserRepository userRepo;
//
//    public AuthContoller(UserRepository userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    @GetMapping("/signin")
//    public ResponseEntity<String> signin(@RequestParam Map<String, String> params) throws IOException {
//        var jsonMapper = new ObjectMapper();
//
//        var userResult = this.userRepo.findByUserId(params.get("userid").toString());
//        var responseMap = new HashMap<String, String>();
//
//        if(userResult.isEmpty()){
//            responseMap.put("status", "failed");
//            responseMap.put("msg", "user not found");
//        }else{
//            responseMap.put("status", "success");
//            responseMap.put("msg", "user found it");
//            responseMap.put("data", jsonMapper.writeValueAsString(userResult.get()));
//            var jwt = Jwts
//                    .builder()
//                    .setHeaderParam("alg", "HS512")
//                    .setHeaderParam("typ", "JWT")
//                    .setPayload("userid -> "+userResult.get().getUserId())
//                    .signWith(SignatureAlgorithm.HS512, "giraffeb")
//                    .compact();
//            responseMap.put("jwt", jwt);
//        }
//
//        var responseJsonString = jsonMapper.writeValueAsString(responseMap);
//
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .body(responseJsonString);
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<String> signiup(@RequestBody String body) throws IOException {
//        var jsonMapper = new ObjectMapper();
//
//        //body parse
//        var requestBodyJsonToMap = jsonMapper.readValue(body, Map.class);
//        var new_user = new User(requestBodyJsonToMap.get("userid").toString()
//                                , requestBodyJsonToMap.get("password").toString());
//
//        var userResult = this.userRepo.save(new_user);
//        var responseMap = new HashMap<String, String>();
//
//        if(userResult == null){
//            responseMap.put("status", "failed");
//            responseMap.put("msg", "user not found");
//        }else{
//            responseMap.put("status", "success");
//            responseMap.put("msg", "signup complete");
//        }
//
//        var responseJsonString = jsonMapper.writeValueAsString(responseMap);
//
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .body(responseJsonString);
//    }
//}
