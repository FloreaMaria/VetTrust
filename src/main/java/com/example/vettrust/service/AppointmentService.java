package com.example.vettrust.service;

import com.example.vettrust.repository.AppointmentRepository;
import com.example.vettrust.repository.PetRepository;
import com.example.vettrust.repository.VetUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final PetOwnerService petOwnerService;
    private final VetUserRepository vetUserRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PetRepository petRepository, PetOwnerService petOwnerService, VetUserRepository vetUserRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.petOwnerService = petOwnerService;
        this.vetUserRepository = vetUserRepository;
    }

}
