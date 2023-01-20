package com.example.vettrust.model;

import com.example.vettrust.dto.user.PetOwnerDto;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PetOwnerDtoMock {
    public static @NotNull PetOwnerDto petOwnerDto(){
        PetOwnerDto petOwner = new PetOwnerDto();
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
