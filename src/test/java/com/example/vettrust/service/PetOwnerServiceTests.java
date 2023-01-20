package com.example.vettrust.service;

import com.example.vettrust.model.PetOwner;
import com.example.vettrust.model.VetReview;
import com.example.vettrust.repository.PetOwnerRepository;
import com.example.vettrust.repository.VetReviewRepository;
import com.example.vettrust.repository.VetUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.vettrust.model.PetOwnerMock.petOwner;
import static com.example.vettrust.model.VetReviewMock.vetReview;
import static com.example.vettrust.model.VetUserMock.vetUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PetOwnerServiceTests {

    @Mock
    PetOwnerRepository petOwnerRepository;

    @Mock
    VetUserRepository vetUserRepository;

    @Mock
    VetReviewRepository vetReviewRepository;

    @InjectMocks
    PetOwnerService petOwnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save(){
        PetOwner petOwner = new PetOwner();
        petOwner.setFirstName("first");
        when(petOwnerRepository.save(petOwner)).thenReturn(petOwner());
        PetOwner petOwnerResult = petOwnerRepository.save(petOwner);
        assertEquals(petOwner.getFirstName(), petOwnerResult.getFirstName());

    }

    @Test
    void findPetOwnerById(){
        when(petOwnerRepository.findById(anyLong())).thenReturn(Optional.of(petOwner()));
        PetOwner result = petOwnerService.findPetOwnerById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void findPetOwnerByEmail(){
        when(petOwnerRepository.findByEmail(anyString())).thenReturn(Optional.of(petOwner()));
        Optional<PetOwner> result = petOwnerService.findPetOwnerByEmail("email");
        assertEquals("email", result.get().getEmail());
    }

    @Test
    void getAll(){
        when(petOwnerRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, petOwnerService.getAllPetOwners().size());
    }

    @Test
    void delete(){
        PetOwner petOwner = petOwner();
        when(petOwnerRepository.findById(anyLong())).thenReturn(Optional.of(petOwner));
        petOwnerService.deletePetOwner(petOwner.getId());
        verify(petOwnerRepository).delete(petOwner);
    }

    @Test
    void addReview(){
        VetReview vetReview = new VetReview();
        vetReview.setId(1L);
        when(vetUserRepository.findById(anyLong())).thenReturn(Optional.of(vetUser()));
        when(petOwnerRepository.findById(anyLong())).thenReturn(Optional.of(petOwner()));
        when(vetUserRepository.save(any())).thenReturn(vetUser());
        when(vetReviewRepository.save(vetReview)).thenReturn(vetReview());
        VetReview vetReviewResult = vetReviewRepository.save(vetReview);

        assertEquals(vetReview.getId(), vetReviewResult.getId());
    }

}
