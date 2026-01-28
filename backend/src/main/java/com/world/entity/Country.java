package com.world.entity;

import com.world.utils.CountryEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country extends BaseEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    private CountryEnum country;

    private String name;

    private int nukes;

    private int sanctions;

    private int budget;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> playerSlots = new ArrayList<>();

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<City> cities = new HashSet<>();

    public void addCity(City city) {
        city.setCountry(this);
        this.cities.add(city);
    }

    public Country(CountryEnum countryEnum,
                   String name,
                   List<City> citiesInput,
                   List<String> playerSlotsInput) {

        this.country = countryEnum;
        this.name = name;
        this.playerSlots = playerSlotsInput != null ? new ArrayList<>(playerSlotsInput) : new ArrayList<>();

        if (citiesInput != null) {
            citiesInput.forEach(this::addCity);
        }
    }

    public Country() {
    }
}
