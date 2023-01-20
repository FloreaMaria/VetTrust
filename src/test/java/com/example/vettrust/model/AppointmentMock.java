package com.example.vettrust.model;

import com.example.vettrust.enums.AppointmentStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.vettrust.model.PetMock.pet;
import static com.example.vettrust.model.VetScheduleMock.vetSchedule;

public class AppointmentMock {
    public static @NotNull Appointment appointment(){
        Appointment appointment =  new Appointment();
        appointment.setId(1L);
        appointment.setAppointmentStatus(AppointmentStatus.VALID);
        appointment.setNotifications(new ArrayList<>());
        appointment.setHour("10:00");
        appointment.setDate("2022-10-10");
        appointment.setVetSchedule(vetSchedule());
        appointment.setPet(pet());
        appointment.setAppointmentType(new AppointmentType());
        appointment.setDiagnosisConclusion(new DiagnosisConclusion());

        return appointment;
    }
}
