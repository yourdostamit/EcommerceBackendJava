package com.ecommerce.Repositries;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.auth.entity.Adresses;

@Repository
public interface AdressRepository extends JpaRepository<Adresses, UUID> {

}
