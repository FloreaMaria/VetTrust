package com.example.vettrust.dto.user;

import com.example.vettrust.dto.PetDto;
import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.model.PetOwner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
public class PetOwnerDto extends BaseUserDto{

    private List<PetDto> pets;
    private List<VetReviewDto> vetReviews;

    public static @NotNull PetOwnerDto entityToDto(@NotNull PetOwner petOwner) {
        PetOwnerDto dto = new PetOwnerDto();
        dto.setEmail(petOwner.getEmail());
        dto.setFirstName(petOwner.getFirstName());
        dto.setLastName(petOwner.getLastName());
        dto.setPhoneNumber(petOwner.getPhoneNumber());
        dto.setPets(petOwner.getPets().stream().map(PetDto::entityToDto).collect(toList()));
        dto.setVetReviews(petOwner.getVetReviews().stream().map(VetReviewDto::entityToDto).collect(toList()));
        return dto;
    }
}
