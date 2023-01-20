package com.example.vettrust.service;

import com.example.vettrust.dto.VetScheduleDto;
import com.example.vettrust.enums.AppointmentValueType;
import com.example.vettrust.model.Appointment;
import com.example.vettrust.model.DiagnosisConclusion;
import com.example.vettrust.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.vettrust.model.AppointmentMock.appointment;
import static com.example.vettrust.model.AppointmentTypeMock.appointmentType;
import static com.example.vettrust.model.DiagnosisConclusionMock.diagnosisConclusion;
import static com.example.vettrust.model.PetMock.pet;
import static com.example.vettrust.model.VetScheduleDtoMock.vetScheduleDto;
import static com.example.vettrust.model.VetUserMock.vetUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppointmentServiceTests {

    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    AppointmentTypeRepository appointmentTypeRepository;
    @Mock
    VetScheduleService vetScheduleService;

    @Mock
    DiagnosisConclusionRepository diagnosisConclusionRepository;

    @Mock
    VetUserService vetUserService;

    @InjectMocks
    AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById(){
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment()));
        Appointment result = appointmentService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void save(){
        Appointment appointment = new Appointment();
        appointment.setId(1L);

        VetScheduleDto vetScheduleDto = new VetScheduleDto();
        vetScheduleDto.setWorkingDay("Friday");

        DiagnosisConclusion diagnosisConclusion = new DiagnosisConclusion();
        diagnosisConclusion.setDiagnosis("diagnosis");

        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet()));
        when(vetScheduleService.findAllByVetUserIdAndClinicLocationId(1L, 1L)).thenReturn(new ArrayList<>());
        when(appointmentTypeRepository.findById(anyLong())).thenReturn(Optional.of(appointmentType()));
        when(vetScheduleService.updateHours(1L, vetScheduleDto())).thenReturn(vetScheduleDto);
        when(diagnosisConclusionRepository.save(diagnosisConclusion)).thenReturn(diagnosisConclusion());
        when(appointmentRepository.save(appointment)).thenReturn(appointment());

        Appointment appointmentResult = appointmentRepository.save(appointment);

        assertEquals(appointmentResult.getId(), appointment.getId());
    }


    @Test
    void getAllAppointmentsForPet(){
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet()));
        when(appointmentRepository.findByPetId(anyLong())).thenReturn(new ArrayList<>());
        assertEquals(0, appointmentService.getAllAppointmentsForPet(1L).size());
    }

    @Test
    void getAllAppointmentsForVet(){
        when(vetUserService.findById(anyLong())).thenReturn((vetUser()));
        when(appointmentRepository.findByPetId(anyLong())).thenReturn(new ArrayList<>());
        assertEquals(0, appointmentService.getAllAppointmentsForVet(1L).size());
    }

    @Test
    void getAllSpecificTypeAppointmentsForVet(){
        when(vetUserService.findById(anyLong())).thenReturn((vetUser()));
        AppointmentValueType appointmentValueType = AppointmentValueType.CONSULTATION;
        when(appointmentRepository.findByVetScheduleVetUserIdAndAppointmentTypeAppointmentValueType(1L,appointmentValueType)).thenReturn(new ArrayList<>());
        assertEquals(0, appointmentService.getAllSpecificTypeAppointmentsForVet(1L, appointmentValueType).size());
    }

    @Test
    void getAllSpecificDateAppointmentsForVet(){
        when(vetUserService.findById(anyLong())).thenReturn((vetUser()));
        when(appointmentRepository.findByVetScheduleVetUserIdAndDate(1L, "2022-10-10")).thenReturn(new ArrayList<>());
        assertEquals(0, appointmentService.getAllSpecificDateAppointmentsForVet(1L, "2022-10-10").size());
    }

    @Test
    void delete(){
        Appointment appointment = appointment();
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));
        appointmentService.deleteAppointment(appointment.getId());
        verify(appointmentRepository).delete(appointment);
    }

}
