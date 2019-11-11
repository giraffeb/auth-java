package org.giraffeb.authjava.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noId;

    @Column(unique = true)

    private String userId="";

    private String password="";

    public User() {
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
