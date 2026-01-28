package com.world.dto.request;

import com.world.utils.CountryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddForCountryRequest {

    private CountryEnum countryEnum;

    private int amount;

    public AddForCountryRequest(CountryEnum countryEnum1, int amount1){
        countryEnum = countryEnum1;
        amount = amount1;
    }
}
