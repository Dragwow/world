package com.world.repository;

import com.world.entity.City;
import com.world.utils.CityEnum;

import java.util.Optional;

public interface CityRepository extends BaseRepository<City> {

    Optional<City> findByCity(CityEnum city);
}
