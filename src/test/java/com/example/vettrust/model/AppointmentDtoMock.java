package com.example.vettrust.model;

import com.example.vettrust.dto.AppointmentDto;
import com.example.vettrust.dto.DiagnosisConclusionDto;
import com.example.vettrust.enums.AppointmentStatus;
import com.example.vettrust.enums.AppointmentValueType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.vettrust.model.DiagnosisConclusionDtoMock.diagnosisConclusionDto;
import static com.example.vettrust.model.PetMock.pet;
import static com.example.vettrust.model.VetScheduleMock.vetSchedule;

public class AppointmentDtoMock {
    public static @NotNull AppointmentDto appointmentDto(){
        AppointmentDto appointment = new AppointmentDto();

        appointment.setAppointmentStatus(AppointmentStatus.VALID);
        appointment.setHour("10:00");
        appointment.setDate("2022-10-10");
        appointment.setDiagnosisConclusionDto(diagnosisConclusionDto());
        appointment.setPrice(200.0);
        appointment.setNotificationDtoList(new ArrayList<>());
        appointment.setClinicLocationId(1L);
        appointment.setVetId(1L);
        appointment.setAppointmentValueType(AppointmentValueType.CONSULTATION);

        return appointment;
    }
}
