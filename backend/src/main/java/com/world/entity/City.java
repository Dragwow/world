package com.world.entity;

import com.world.utils.CityEnum;
import com.world.utils.CountryEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "cities")
public class City extends BaseEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    private CityEnum city;

    private String name;

    private int economy;

    private int ecology;

    private int shield;

    private int baseIncome;

    private int income;

    private int life;

    private boolean destroyed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public City(CityEnum cityEnum, String s){
        city = cityEnum;
        name = s;
        baseIncome = 100;
        income = 100;
        life = 50;
        destroyed = false;
    }

    public City(){}

}
