package com.example.vettrust.dto.user;

import com.example.vettrust.dto.VetReviewDto;
import com.example.vettrust.dto.VetScheduleDto;
import com.example.vettrust.model.VetSchedule;
import com.example.vettrust.model.VetUser;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
public class VetUserDto extends BaseUserDto {
    private Set<VetScheduleDto> vetSchedules;
    private List<VetReviewDto> vetReviews;

    public static @NotNull VetUserDto entityToDto(@NotNull VetUser vetUser) {
        VetUserDto dto = new VetUserDto();
        dto.setEmail(vetUser.getEmail());
        dto.setFirstName(vetUser.getFirstName());
        dto.setLastName(vetUser.getLastName());
        dto.setPhoneNumber(vetUser.getPhoneNumber());
        dto.setVetReviews(vetUser.getVetReviews().stream().map(VetReviewDto::entityToDto).collect(toList()));
        dto.setVetSchedules(vetUser.getVetSchedules().stream().map(VetScheduleDto::entityToDto).collect(Collectors.toSet()));
        return dto;
    }
}