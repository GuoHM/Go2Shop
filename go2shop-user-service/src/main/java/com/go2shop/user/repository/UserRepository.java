package com.go2shop.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.go2shop.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
