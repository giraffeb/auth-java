package org.giraffeb.authjava.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UserController {

    private UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public ResponseEntity<String> hello() throws JsonProcessingException {
        var result = new HashMap<String, String>();
        result.put("result", "hello ok");

        var mapper = new ObjectMapper();
        var str = mapper.writeValueAsString(result);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(str);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<String> findUser(@PathVariable String userId) throws JsonProcessingException {
        var jsonMapper = new ObjectMapper();

        var userResult = this.userRepo.findByUserId(userId);
        var responseJsonMap = new HashMap<String, Object>();

        if(userResult.isEmpty()) {
            responseJsonMap.put("status", "not found user");
        }else{
            responseJsonMap.put("status", "found user");
            responseJsonMap.put("data", jsonMapper.writeValueAsString(userResult.get()));
        }

        var responseJsonString = jsonMapper.writeValueAsString(responseJsonMap);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(responseJsonString);
    }
}
