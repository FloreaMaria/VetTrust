package com.example.vettrust.service;

import com.example.vettrust.dto.PetDto;
import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.dto.user.VetUserDto;
import com.example.vettrust.exception.NoVetFoundException;
import com.example.vettrust.model.Pet;
import com.example.vettrust.model.VetReview;
import com.example.vettrust.model.VetUser;
import com.example.vettrust.repository.VetReviewRepository;
import com.example.vettrust.repository.VetUserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class VetUserService {
    private final VetUserRepository vetUserRepository;
    private final VetReviewRepository vetReviewRepository;

    @Autowired
    public VetUserService(VetUserRepository vetUserRepository, VetReviewRepository vetReviewRepository) {
        this.vetUserRepository = vetUserRepository;
        this.vetReviewRepository = vetReviewRepository;
    }

    public VetUserDto saveVet(@NotNull VetUserDto vetUserDto) throws ParseException {
        VetUser vet = new VetUser();
        vet.setRating(0.0);
        vet.setEmail(vetUserDto.getEmail());
        vet.setFirstName(vetUserDto.getFirstName());
        vet.setLastName(vetUserDto.getLastName());
        vet.setPhoneNumber(vetUserDto.getPhoneNumber());
        vet.setPassword(vetUserDto.getPassword());
        vet.setVetSchedules(new HashSet<>());
        vet.setVetReviews(new ArrayList<>());
        return VetUserDto.entityToDto(vetUserRepository.save(vet));

    }

    @Transactional
    public List<VetUserDto> getAll(){
        List<VetUser> vets =  vetUserRepository.findAll();
        return vets.stream().map(VetUserDto::entityToDto).collect(Collectors.toList());
    }

    @Transactional
    public VetUser findById(@NotNull Long id){
        return vetUserRepository.findById(id).orElseThrow(() -> new NoVetFoundException("Vet with given id " + id + " not found"));
    }


    @Transactional
    public VetUserDto findByIdDto(@NotNull Long id) {
        VetUser vetUser = findById(id);
        return VetUserDto.entityToDto(vetUser);
    }

    @Transactional
    public Optional<VetUser> findByEmail(@NotNull String email){
        return Optional.ofNullable(vetUserRepository.findByEmail(email).orElseThrow(() -> new NoVetFoundException("Vet with given email " + email + " not found")));
    }

    @Transactional
    public VetUserDto updateVet(@NotNull Long vetId, VetUserDto vetDto){
        VetUser vetUser = findById(vetId);
        if(vetUser != null){
            if(vetDto.getPhoneNumber()!= null){
                vetUser.setPhoneNumber(vetDto.getPhoneNumber());
            }
            if(vetDto.getEmail() != null)
            {
                vetUser.setEmail(vetDto.getEmail());
            }

            return VetUserDto.entityToDto(vetUserRepository.save(vetUser));
        }
        return null;
    }

    public Boolean deleteVet(@NotNull Long id){
        VetUser vet = findById(id);
        vetUserRepository.delete(vet);
        return true;
    }


    public List<VetReviewDto>getReviewsForVet(@NotNull Long vetId){
        VetUser vetUser = findById(vetId);
        List<VetReview> vetReviews = vetReviewRepository.findAllByVetUserId(vetId);
        return vetReviews.stream().map(VetReviewDto::entityToDto).collect(toList());
    }
}
