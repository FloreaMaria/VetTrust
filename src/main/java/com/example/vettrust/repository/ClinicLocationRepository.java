package com.example.vettrust.repository;

import com.example.vettrust.model.ClinicLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicLocationRepository extends JpaRepository<ClinicLocation, Long> {
}
