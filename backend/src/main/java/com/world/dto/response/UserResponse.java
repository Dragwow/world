package com.world.dto.response;

import com.world.utils.CountryEnum;
import com.world.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String username;
    private Role role;
    private CountryEnum countrySelected;
}
