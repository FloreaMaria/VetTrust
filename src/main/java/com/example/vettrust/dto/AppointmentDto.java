package com.example.vettrust.dto;

import com.example.vettrust.enums.AppointmentStatus;
import com.example.vettrust.model.Appointment;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
public class AppointmentDto {
    private String date;
    private AppointmentStatus appointmentStatus;
    private Long petId;
    private Long vetUserId;
    private Long clinicLocationId;
    private String appointmentType;
    private List<NotificationDto> notificationDtoList;

    public static @NotNull AppointmentDto entityToDto(@NotNull Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentStatus(appointment.getAppointmentStatus());
        appointmentDto.setAppointmentType(appointment.getAppointmentType().getName());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setPetId(appointment.getPet().getId());
        appointmentDto.setClinicLocationId(appointment.getClinicLocation().getId());
        appointmentDto.setVetUserId(appointment.getVetUser().getId());
        return appointmentDto;
    }
}
