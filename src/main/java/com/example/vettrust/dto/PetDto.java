package com.example.vettrust.dto;

import com.example.vettrust.model.Pet;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class PetDto {
    private Long age;
    private String name;
    private String type;
    private Long petOwnerId;

    public static @NotNull PetDto entityToDto(@NotNull Pet pet) {
        PetDto dto = new PetDto();
        dto.setAge(pet.getAge());
        dto.setName(pet.getName());
        dto.setType(pet.getType());
        dto.setPetOwnerId(pet.getPetOwner().getId());
        return dto;
    }
}
