package com.example.vettrust.repository;

import com.example.vettrust.model.VetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VetUserRepository extends JpaRepository<VetUser, Long> {
   Optional<VetUser> findByEmail(String email);
}
