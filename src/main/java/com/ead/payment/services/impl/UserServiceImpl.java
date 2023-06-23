package com.ead.payment.services.impl;

import com.ead.payment.models.UserModel;
import com.ead.payment.repositories.UserRepository;
import com.ead.payment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserModel save(UserModel model) {
        return repository.save(model);
    }

    @Override
    @Transactional
    public void delete(UUID userId) {
        repository.deleteById(userId);
    }
}
