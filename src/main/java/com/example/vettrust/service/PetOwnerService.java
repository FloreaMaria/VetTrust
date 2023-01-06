package com.example.vettrust.service;

import com.example.vettrust.dto.user.PetOwnerDto;
import com.example.vettrust.model.PetOwner;
import com.example.vettrust.repository.PetOwnerRepository;
import com.example.vettrust.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetOwnerService {
    private final PetOwnerRepository petOwnerRepository;
    private final PetRepository petRepository;

    @Autowired
    public PetOwnerService(PetOwnerRepository petOwnerRepository, PetRepository petRepository) {
        this.petOwnerRepository = petOwnerRepository;
        this.petRepository = petRepository;
    }

    @Transactional
    public PetOwner findPetOwnerById(Long id){
        return petOwnerRepository.findById(id).orElseThrow();
    }

    @Transactional
    public PetOwnerDto findPetOwnerDto(Long id){
        PetOwner petOwner= findPetOwnerById(id);
        return PetOwnerDto.entityToDto(petOwner);
    }

    public boolean deletePetOwner(Long id){
        if(id != null) {
            PetOwner petOwner = findPetOwnerById(id);
            petOwnerRepository.delete(petOwner);
            return true;
        }
        return false;
    }
    @Transactional
    public List<PetOwnerDto> getAllPetOwners(){
        List<PetOwner> petOwners =  petOwnerRepository.findAll();
        return petOwners.stream().map(PetOwnerDto::entityToDto).collect(Collectors.toList());
    }


}
