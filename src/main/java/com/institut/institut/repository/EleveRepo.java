package com.institut.institut.repository;

import com.institut.institut.models.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EleveRepo extends JpaRepository<Eleve,Long> {
}
