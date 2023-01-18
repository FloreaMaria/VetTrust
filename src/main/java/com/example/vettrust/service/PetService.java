package com.example.vettrust.service;

import com.example.vettrust.dto.PetDto;
import com.example.vettrust.model.Pet;
import com.example.vettrust.model.PetOwner;
import com.example.vettrust.repository.PetRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
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
    public Pet findById(Long id){
        return petRepository.findById(id).orElseThrow();
    }

    @Transactional
    public PetDto findByIdDao(Long id){
        Pet pet = findById(id);
        return PetDto.entityToDto(pet);
    }
    @Transactional
    public List<Pet> findByOwnerId(Long id){
        return petRepository.findAllByPetOwnerId(id);
    }

    @Transactional
    public List<PetDto> findPetsByOwnerId(Long id){
            return findByOwnerId(id).stream().map(PetDto::entityToDto).collect(Collectors.toList());
    }

    public PetDto savePet(@NotNull PetDto petDto){
        Pet pet = new Pet();
        PetOwner petOwner =  petOwnerService.findPetOwnerById(petDto.getPetOwnerId());
        if(petOwner != null){
            pet.setPetOwner(petOwner);
            pet.setAge(petDto.getAge());
            pet.setName(petDto.getName());
            pet.setType(petDto.getType());
            return PetDto.entityToDto(petRepository.save(pet));
        }
        return null;
    }

    public List<PetDto> getAll(){
        List<Pet> pets =  petRepository.findAll();
        return pets.stream().map(PetDto::entityToDto).collect(Collectors.toList());
    }
    public PetDto updatePet(Long petId, PetDto petDto){
        Pet pet = findById(petId);
        if(pet != null){
            pet.setAge(petDto.getAge());
            PetOwner petOwner = petOwnerService.findPetOwnerById(petDto.getPetOwnerId());
            if(petOwner != null){
                pet.setPetOwner(petOwner);
            }
            pet.setName(petDto.getName());
            return PetDto.entityToDto(petRepository.save(pet));
        }
        return null;
    }

    public boolean deletePet(Long id){
        Pet pet = findById(id);
        if(pet != null){
            petRepository.delete(pet);
            return true;
        }
        return false;
    }


}
