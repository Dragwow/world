package com.world.repository;

import com.world.entity.Country;
import com.world.utils.CountryEnum;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends BaseRepository<Country> {

    Optional<Country> findByCountry(CountryEnum countryEnum);
    boolean existsByCountry(CountryEnum countryEnum);

    @Query("SELECT c.name FROM Country c")
    List<String> findAllCountryNames();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Country c where c.country = :country")
    Optional<Country> findByCountryForUpdate(@Param("country") CountryEnum country);




}
