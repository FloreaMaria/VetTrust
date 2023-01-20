package com.example.vettrust.service;


import com.example.vettrust.dto.PetDto;
import com.example.vettrust.dto.VetScheduleDto;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class VetScheduleService {


    private final VetScheduleRepository vetScheduleRepository;
    private final VetUserRepository vetUserRepository;
    private final ClinicLocationRepository clinicLocationRepository;

    @Autowired
    public VetScheduleService(VetScheduleRepository vetScheduleRepository, VetUserRepository vetUserRepository, ClinicLocationRepository clinicLocationRepository) {
        this.vetScheduleRepository = vetScheduleRepository;
        this.vetUserRepository = vetUserRepository;
        this.clinicLocationRepository = clinicLocationRepository;
    }

    public VetScheduleDto save(@NotNull VetScheduleDto vetScheduleDto){
        VetSchedule vetSchedule = new VetSchedule();
        Optional<VetUser> vetUser = vetUserRepository.findById(vetScheduleDto.getVetId());
        if(vetUser.isPresent()){
            Optional<ClinicLocation> clinicLocation = clinicLocationRepository.findById(vetScheduleDto.getClinicLocationId());
            if(clinicLocation.isPresent()){
                vetSchedule.setWorkingHours(vetScheduleDto.getWorkingHours());
                vetSchedule.setVetUser(vetUser.get());
                vetSchedule.setClinicLocation(clinicLocation.get());
                vetSchedule.setAvailableWorkingHours(vetScheduleDto.getAvailableWorkingHours());
                vetSchedule.setWorkingDay(vetScheduleDto.getWorkingDay());
                return VetScheduleDto.entityToDto(vetScheduleRepository.save(vetSchedule));
            }
        }
        return null;
    }

    public Optional<VetSchedule> findById(Long id){
        return vetScheduleRepository.findById(id);
    }

    @Transactional
    public List<VetScheduleDto> findAllByVetUserId(Long id) {
        List<VetSchedule> vetSchedules = vetScheduleRepository.findAllByVetUserId(id);
        return vetSchedules.stream().map(VetScheduleDto::entityToDto).collect(toList());
    }

    @Transactional
    public List<VetSchedule> findAllByVetUserIdAndClinicLocationId(Long vetId, Long clinicLocationId) {
        List<VetSchedule> vetSchedules = vetScheduleRepository.findAllByVetUserId(vetId);
        return vetSchedules.stream().filter(c -> c.getClinicLocation().getId().equals(clinicLocationId)).collect(Collectors.toList());
    }

    public VetScheduleDto updateHours(Long id, VetScheduleDto vetScheduleDto){
        Optional<VetSchedule> vetSchedule = vetScheduleRepository.findById(id);
        if(vetSchedule.isPresent()){
            VetSchedule vetScheduleUpdated = vetSchedule.get();
            if(vetScheduleDto.getAvailableWorkingHours() != null){
                vetScheduleUpdated.setAvailableWorkingHours(vetScheduleDto.getAvailableWorkingHours());
            }
            if(vetScheduleDto.getWorkingHours() != null){
                vetScheduleUpdated.setWorkingHours(vetScheduleUpdated.getWorkingHours());
            }
            return VetScheduleDto.entityToDto(vetScheduleRepository.save(vetScheduleUpdated));

        }
        return null;
    }

    public boolean deleteSchedule(Long scheduleId){
        if(scheduleId != null){
            Optional<VetSchedule> vetSchedule = findById(scheduleId);
            if(vetSchedule.isPresent()){
                vetScheduleRepository.delete(vetSchedule.get());
                return true;
            }
        }
        return false;
    }
}
