package org.giraffeb.authjava.user;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean isExistUserByUserId(String userId){
        boolean isExist = false;
        isExist = userRepository.existsUserByUserId(userId);

        return isExist;
    }

    public boolean isUserSignIn(Map<String, Object> params) {

        boolean isAuth = false;

        String userId = params.get("userId").toString();
        String password = params.get("password").toString();

        //TODO: userId, password에 대한 validation을 미리 수행해서 값만 조회할 것.

        User currentUser = userRepository.findByUserId(userId)
                            .orElse(new User());

        if(
            currentUser
                .getPassword()
                .equals(password))
        {

            isAuth = true;
        }

        return isAuth;
    }

    public boolean signup(Map<String, Object> params) {
        boolean isSignUp = false;

        String userId = params.get("userId").toString();
        String password = params.get("password").toString();

        boolean isUserIdExist = this.isExistUserByUserId(userId);

        if(isUserIdExist != true){
            isSignUp = true;
            try{
                userRepository.save(new User(userId, password));
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return isSignUp;
    }
}
