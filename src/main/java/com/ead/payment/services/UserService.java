package com.ead.payment.services;

import com.ead.payment.models.UserModel;

import java.util.UUID;

public interface UserService {

    UserModel save(UserModel model);
    void delete(UUID userId);

}
