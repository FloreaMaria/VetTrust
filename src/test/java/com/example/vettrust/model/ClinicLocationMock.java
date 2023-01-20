package com.example.vettrust.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.vettrust.model.VetUserMock.vetUser;

public class ClinicLocationMock {
    public static @NotNull ClinicLocation clinicLocation() {
        ClinicLocation clinicLocation = new ClinicLocation();
        clinicLocation.setId(1L);
        clinicLocation.setAddress("address");
        clinicLocation.setAvailability(true);
        clinicLocation.setCity("city");
        clinicLocation.setVetSchedules(new ArrayList<>());
        return clinicLocation;
    }
}
