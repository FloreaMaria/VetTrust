package com.example.vettrust.repository;

import com.example.vettrust.model.Pet;
import com.example.vettrust.model.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {

    PetOwner findByEmail(String email);
}
