package com.example.vettrust.model;

import com.example.vettrust.dto.PetDto;
import jdk.jfr.Name;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.vettrust.model.PetOwnerMock.petOwner;

public class PetDtoMock {
    public static @NotNull PetDto petDto(){
        PetDto pet = new PetDto();
        pet.setName("pet");
        pet.setAge(1L);
        pet.setType("DOG");
        return pet;
    }
}
