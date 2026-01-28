package com.world.dto.request;

import com.world.utils.CityEnum;
import com.world.utils.KeysCity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddStatRequest {

    private CityEnum cityEnum;
    private KeysCity key;
    private int value;

    public AddStatRequest(CityEnum cityEnum1, KeysCity key1, int value1){
        cityEnum = cityEnum1;
        key = key1;
        value = value1;
    }

}
