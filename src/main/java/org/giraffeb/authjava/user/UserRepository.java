package org.giraffeb.authjava.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
    Boolean existsUserByUserId(String userId);
    Optional<User> findByUserId(String userId);

}
