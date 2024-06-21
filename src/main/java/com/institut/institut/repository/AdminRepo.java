package com.institut.institut.repository;

import com.institut.institut.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin,Long> {

    Optional<Admin> findByUsernameAdmin(String username);
}
