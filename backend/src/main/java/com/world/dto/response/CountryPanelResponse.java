package com.world.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CountryPanelResponse {

    private String name;

    private int nukes;

    private int sanctions;

    private int budget;

    private List<CityPanelResponse> cities;

    private List<String> playerSlots;


}
