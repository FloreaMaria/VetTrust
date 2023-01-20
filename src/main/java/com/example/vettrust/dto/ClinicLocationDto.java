package com.example.vettrust.dto;

import com.example.vettrust.model.ClinicLocation;
import com.example.vettrust.model.VetSchedule;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class ClinicLocationDto {
    private Boolean availability;
    private String city;
    private String address;
    private List<VetSchedule> vetSchedules;


    public static @NotNull ClinicLocationDto entityToDto(@NotNull ClinicLocation location) {
        ClinicLocationDto dto = new ClinicLocationDto();
        dto.setAvailability(location.getAvailability());
        dto.setCity(location.getCity());
        dto.setAddress(location.getAddress());
        dto.setVetSchedules(location.getVetSchedules());
        return dto;
    }
}
