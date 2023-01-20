package com.example.vettrust.service;

import com.example.vettrust.dto.PetDto;
import com.example.vettrust.exception.NoPetFoundException;
import com.example.vettrust.model.Pet;
import com.example.vettrust.model.PetOwner;
import com.example.vettrust.repository.PetRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {
    private final PetOwnerService petOwnerService;
    private final PetRepository petRepository;


    @Autowired
    public PetService(PetOwnerService petOwnerService, PetRepository petRepository) {
        this.petOwnerService = petOwnerService;
        this.petRepository = petRepository;

    }

    @Transactional
    public Pet findById(@NotNull Long id){
        return petRepository.findById(id).orElseThrow(() -> new NoPetFoundException("Pet with given id doesn't exist"));
    }

    @Transactional
    public PetDto findByIdDao(@NotNull Long id) {
        Pet pet = findById(id);
        return PetDto.entityToDto(pet);
    }

    @Transactional
    public List<Pet> findByOwnerEmail(@NotNull String email){
        Optional<PetOwner> petOwner = petOwnerService.findPetOwnerByEmail(email);
        return petRepository.findAllByPetOwnerEmail(email);
    }

    @Transactional
    public List<PetDto> findPetsByOwnerEmail(@NotNull String email){
        return findByOwnerEmail(email).stream().map(PetDto::entityToDto).collect(Collectors.toList());
    }

    public PetDto savePet(@NotNull PetDto petDto){
        Pet pet = new Pet();
        PetOwner petOwner =  petOwnerService.findPetOwnerById(petDto.getPetOwnerId());
        pet.setPetOwner(petOwner);
        pet.setAge(petDto.getAge());
        pet.setName(petDto.getName());
        pet.setType(petDto.getType());
        return PetDto.entityToDto(petRepository.save(pet));

    }

    public List<PetDto> getAll(){
        List<Pet> pets =  petRepository.findAll();
        return pets.stream().map(PetDto::entityToDto).collect(Collectors.toList());
    }

    public PetDto updatePet(@NotNull Long petId, PetDto petDto){
        Pet pet = findById(petId);
        pet.setAge(petDto.getAge());
        PetOwner petOwner = petOwnerService.findPetOwnerById(petDto.getPetOwnerId());
        pet.setPetOwner(petOwner);
        pet.setName(petDto.getName());
        return PetDto.entityToDto(petRepository.save(pet));

    }

    public boolean deletePet(@NotNull Long id){
        Pet pet = findById(id);
        petRepository.delete(pet);
        return true;

    }


}
