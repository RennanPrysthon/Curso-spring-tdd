package com.wallet.wallet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.wallet.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmailEquals(String email);
}
