package com.ecommerce.auth.repositries;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.auth.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {

	Authority findByRoleCode(String user);
}
