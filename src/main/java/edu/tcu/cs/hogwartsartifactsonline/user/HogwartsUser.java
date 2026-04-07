package edu.tcu.cs.hogwartsartifactsonline.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class HogwartsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private boolean enabled;

    @NotEmpty
    private String roles; // "admin user"

    // getters and setters
}