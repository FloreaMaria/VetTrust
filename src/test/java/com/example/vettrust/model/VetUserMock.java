package com.example.vettrust.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;

public class VetUserMock {
    public static @NotNull VetUser vetUser() {
        VetUser vet = new VetUser();
        vet.setId(1L);
        vet.setRating(0.0);
        vet.setEmail("vet@gmail.com");
        vet.setFirstName("vet");
        vet.setLastName("vet");
        vet.setPhoneNumber("0738838944");
        vet.setPassword("password");
        vet.setVetReviews(new ArrayList<>());
        vet.setVetSchedules(new HashSet<>());
        return vet;
    }
}
