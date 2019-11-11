package org.giraffeb.authjava.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.giraffeb.authjava.jwt.TokenGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 서버가 정상 작동 중인지 확인
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/")
    public ResponseEntity<String> hello() throws JsonProcessingException {
        var result = new HashMap<String, String>();
        result.put("result", "hello ok");

        var mapper = new ObjectMapper();
        var str = mapper.writeValueAsString(result);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(str);
    }

    /**
     * 사용자 아이디 존재여부를 알려주는 요청
     * 프론트 엔드에서 확인 용
     * @param userId
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<String> userExist(@PathVariable String userId) throws JsonProcessingException {
        var jsonMapper = new ObjectMapper();

        var isExist = this.userService.isExistUserByUserId(userId);
        var responseJsonMap = new HashMap<String, Object>();

        if(isExist == false) {
            responseJsonMap.put("status", "not found user");
        }else{
            responseJsonMap.put("status", "found user");
//            responseJsonMap.put("data", jsonMapper.writeValueAsString(userResult.get()));
        }

        var responseJsonString = jsonMapper.writeValueAsString(responseJsonMap);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(responseJsonString);
    }



    @PostMapping("/auth/signin")
    public ResponseEntity<String> userSignin(@RequestBody Map<String, Object> params){
        System.out.println("#PARAMS->"+params);

        Map<String, String> responseBody = new HashMap<>();
        boolean isAuth = userService.isUserSignIn(params);
        String jwtToken = null;
        ResponseEntity response = null;

        if( isAuth == true){ //인증이 성공했다면
            String userId = params.get("userId").toString();
            jwtToken = TokenGenerator.generateToken(userId);

            responseBody.put("status", "success");


            response = ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, jwtToken)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(responseBody);

        }else{ //인증이 실패했다면
            responseBody.put("status", "failed");

            response = ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(responseBody);
        }

        return response;
    }

    /**
     * 회원가입 구현     * @param params
     * @return
     */
    @PostMapping("/auth/signup")
    public ResponseEntity<String> userSignup(@RequestBody Map<String, Object> params){
        ResponseEntity response = null;
        Map<String, Object> responseBody = new HashMap<>();

        boolean isSignUp = userService.signup(params);

        if(isSignUp == true) {
            responseBody.put("status", "success signup");

            response = ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(responseBody);
        }else{
            responseBody.put("status", "failed signup");

            response = ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(responseBody);
        }


        return response;
    }


}
