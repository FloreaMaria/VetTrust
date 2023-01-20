package com.example.vettrust.service;

import com.example.vettrust.dto.user.VetUserDto;
import com.example.vettrust.model.VetReview;
import com.example.vettrust.model.VetUser;
import com.example.vettrust.repository.VetReviewRepository;
import com.example.vettrust.repository.VetUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.vettrust.model.VetReviewMock.vetReview;
import static com.example.vettrust.model.VetUserDtoMock.vetUserDto;
import static com.example.vettrust.model.VetUserMock.vetUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VetUserServiceTests {
    @Mock
    VetUserRepository vetUserRepository;

    @Mock
    VetReviewRepository vetReviewRepository;

    @InjectMocks
    VetUserService vetUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void saveVet(){
        VetUser vetUser = new VetUser();
        vetUser.setEmail("vet@gmail.com");
        when(vetUserRepository.save(vetUser)).thenReturn(vetUser);
        VetUser vetUserResult = vetUserRepository.save(vetUser);
        assertEquals(vetUser.getEmail(), vetUserResult.getEmail());
    }

    @Test
    void findAll() {
        when(vetUserRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, vetUserService.getAll().size());
    }


    @Test
    void findAllVets(){
        List<VetUserDto> vetUsers = new ArrayList<>();
        vetUsers.add(vetUserDto());
        when(vetUserService.getAll()).thenReturn(vetUsers);
        assertEquals(1, vetUsers.size());
    }

    @Test
    void findByEmail(){
        when(vetUserRepository.findByEmail(anyString())).thenReturn(Optional.of(vetUser()));
        Optional<VetUser> result = vetUserService.findByEmail("vet@gmail.com");
        assertEquals("vet@gmail.com", result.get().getEmail());
    }


    @Test
    void updateVet() {
        VetUser vetUser = vetUser();
        when(vetUserRepository.findById(anyLong())).thenReturn(Optional.of(vetUser()));
        when(vetUserRepository.save(any())).thenReturn(vetUser);
        VetUserDto vetUserDto = vetUserService.updateVet(1L, vetUserDto());
        assertNotNull(vetUserDto);
    }

    @Test
    void getReviews(){
        List<VetReview> vetReviews = new ArrayList<>();
        when(vetUserRepository.findById(anyLong())).thenReturn(Optional.of(vetUser()));
        when(vetReviewRepository.findAllByVetUserId(anyLong())).thenReturn(vetReviews);
        vetReviews.add(vetReview());
        vetUserService.getReviewsForVet(anyLong());
        assertEquals(1, vetReviews.size());
    }

    @Test
    void deleteVet(){
        VetUser vetUser = vetUser();
        when(vetUserRepository.findById(anyLong())).thenReturn(Optional.of(vetUser));
        vetUserService.deleteVet(vetUser.getId());
        verify(vetUserRepository).delete(vetUser);
    }
}
