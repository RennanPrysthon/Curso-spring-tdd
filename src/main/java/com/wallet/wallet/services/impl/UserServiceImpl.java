package com.wallet.wallet.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.wallet.entity.User;
import com.wallet.wallet.repositories.UserRepository;
import com.wallet.wallet.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repository;
	
	@Override
	public User save(User u) {
		return repository.save(u);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return repository.findByEmailEquals(email); 
	}

}
