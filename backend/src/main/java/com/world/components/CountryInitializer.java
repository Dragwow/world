package com.world.components;

import com.world.entity.City;
import com.world.entity.Country;
import com.world.repository.CountryRepository;
import com.world.utils.CountryEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.world.utils.CityEnum.*;
import static com.world.utils.CountryEnum.*;

@Component
@RequiredArgsConstructor
public class CountryInitializer {

    private final CountryRepository repository;

    @PostConstruct
    public void init() {

        saveCountry(
            USA, "США",
            Arrays.asList(
                new City(NEW_YORK, "Нью-Йорк"),
                new City(LOS_ANGELES, "Лос-Анджелес"),
                new City(CHICAGO, "Чикаго"),
                new City(HOUSTON, "Хьюстон")
            ),
            List.of()
        );

        saveCountry(
            GERMANY, "Германия",
            Arrays.asList(
                new City(BERLIN, "Берлин"),
                new City(MUNICH, "Мюнхен"),
                new City(HAMBURG, "Гамбург"),
                new City(FRANKFURT, "Франкфурт")
            ),
            List.of()
        );

        saveCountry(
            CHINA, "Китай",
            Arrays.asList(
                new City(BEIJING, "Пекин"),
                new City(SHANGHAI, "Шанхай"),
                new City(GUANGZHOU, "Гуанчжоу"),
                new City(SHENZHEN, "Шэньчжэнь")
            ),
            List.of()
        );

        saveCountry(
            IRAN, "Иран",
            Arrays.asList(
                new City(TEHRAN, "Тегеран"),
                new City(SHIRAZ, "Шираз"),
                new City(ISFAHAN, "Исфахан"),
                new City(TABRIZ, "Тебриз")
            ),
            List.of()
        );

        saveCountry(
            NORTH_KOREA, "Сев. Корея",
            Arrays.asList(
                new City(PYONGYANG, "Пхеньян"),
                new City(KAESONG, "Кэсон"),
                new City(RASON, "Расон"),
                new City(WONSAN, "Вонсан")
            ),
            List.of()
        );

        saveCountry(
            ISRAEL, "Израиль",
            Arrays.asList(
                new City(TEL_AVIV, "Тель-Авив"),
                new City(JERUSALEM, "Иерусалим"),
                new City(HAIFA, "Хайфа"),
                new City(EILAT, "Эйлат")
            ),
            List.of()
        );
    }

    private void saveCountry(CountryEnum country1,
                             String name1,
                             List<City> cities1,
                             List<String> playerSlots1) {

        if (repository.existsByCountry(country1)){
            return;
        }

        List<String> limitedSlots = playerSlots1 == null
            ? List.of()
            : playerSlots1.size() > 3
            ? playerSlots1.subList(0, 3)
            : playerSlots1;

        Country country = new Country(
            country1,
            name1,
            cities1,
            limitedSlots
        );

        repository.save(country);
    }
}
