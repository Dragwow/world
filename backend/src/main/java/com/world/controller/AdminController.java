package com.world.controller;

import com.world.dto.request.AddForCountryRequest;
import com.world.dto.request.AddStatRequest;
import com.world.dto.request.DataTableRequest;
import com.world.dto.response.CountryPanelResponse;
import com.world.dto.response.DataTableResponse;
import com.world.dto.response.ResponseContainer;
import com.world.service.CityService;
import com.world.service.CountryService;
import com.world.service.UserService;
import com.world.utils.CityEnum;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CountryService countryService;
    private final CityService cityService;


    @PostMapping("/add-stat")
    public ResponseEntity<Void> addStat(@RequestBody AddStatRequest request) {
        cityService.addStat(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/nuke-city")
    public ResponseEntity<Void> nukeCity(@RequestBody CityEnum city) {
        cityService.nukeCity(city);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-nuke")
    public ResponseEntity<Void> addNuke(@RequestBody AddForCountryRequest request) {
        countryService.addNuke(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-sanctional")
    public ResponseEntity<Void> addSanctional(@RequestBody AddForCountryRequest request) {
        countryService.setSanction(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calculate-world")
    public ResponseEntity<Void> calculate() {
        countryService.recalcBudgetForAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all-countries")
    public List<CountryPanelResponse> getAllCountries() {
        return countryService.getAllCountriesPanel();
    }

    //    @GetMapping("/users-list")
//    public ResponseEntity<ResponseContainer<DataTableResponse<UserListResponse>>> getUsers(
//        @RequestParam(defaultValue = "1") int page,
//        @RequestParam(defaultValue = "10") int size,
//        @RequestParam(defaultValue = "username") String sort,
//        @RequestParam(defaultValue = "asc") String order
//    ) {
//        DataTableRequest request = new DataTableRequest(page, size, sort, order);
//        return ResponseEntity.ok(new ResponseContainer<>(userService.getAllUsers(request)));
//    }
}
