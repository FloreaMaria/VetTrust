package com.example.vettrust.repository;

import com.example.vettrust.model.Pet;
import com.example.vettrust.model.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByPetOwnerId(Long id);
    List<Pet> findAllByPetOwnerEmail(String email);
}
