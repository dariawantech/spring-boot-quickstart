package com.dariawan.meetdash.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
    
    private static final long serialVersionUID = 98556721815764L;
    
    private Integer id;
    private String username;
    private String password;
    // private String confirmPassword;
    // private String passwordHint;
    private String firstName;
    private String lastName;
    private String email;
    // private String photo;
    // private String phoneNumber;
    // private String website;
    private boolean enabled;
}
