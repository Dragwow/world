package com.world.dto.request;

import com.world.utils.CountryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnterRequest {

    private String username;

    private String password;

    private CountryEnum countryEnum;
}
