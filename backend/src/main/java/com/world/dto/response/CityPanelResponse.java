package com.world.dto.response;

import com.world.entity.City;
import com.world.utils.CityEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityPanelResponse {

    private CityEnum city;

    private String name;

    private int economy;

    private int ecology;

    private int shield;

    private int baseIncome;

    private int income;

    private int life;

    private boolean destroyed;

    public CityPanelResponse(City city) {
        this.city = city.getCity();
        this.name = city.getName();
        this.economy = city.getEconomy();
        this.ecology = city.getEcology();
        this.shield = city.getShield();
        this.baseIncome = city.getBaseIncome();
        this.income = city.getIncome();
        this.life = city.getLife();
        this.destroyed = city.isDestroyed();
    }
}
