package com.example.vettrust.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.vettrust.model.PetOwnerMock.petOwner;

public class PetMock {
    public static @NotNull Pet pet() {
        Pet pet = new Pet();
        pet.setName("pet");
        pet.setPetOwner(petOwner());
        pet.setAge(1L);
        pet.setType("DOG");
        pet.setAppointments(new ArrayList<>());
        pet.setId(1L);
        return pet;
    }
}
