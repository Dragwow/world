package com.world.service;

import com.world.dto.request.DataTableRequest;
import com.world.dto.request.EnterRequest;
import com.world.dto.request.SelectRequest;
import com.world.dto.response.DataTableResponse;
import com.world.dto.response.TokenResponse;
import com.world.dto.response.UserListResponse;
import com.world.entity.User;
import com.world.utils.CountryEnum;

public interface UserService {

    void selectCountry(SelectRequest request);
//    DataTableResponse<UserListResponse> getAllUsers(DataTableRequest request);
    User enterUser(EnterRequest request);

}
