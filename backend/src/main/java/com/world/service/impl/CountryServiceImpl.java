package com.world.service.impl;

import com.world.dto.request.AddForCountryRequest;
import com.world.dto.request.DataTableRequest;
import com.world.dto.response.CityPanelResponse;
import com.world.dto.response.CountryPanelResponse;
import com.world.entity.City;
import com.world.entity.Country;
import com.world.entity.User;
import com.world.repository.CityRepository;
import com.world.repository.CountryRepository;
import com.world.repository.UserRepository;
import com.world.service.CountryService;
import com.world.utils.CountryEnum;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    private static final Map<String, String> SORT_FIELDS = Map.of(
        "id", "id",
        "name", "name",
        "budget", "budget",
        "nukes", "nukes"
    );

    @Override
    public void addNuke(AddForCountryRequest request) {
        Country country = getCountryByName(request.getCountryEnum());
        country.setNukes(country.getNukes() + request.getAmount());
        countryRepository.save(country);
    }

    @Override
    public void setSanction(AddForCountryRequest request) {
        Country country = getCountryByName(request.getCountryEnum());
        int newSanctions = country.getSanctions() + request.getAmount();
        country.setSanctions(newSanctions);

        int damage = request.getAmount() * 10;

        if (country.getCities() != null) {
            for (City city : country.getCities()) {
                int newLife = city.getLife() - damage;
                city.setLife(Math.max(newLife, 0));
                cityRepository.save(city);
            }
        }

        countryRepository.save(country);
    }

    @Override
    public void recalcBudgetForAll() {
        List<Country> countries = countryRepository.findAll();

        for (Country country : countries) {
            int budget = country.getCities().stream()
                .mapToInt(City::getIncome)
                .sum();
            country.setBudget(country.getBudget() + budget);
        }

        countryRepository.saveAll(countries);
    }

    @Override
    public List<String> getCountryForStart() {
        return countryRepository.findAllCountryNames();
    }

    @Override
    @Transactional
    public CountryPanelResponse getCountryPanel(String username) {
        User user = userRepository.getUserByUsername(username)
            .orElseThrow(() -> new IllegalStateException("Пользователь не состоит ни в одной стране"));

        Country country = user.getCountry();

        List<String> playerSlots = new ArrayList<>(country.getPlayerSlots());
        Set<City> cities = new HashSet<>(country.getCities());

        return mapToCountryPanelResponse(country, cities, playerSlots);
    }

    private CountryPanelResponse mapToCountryPanelResponse(Country country,
                                                           Set<City> cities,
                                                           List<String> playerSlots) {
        List<CityPanelResponse> cityResponses = cities
            .stream()
            .map(CityPanelResponse::new)
            .toList();

        return new CountryPanelResponse(
            country.getName(),
            country.getNukes(),
            country.getSanctions(),
            country.getBudget(),
            cityResponses,
            playerSlots
        );
    }

    private CityPanelResponse mapToCityPanelResponse(City city) {
        return new CityPanelResponse(city);
    }

    @Override
    @Transactional
    public List<CountryPanelResponse> getAllCountriesPanel() {
        List<Country> countries = countryRepository.findAll();

        return countries.stream()
            .map(country -> {
                Set<City> cities = new HashSet<>(country.getCities());
                List<String> playerSlots = new ArrayList<>(country.getPlayerSlots());
                return mapToCountryPanelResponse(country, cities, playerSlots);
            })
            .collect(Collectors.toList());
    }


    private Page<Country> findAll(DataTableRequest request) {
        String sortField = SORT_FIELDS.getOrDefault(request.getSort(), "id");
        Sort.Direction direction = "asc".equalsIgnoreCase(request.getOrder()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(direction, sortField));
        return countryRepository.findAll(pageable);
    }

    @Override
    public Country getCountryByName(CountryEnum countryEnum) {
        return countryRepository.findByCountry(countryEnum)
            .orElseThrow(() -> new IllegalArgumentException("Страна не найдена: " + countryEnum));
    }
}
