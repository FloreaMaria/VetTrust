package com.example.vettrust.model;

import com.example.vettrust.dto.VetScheduleDto;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.vettrust.model.ClinicLocationMock.clinicLocation;
import static com.example.vettrust.model.VetUserMock.vetUser;

public class VetScheduleDtoMock {
    public static @NotNull VetScheduleDto vetScheduleDto() {
        VetScheduleDto vetScheduleDto = new VetScheduleDto();
        vetScheduleDto.setWorkingHours("10:00-12:00");
        vetScheduleDto.setClinicLocationId(1L);
        vetScheduleDto.setAvailableWorkingHours("10:00-12:00");
        vetScheduleDto.setWorkingDay("Monday");
        vetScheduleDto.setVetId(1L);
        return vetScheduleDto;
    }
}
