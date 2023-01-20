package com.example.vettrust.service;

import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.dto.user.PetOwnerDto;
import com.example.vettrust.model.PetOwner;
import com.example.vettrust.model.VetReview;
import com.example.vettrust.model.VetUser;
import com.example.vettrust.repository.PetOwnerRepository;
import com.example.vettrust.repository.PetRepository;
import com.example.vettrust.repository.VetReviewRepository;
import com.example.vettrust.repository.VetUserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetOwnerService {
    private final PetOwnerRepository petOwnerRepository;
    private final PetRepository petRepository;
    private final VetUserRepository vetUserRepository;
    private final VetReviewRepository vetReviewRepository;

    @Autowired
    public PetOwnerService(PetOwnerRepository petOwnerRepository, PetRepository petRepository,VetUserRepository vetUserRepository, VetReviewRepository vetReviewRepository) {
        this.petOwnerRepository = petOwnerRepository;
        this.petRepository = petRepository;
        this.vetUserRepository = vetUserRepository;
        this.vetReviewRepository = vetReviewRepository;
    }

    @Transactional
    public PetOwner findPetOwnerById(Long id){
        return petOwnerRepository.findById(id).orElseThrow();
    }

    @Transactional
    public PetOwner findPetOwnerByEmail(String email){
        return petOwnerRepository.findByEmail(email);
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

    @Transactional
    public VetReviewDto addClientReview(@NotNull VetReviewDto dto) {
        Optional<VetUser> optionalVetUser = vetUserRepository.findById(dto.getVetId());
        PetOwner petOwner = findPetOwnerById(dto.getPetOwnerId());
        if (optionalVetUser.isPresent() && petOwner != null) {

            //check if owner had a appointment with vet

            //check if owner already added review for vet
            Optional<VetReview> alreadyAddedReviewForRestaurant = petOwner.getVetReviews().stream().filter(
                    review -> review.getPetOwner().getId().equals(petOwner.getId()) &&
                            review.getVetUser().getId().equals(dto.getVetId())).findAny();
            if (alreadyAddedReviewForRestaurant.isEmpty()) {
                VetUser vetUser = optionalVetUser.get();

                int totalNumberOfStars = vetUser.getVetReviews().stream().mapToInt(VetReview::getStars).sum();
                totalNumberOfStars += dto.getStars();
                int totalNumberOfReviews = vetUser.getVetReviews().size() + 1;
                vetUser.setRating((double) totalNumberOfStars / totalNumberOfReviews);
                vetUser = vetUserRepository.save(vetUser);

                //save review
                VetReview review = new VetReview();
                review.setVetUser(vetUser);
                review.setPetOwner(petOwner);
                review.setStars(dto.getStars());
                review.setComment(dto.getComment());
                return VetReviewDto.entityToDto(vetReviewRepository.save(review));
            }
        }
        return null;
    }


}
