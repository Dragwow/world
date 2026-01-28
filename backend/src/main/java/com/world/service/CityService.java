package com.world.service;

import com.world.dto.request.AddStatRequest;
import com.world.entity.City;
import com.world.utils.CityEnum;
import com.world.utils.KeysCity;

public interface CityService {

    void addStat(AddStatRequest request);
    void recalcCity(City city);
    void nukeCity(CityEnum cityEnum);
}
