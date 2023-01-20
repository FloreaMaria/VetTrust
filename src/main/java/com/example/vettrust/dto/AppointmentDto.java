package com.example.vettrust.dto;

import com.example.vettrust.enums.AppointmentStatus;
import com.example.vettrust.model.Appointment;
import com.example.vettrust.model.Notification;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
public class AppointmentDto {
    private String date;
    private String hour;
    private AppointmentStatus appointmentStatus;
    private Long petId;
    private Long vetId;
    private Long clinicLocationId;
    private String appointmentType;
    private Double price;
    private DiagnosisConclusionDto diagnosisConclusionDto;
    private List<NotificationDto> notificationDtoList;

    public static @NotNull AppointmentDto entityToDto(@NotNull Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentStatus(appointment.getAppointmentStatus());
        appointmentDto.setAppointmentType(appointment.getAppointmentType().getName());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setPetId(appointment.getPet().getId());
        appointmentDto.setClinicLocationId(appointment.getVetSchedule().getClinicLocation().getId());
        appointmentDto.setVetId(appointment.getVetSchedule().getVetUser().getId());
        appointmentDto.setHour(appointment.getHour());
        appointmentDto.setPrice(appointment.getAppointmentType().getPrice());
        appointmentDto.setDiagnosisConclusionDto(DiagnosisConclusionDto.entityToDto(appointment.getDiagnosisConclusion()));

        if(appointment.getNotifications() != null) {
            appointmentDto.setNotificationDtoList(appointment.getNotifications()
                    .stream().map(NotificationDto::entityToDto).collect(toList()));
        } else {
            appointmentDto.setNotificationDtoList(new ArrayList<>());
        }
        return appointmentDto;
    }
}

