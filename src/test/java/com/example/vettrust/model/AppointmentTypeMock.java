package com.example.vettrust.model;

import com.example.vettrust.enums.AppointmentValueType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AppointmentTypeMock {
    public static @NotNull AppointmentType appointmentType(){
        AppointmentType appointmentType = new AppointmentType();
        appointmentType.setId(1L);
        appointmentType.setPrice(100.0);
        appointmentType.setAppointmentValueType(AppointmentValueType.CONSULTATION);
        appointmentType.setAppointments(new ArrayList<>());

        return appointmentType;
    }
}
