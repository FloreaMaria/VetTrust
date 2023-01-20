package com.example.vettrust.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PetOwnerMock {
    public static @NotNull PetOwner petOwner(){
        PetOwner petOwner = new PetOwner();
        petOwner.setId(1L);
        petOwner.setFirstName("first");
        petOwner.setLastName("last");
        petOwner.setEmail("email");
        petOwner.setPassword("password");
        petOwner.setPhoneNumber("0738838944");
        petOwner.setPets(new ArrayList<>());
        petOwner.setVetReviews(new ArrayList<>());
        return petOwner;
    }
}
