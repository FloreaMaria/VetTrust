package com.example.vettrust.dto;


import com.example.vettrust.model.VetReview;
import com.example.vettrust.model.VetSchedule;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class VetScheduleDto {
    private String workingDay;

    private String workingHours;

    private String availableWorkingHours;

    private Long vetId;

    private Long clinicLocationId;

    public static @NotNull VetScheduleDto entityToDto(@NotNull VetSchedule vetSchedule) {
        VetScheduleDto dto = new VetScheduleDto();
        dto.setVetId(vetSchedule.getVetUser().getId());
        dto.setWorkingHours(vetSchedule.getWorkingHours());
        dto.setWorkingDay(vetSchedule.getWorkingDay());
        dto.setAvailableWorkingHours(vetSchedule.getAvailableWorkingHours());
        dto.setClinicLocationId(vetSchedule.getClinicLocation().getId());
        return dto;
    }
}
