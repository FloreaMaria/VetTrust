package com.example.vettrust.dto;

import com.example.vettrust.model.Appointment;
import com.example.vettrust.model.VetReview;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
public class AppointmentDto {
    private String date;
    private String appointmentStatus;
    private Long historyId;
    private Long vetUserId;
    private Long clinicLocationId;
    private String appointmentType;
    private List<NotificationDto> notificationDtoList;
    public static @NotNull AppointmentDto entityToDto(@NotNull Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentStatus(appointment.getAppointmentStatus().toString());
        appointmentDto.setAppointmentType(appointmentDto.getAppointmentType());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setHistoryId(appointment.getHistory().getId());
        appointmentDto.setClinicLocationId(appointment.getClinicLocation().getId());
        appointmentDto.setVetUserId(appointment.getVetUser().getId());
        return appointmentDto;
    }
}
