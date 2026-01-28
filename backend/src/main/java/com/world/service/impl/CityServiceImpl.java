package com.world.service.impl;

import com.world.dto.request.AddStatRequest;
import com.world.entity.City;
import com.world.repository.CityRepository;
import com.world.service.CityService;
import com.world.utils.CityEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository repository;

    @Override
    public void addStat(AddStatRequest request) {
        City city = getCityOpt(request.getCityEnum());

        switch (request.getKey()) {
            case ECONOMY -> city.setEconomy(city.getEconomy() + request.getValue());
            case ECOLOGY -> city.setEcology(city.getEcology() + request.getValue());
            case SHIELD -> city.setShield(city.getShield() + request.getValue());
        }
        recalcCity(city);
    }

    @Override
    public void recalcCity(City city) {
        if (city.isDestroyed()) {
            city.setIncome(0);
            city.setLife(0);
            return;
        }
        city.setIncome(Math.round(city.getBaseIncome() * (1 + city.getEconomy() / 100.0f)));
        city.setLife(Math.max(0, city.getEconomy() + city.getEcology() + city.getShield() * 5));

        repository.save(city);
    }

    @Override
    public void nukeCity(CityEnum cityEnum) {
        City city = getCityOpt(cityEnum);

        if (!city.isDestroyed()) {
            if (city.getShield() > 0) {
                city.setShield(city.getShield() - 1);
            } else {
                city.setDestroyed(true);
                city.setIncome(0);
                city.setLife(0);
            }
        }

        repository.save(city);
    }

    private City getCityOpt(CityEnum cityEnum){
        return repository.findByCity(cityEnum)
            .orElseThrow();
    }
}
