package com.example.vettrust.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.vettrust.model.ClinicLocationMock.clinicLocation;
import static com.example.vettrust.model.VetUserMock.vetUser;

public class VetScheduleMock {
    public static @NotNull VetSchedule vetSchedule() {
        VetSchedule vetSchedule = new VetSchedule();
        vetSchedule.setWorkingHours("10:00-12:00");
        vetSchedule.setVetUser(vetUser());
        vetSchedule.setClinicLocation(clinicLocation());
        vetSchedule.setAvailableWorkingHours("10:00-12:00");
        vetSchedule.setAppointments(new ArrayList<>());
        vetSchedule.setId(1L);
        vetSchedule.setWorkingDay("Monday");
        return vetSchedule;
    }
}
