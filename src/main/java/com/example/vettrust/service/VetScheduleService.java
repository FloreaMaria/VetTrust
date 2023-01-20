package com.example.vettrust.service;


import com.example.vettrust.dto.VetScheduleDto;
import com.example.vettrust.exception.NoClinicLocationFoundException;
import com.example.vettrust.exception.NoVetScheduleFoundException;
import com.example.vettrust.model.ClinicLocation;
import com.example.vettrust.model.VetSchedule;
import com.example.vettrust.model.VetUser;
import com.example.vettrust.repository.ClinicLocationRepository;
import com.example.vettrust.repository.VetScheduleRepository;
import com.example.vettrust.repository.VetUserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class VetScheduleService {


    private final VetScheduleRepository vetScheduleRepository;
    private final VetUserService vetUserService;
    private final ClinicLocationRepository clinicLocationRepository;

    @Autowired
    public VetScheduleService(VetScheduleRepository vetScheduleRepository, VetUserService vetUserService, ClinicLocationRepository clinicLocationRepository) {
        this.vetScheduleRepository = vetScheduleRepository;
        this.vetUserService = vetUserService;
        this.clinicLocationRepository = clinicLocationRepository;
    }

    public VetScheduleDto save(@NotNull @Valid VetScheduleDto vetScheduleDto){
        VetSchedule vetSchedule = new VetSchedule();
        VetUser vetUser = vetUserService.findById(vetScheduleDto.getVetId());
        ClinicLocation clinicLocation = clinicLocationRepository.findById(vetScheduleDto.getClinicLocationId()).orElseThrow(() ->
                new NoClinicLocationFoundException("Clinic location with given id was not found"));
        vetSchedule.setWorkingHours(vetScheduleDto.getWorkingHours());
        vetSchedule.setVetUser(vetUser);
        vetSchedule.setClinicLocation(clinicLocation);
        vetSchedule.setAvailableWorkingHours(vetScheduleDto.getAvailableWorkingHours());
        vetSchedule.setWorkingDay(vetScheduleDto.getWorkingDay());
        return VetScheduleDto.entityToDto(vetScheduleRepository.save(vetSchedule));

    }

    public VetSchedule findById(@NotNull Long id){
        return vetScheduleRepository.findById(id).orElseThrow(
                () -> new NoVetScheduleFoundException("Vet Schedule with given id was not found"));
    }

    @Transactional
    public List<VetScheduleDto> findAllByVetUserId(@NotNull Long id) {
        VetUser vetUser = vetUserService.findById(id);
        List<VetSchedule> vetSchedules = vetScheduleRepository.findAllByVetUserId(id);
        return vetSchedules.stream().map(VetScheduleDto::entityToDto).collect(toList());
    }

    @Transactional
    public List<VetSchedule> findAllByVetUserIdAndClinicLocationId(@NotNull Long vetId, @NotNull Long clinicLocationId) {
        VetUser vetUser = vetUserService.findById(vetId);
        ClinicLocation clinicLocation = clinicLocationRepository.findById(clinicLocationId).orElseThrow(() ->
                new NoClinicLocationFoundException("Clinic location with given id was not found"));
        List<VetSchedule> vetSchedules = vetScheduleRepository.findAllByVetUserId(vetId);
        return vetSchedules.stream().filter(c -> c.getClinicLocation().getId().equals(clinicLocationId)).collect(Collectors.toList());
    }


    public VetScheduleDto updateHours(@NotNull Long id, @NotNull VetScheduleDto vetScheduleDto){
        VetSchedule vetScheduleUpdated = findById(id);

        if(vetScheduleDto.getAvailableWorkingHours() != null){
            vetScheduleUpdated.setAvailableWorkingHours(vetScheduleDto.getAvailableWorkingHours());
        }
        if(vetScheduleDto.getWorkingHours() != null){
            vetScheduleUpdated.setWorkingHours(vetScheduleUpdated.getWorkingHours());
        }
        return VetScheduleDto.entityToDto(vetScheduleRepository.save(vetScheduleUpdated));

    }

    public boolean deleteSchedule(@NotNull Long scheduleId){
        VetSchedule vetSchedule = findById(scheduleId);
        vetScheduleRepository.delete(vetSchedule);
        return true;
    }
}
