package com.world.entity;

import com.world.utils.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    private String username;

    private Role role;

    public User(){}

    public User(String username1, Role role1){
        username = username1;
        role = role1;
    }

}
