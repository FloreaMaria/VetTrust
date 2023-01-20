package com.example.vettrust.service;

import com.example.vettrust.dto.VetScheduleDto;
import com.example.vettrust.model.VetSchedule;
import com.example.vettrust.repository.ClinicLocationRepository;
import com.example.vettrust.repository.VetScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.vettrust.model.ClinicLocationMock.clinicLocation;
import static com.example.vettrust.model.VetScheduleDtoMock.vetScheduleDto;
import static com.example.vettrust.model.VetScheduleMock.vetSchedule;
import static com.example.vettrust.model.VetUserMock.vetUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VetScheduleServiceTests {
    @Mock
    VetScheduleRepository vetScheduleRepository;

    @Mock
    VetUserService vetUserService;

    @Mock
    ClinicLocationRepository clinicLocationRepository;

    @InjectMocks
    VetScheduleService vetScheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveVetSchedule(){
        VetSchedule vetSchedule = new VetSchedule();
        vetSchedule.setWorkingHours("10:00-12:00");

        when(vetUserService.findById(anyLong())).thenReturn(vetUser());
        when(clinicLocationRepository.findById(anyLong())).thenReturn(Optional.of(clinicLocation()));
        when(vetScheduleRepository.save(vetSchedule)).thenReturn(vetSchedule());
        VetSchedule vetScheduleResult = vetScheduleRepository.save(vetSchedule);
        assertEquals(vetSchedule.getWorkingHours(), vetScheduleResult.getWorkingHours());
    }

    @Test
    void findById(){
        when(vetScheduleRepository.findById(anyLong())).thenReturn(Optional.of(vetSchedule()));
        VetSchedule result = vetScheduleService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void findAllByVetId(){
        when(vetUserService.findById(anyLong())).thenReturn(vetUser());
        List<VetScheduleDto> vetSchedules = new ArrayList<>();
        vetSchedules.add(vetScheduleDto());
        when(vetScheduleService.findAllByVetUserId(anyLong())).thenReturn(vetSchedules);
        assertEquals(1, vetSchedules.size());
    }

    @Test
    void findAllByVetUserIdAndClinicLocationId(){
        when(vetUserService.findById(anyLong())).thenReturn(vetUser());
        when(clinicLocationRepository.findById(anyLong())).thenReturn(Optional.of(clinicLocation()));
        List<VetSchedule> vetSchedules = new ArrayList<>();
        vetSchedules.add(vetSchedule());
        when(vetScheduleService.findAllByVetUserIdAndClinicLocationId(1L,1L)).thenReturn(vetSchedules);
        assertTrue(vetSchedules.size() >=1);
    }

    @Test
    void updateHours(){
        VetScheduleDto vetSchedule = new VetScheduleDto();
        when(vetScheduleRepository.findById(anyLong())).thenReturn(Optional.of(vetSchedule()));
        when(vetScheduleRepository.save(any())).thenReturn(vetSchedule());
        VetScheduleDto vetScheduleDto = vetScheduleService.updateHours(1L, vetSchedule);
        assertNotNull(vetScheduleDto);
    }


    @Test
    void delete(){
        VetSchedule vetSchedule = vetSchedule();
        when(vetScheduleRepository.findById(anyLong())).thenReturn(Optional.of(vetSchedule));
        vetScheduleService.deleteSchedule(vetSchedule.getId());
        verify(vetScheduleRepository).delete(vetSchedule);
    }




}
