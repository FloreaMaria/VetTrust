package com.example.vettrust.service;

import com.example.vettrust.dto.AppointmentDto;
import com.example.vettrust.enums.AppointmentStatus;
import com.example.vettrust.model.*;
import com.example.vettrust.repository.AppointmentRepository;
import com.example.vettrust.repository.AppointmentTypeRepository;
import com.example.vettrust.repository.PetRepository;
import com.example.vettrust.repository.VetUserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final PetOwnerService petOwnerService;
    private final VetUserRepository vetUserRepository;
    private final AppointmentTypeRepository appointmentTypeRepository;


    public AppointmentService(AppointmentRepository appointmentRepository, PetRepository petRepository, PetOwnerService petOwnerService, VetUserRepository vetUserRepository, AppointmentTypeRepository appointmentTypeRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.petOwnerService = petOwnerService;
        this.vetUserRepository = vetUserRepository;

        this.appointmentTypeRepository = appointmentTypeRepository;
    }

    public AppointmentDto saveAppointment(@NotNull AppointmentDto appointmentDto) throws ParseException {
        Appointment appointment =  new Appointment();
        Optional<Pet> pet = petRepository.findById(appointmentDto.getPetId());
        if(pet.isPresent()){
            PetOwner petOwner = petOwnerService.findPetOwnerById(pet.get().getPetOwner().getId());
            if(petOwner != null){
                Optional<VetUser> vetUser = vetUserRepository.findById(appointmentDto.getVetUserId());
                if(vetUser.isPresent()){
                    ClinicLocation clinicLocation = vetUser.get().getClinicLocation();
                    if(clinicLocation != null){
                        Optional<AppointmentType> appointmentType =  appointmentTypeRepository.findByName(appointmentDto.getAppointmentType());
                        if(appointmentType.isPresent()){
                            //created appointment
                            appointment.setAppointmentType(appointmentType.get());
                            appointment.setAppointmentStatus(AppointmentStatus.VALID);
                            appointment.setClinicLocation(clinicLocation);
                            appointment.setPet(pet.get());
                            appointment.setVetUser(vetUser.get());

                            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(appointmentDto.getDate());
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                            appointment.setDate(formatter.format(date));

                            appointmentRepository.save(appointment);
                            return AppointmentDto.entityToDto(appointment);
                        }
                    }
                }
            }
        }
        return null;
    }

    public AppointmentDto updateAppointment(@NotNull Long appointmentId, @NotNull AppointmentDto appointmentDto){
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if(appointment.isPresent()){
            Appointment updatedAppointment = appointment.get();
            if(appointmentDto.getAppointmentStatus() != null){
                updatedAppointment.setAppointmentStatus(appointmentDto.getAppointmentStatus());
            }
            if(appointmentDto.getDate() != null){
                updatedAppointment.setDate(appointmentDto.getDate());
            }
            appointmentRepository.save(updatedAppointment);
            return AppointmentDto.entityToDto(updatedAppointment);
        }
        return null;
    }

    public List<AppointmentDto> getAllAppointmentsForPet(@NotNull Long petId){
        List<Appointment> appointments = appointmentRepository.findByPetId(petId);
        return appointments.stream().map(AppointmentDto::entityToDto).collect(toList());
    }


}
