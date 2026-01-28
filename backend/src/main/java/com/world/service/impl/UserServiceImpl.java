package com.world.service.impl;

import com.world.dto.request.DataTableRequest;
import com.world.dto.request.EnterRequest;
import com.world.dto.request.SelectRequest;
import com.world.dto.response.DataTableResponse;
import com.world.dto.response.TokenResponse;
import com.world.dto.response.UserListResponse;
import com.world.entity.Country;
import com.world.entity.User;
import com.world.repository.CountryRepository;
import com.world.repository.UserRepository;
import com.world.service.UserService;
import com.world.utils.CountryEnum;
import com.world.utils.Role;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final CountryRepository countryRepository;

    private static final String ADMIN_PASSWORD = "123";
    private static final String USER_PASSWORD = "1234";

    @Override
    public User enterUser(EnterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // Проверка пароля
        if (!ADMIN_PASSWORD.equals(password) && !USER_PASSWORD.equals(password)) {
            throw new IllegalArgumentException("Неверный пароль");
        }

        // Получаем пользователя или создаём нового
        return repository.getUserByUsername(username)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setRole(USER_PASSWORD.equals(password) ? Role.USER : Role.ADMIN);
                return repository.save(newUser);
            });
    }

    @Override
    public void selectCountry(SelectRequest request) {
        User user = repository.getUserByUsername(request.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if (user.getCountry() != null) {
            throw new IllegalStateException("Страна уже выбрана");
        }

        Country country = countryRepository.findByCountryForUpdate(request.getCountryEnum())
            .orElseThrow(() -> new IllegalArgumentException("Страна не найдена"));

        List<String> slots = country.getPlayerSlots();
        if (slots == null) {
            slots = new ArrayList<>();
            country.setPlayerSlots(slots);
        }

        if (slots.size() >= 3) {
            throw new IllegalStateException("Все слоты заняты");
        }

        slots.add(request.getUsername());
        user.setCountry(country);

        countryRepository.save(country);
        repository.save(user);
    }

//    @Override
//    public DataTableResponse<UserListResponse> getAllUsers(DataTableRequest request) {
//        Page<User> page = findAll(request);
//
//        DataTableResponse<UserListResponse> response = new DataTableResponse<>();
//        response.setSort(request.getSort());
//        response.setOrder(request.getOrder());
//        response.setItems(page.getContent().stream()
//            .map(UserListResponse::new)
//            .toList());
//
//        return response;
//    }
//
//    private Page<User> findAll(DataTableRequest request) {
//        String sortField = SORT_FIELDS.getOrDefault(request.getSort(), "id");
//
//        Sort.Direction direction = "asc".equalsIgnoreCase(request.getOrder())
//            ? Sort.Direction.ASC
//            : Sort.Direction.DESC;
//
//        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(direction, sortField));
//
//        return repository.findAll(pageable);
//    }
//
//    private static final Map<String, String> SORT_FIELDS = Map.of(
//        "id", "id",
//        "username", "username",
//        "role", "role",
//        "country", "country.name"
//    );
}
