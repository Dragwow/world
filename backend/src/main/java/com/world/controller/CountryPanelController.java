package com.world.controller;

import com.world.dto.response.CountryPanelResponse;
import com.world.dto.response.ResponseContainer;
import com.world.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/country")
@AllArgsConstructor
public class CountryPanelController {

    private final CountryService countryService;

    @GetMapping("/country-panel/{username}")
    public ResponseEntity<ResponseContainer<CountryPanelResponse>> getCountryPanel(@PathVariable String username) {
        CountryPanelResponse panel = countryService.getCountryPanel(username);
        return ResponseEntity.ok(new ResponseContainer<>(panel));

    }
}
