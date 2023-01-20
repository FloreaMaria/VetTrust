package com.example.vettrust.model;

import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.dto.user.VetUserDto;
import org.jetbrains.annotations.NotNull;

import static com.example.vettrust.model.VetUserMock.vetUser;

public class VetReviewMock {
    public static @NotNull VetReview vetReview() {
        VetReview vetReview = new VetReview();
        PetOwner petOwner = new PetOwner();
        petOwner.setId(1L);
        vetReview.setPetOwner(petOwner);
        vetReview.setId(1L);
        vetReview.setVetUser(vetUser());
        vetReview.setComment("Great");
        vetReview.setStars(4);
        return vetReview;
    }
}
