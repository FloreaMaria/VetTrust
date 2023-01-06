package com.example.vettrust.dto;

import com.example.vettrust.model.Appointment;
import com.example.vettrust.model.ClinicLocation;
import com.example.vettrust.model.VetUser;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import org.jetbrains.annotations.NotNull;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
public class ClinicLocationDto {
    private Boolean availability;
    private String city;
    private String address;
    private List<VetUser> vetUserList;
    private List<AppointmentDto> appointmentList;


    public static @NotNull ClinicLocationDto entityToDto(@NotNull ClinicLocation location) {
        ClinicLocationDto dto = new ClinicLocationDto();
        dto.setAvailability(location.getAvailability());
        dto.setCity(location.getCity());
        dto.setAddress(location.getAddress());
        dto.setVetUserList(location.getVetUserList());
        dto.setAppointmentList(location.getAppointments().stream().map(AppointmentDto::entityToDto).collect(toList()));
        return dto;
    }
}
