package com.world.service;

import com.world.dto.request.AddForCountryRequest;
import com.world.dto.response.CountryPanelResponse;
import com.world.entity.Country;
import com.world.utils.CountryEnum;

import java.util.List;

public interface CountryService {

    Country getCountryByName(CountryEnum countryEnum);

    List<CountryPanelResponse> getAllCountriesPanel();

    void addNuke(AddForCountryRequest request);

    void setSanction(AddForCountryRequest request);

    void recalcBudgetForAll();

    List<String> getCountryForStart();

    CountryPanelResponse getCountryPanel(String username);

    void minusNuke(AddForCountryRequest request);
    void minusBudget(AddForCountryRequest request);
    void minusSanctional(AddForCountryRequest request);


}
