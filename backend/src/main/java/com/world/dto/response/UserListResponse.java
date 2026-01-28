package com.world.dto.response;

import com.world.entity.Country;
import com.world.entity.User;
import com.world.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserListResponse  {

    private Long id;

    private Country country;

    private String username;

    private Role role;

    public UserListResponse(User user){
        id = user.getId();
        country = user.getCountry();
        username = user.getUsername();
        role = user.getRole();
    }
}
