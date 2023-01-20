package com.example.vettrust.service;

import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.dto.user.PetOwnerDto;
import com.example.vettrust.exception.NoPetOwnerFoundException;
import com.example.vettrust.exception.NoVetFoundException;
import com.example.vettrust.model.PetOwner;
import com.example.vettrust.model.VetReview;
import com.example.vettrust.model.VetUser;
import com.example.vettrust.repository.PetOwnerRepository;
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
    private final VetUserRepository vetUserRepository;
    private final VetReviewRepository vetReviewRepository;

    @Autowired
    public PetOwnerService(PetOwnerRepository petOwnerRepository, VetUserRepository vetUserRepository, VetReviewRepository vetReviewRepository) {
        this.petOwnerRepository = petOwnerRepository;
        this.vetUserRepository = vetUserRepository;
        this.vetReviewRepository = vetReviewRepository;
    }


    public PetOwnerDto savePetOwner(@NotNull PetOwnerDto petOwnerDto){
        PetOwner petOwner = new PetOwner();
        petOwner.setEmail(petOwnerDto.getEmail());
        petOwner.setFirstName(petOwnerDto.getFirstName());
        petOwner.setLastName(petOwnerDto.getLastName());
        petOwner.setPhoneNumber(petOwnerDto.getPhoneNumber());
        petOwner.setPassword(petOwnerDto.getPassword());
        return PetOwnerDto.entityToDto(petOwnerRepository.save(petOwner));

    }

    @Transactional
    public PetOwner findPetOwnerById(@NotNull Long id){
        return petOwnerRepository.findById(id).orElseThrow(()-> new NoPetOwnerFoundException("Pet owner with given id doesn't exist"));
    }

    @Transactional
    public Optional<PetOwner> findPetOwnerByEmail(@NotNull String email){
        return Optional.ofNullable(petOwnerRepository.findByEmail(email).orElseThrow(() -> new NoPetOwnerFoundException("Pet owner with given email not found")));
    }


    @Transactional
    public PetOwnerDto findPetOwnerDto(@NotNull Long id){
        PetOwner petOwner= findPetOwnerById(id);
        return PetOwnerDto.entityToDto(petOwner);
    }

    public boolean deletePetOwner(@NotNull Long id){
        PetOwner petOwner = findPetOwnerById(id);
        petOwnerRepository.delete(petOwner);
        return true;
    }

    @Transactional
    public List<PetOwnerDto> getAllPetOwners(){
        List<PetOwner> petOwners =  petOwnerRepository.findAll();
        return petOwners.stream().map(PetOwnerDto::entityToDto).collect(Collectors.toList());
    }

    @Transactional
    public VetReviewDto addClientReview(@NotNull VetReviewDto dto) {
        Optional<VetUser> optionalVetUser = Optional.ofNullable(vetUserRepository.findById(dto.getVetId()).orElseThrow(() -> new NoVetFoundException("Vet user with given id not found")));
        PetOwner petOwner = findPetOwnerById(dto.getPetOwnerId());
        if (optionalVetUser.isPresent() && petOwner != null) {

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
