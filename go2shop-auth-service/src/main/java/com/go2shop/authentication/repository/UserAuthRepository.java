package com.go2shop.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.go2shop.authentication.model.entity.SecurityUser;

@Repository
public interface UserAuthRepository extends JpaRepository<SecurityUser, Long>{

	SecurityUser findByUsername(String username);

}
