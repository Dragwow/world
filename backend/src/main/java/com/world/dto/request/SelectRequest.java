package com.world.dto.request;

import com.world.utils.CountryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectRequest {
    private String username;
    private CountryEnum countryEnum;
}
