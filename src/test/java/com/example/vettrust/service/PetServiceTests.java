package com.example.vettrust.service;

import com.example.vettrust.dto.PetDto;
import com.example.vettrust.model.Pet;
import com.example.vettrust.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.vettrust.model.PetDtoMock.petDto;
import static com.example.vettrust.model.PetMock.pet;
import static com.example.vettrust.model.PetOwnerMock.petOwner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PetServiceTests {
    @Mock
    PetOwnerService petOwnerService;

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetService petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePet(){
        Pet pet = new Pet();
        pet.setName("pet");
        when(petOwnerService.findPetOwnerById(anyLong())).thenReturn(petOwner());
        when(petRepository.save(pet)).thenReturn(pet());
        Pet petResult = petRepository.save(pet);
        assertEquals(pet.getName(), petResult.getName());

    }

    @Test
    void findById(){
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet()));
        Pet result = petService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void findByOwnerEmail(){
        when(petOwnerService.findPetOwnerByEmail(anyString())).thenReturn(Optional.of(petOwner()));
        List<Pet> pets = new ArrayList<>();
        pets.add(pet());
        when(petService.findByOwnerEmail(anyString())).thenReturn(pets);
        assertEquals(1, pets.size());
    }

    @Test
    void getAll(){
        when(petRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, petService.getAll().size());
    }

    @Test
    void updatePet(){
        Pet pet = pet();
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet()));
        when(petOwnerService.findPetOwnerById(anyLong())).thenReturn(petOwner());
        when(petRepository.save(any())).thenReturn(pet);
        PetDto petDto = petService.updatePet(1L, petDto());
        assertNotNull(petDto);
    }

    @Test
    void delete(){
        Pet pet = pet();
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));
        petService.deletePet(pet.getId());
        verify(petRepository).delete(pet);
    }
}
