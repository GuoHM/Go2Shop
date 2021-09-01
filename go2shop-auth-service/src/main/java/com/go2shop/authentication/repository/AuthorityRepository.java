package com.go2shop.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.go2shop.authentication.model.AuthorityName;
import com.go2shop.authentication.model.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Authority findByName(AuthorityName name);
}
