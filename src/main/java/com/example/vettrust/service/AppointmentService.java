package com.example.vettrust.service;

import com.example.vettrust.dto.AppointmentDto;
import com.example.vettrust.dto.VetScheduleDto;
import com.example.vettrust.enums.AppointmentStatus;
import com.example.vettrust.enums.NotificationType;
import com.example.vettrust.model.*;
import com.example.vettrust.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final PetOwnerService petOwnerService;
    private final VetUserRepository vetUserRepository;
    private final AppointmentTypeRepository appointmentTypeRepository;
    private final VetScheduleService vetScheduleService;
    private final NotificationRepository notificationRepository;
    private final VetReviewRepository vetReviewRepository;
    private final DiagnosisConclusionRepository diagnosisConclusionRepository;


    public AppointmentService(AppointmentRepository appointmentRepository, PetRepository petRepository, PetOwnerService petOwnerService, VetUserRepository vetUserRepository, AppointmentTypeRepository appointmentTypeRepository, VetScheduleService vetScheduleService, NotificationRepository notificationRepository, VetReviewRepository vetReviewRepository, DiagnosisConclusionRepository diagnosisConclusionRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.petOwnerService = petOwnerService;
        this.vetUserRepository = vetUserRepository;
        this.appointmentTypeRepository = appointmentTypeRepository;
        this.vetScheduleService = vetScheduleService;
        this.notificationRepository = notificationRepository;
        this.vetReviewRepository = vetReviewRepository;
        this.diagnosisConclusionRepository = diagnosisConclusionRepository;
    }


    @Transactional
    public Appointment findById(Long id){
        return appointmentRepository.findById(id).orElseThrow();
    }

    public AppointmentDto saveAppointment(@NotNull AppointmentDto appointmentDto) throws ParseException {
        Appointment appointment =  new Appointment();
        Optional<Pet> pet = petRepository.findById(appointmentDto.getPetId());
        if(pet.isPresent()){
            List<VetSchedule> vetSchedules = vetScheduleService.findAllByVetUserIdAndClinicLocationId(appointmentDto.getVetId(),
                    appointmentDto.getClinicLocationId());
            if(vetSchedules !=null){
                String chosenHour = appointmentDto.getHour();
                LocalDate chosenDate = LocalDate.parse(appointmentDto.getDate());
                for (VetSchedule vetSchedule: vetSchedules) {
                    String workingDay =vetSchedule.getWorkingDay().toUpperCase(Locale.ROOT);
                    if(chosenDate.getDayOfWeek().toString().toUpperCase(Locale.ROOT).equals(workingDay)){
                        String availableWorkingHours = vetSchedule.getAvailableWorkingHours();
                        Set<String> availableWorkingHoursSet = new HashSet<String>(Arrays.asList(availableWorkingHours.split(",")));
                        for (String availableHour: availableWorkingHoursSet) {
                            if(chosenHour.equals(availableHour.trim())){
                                Optional<AppointmentType> appointmentType =  appointmentTypeRepository.findByName(appointmentDto.getAppointmentType());
                                if(appointmentType.isPresent()){

                                    availableWorkingHoursSet.remove(availableHour);
                                    vetSchedule.setAvailableWorkingHours(String.join(",", availableWorkingHoursSet));
                                    vetScheduleService.updateHours(vetSchedule.getId(), VetScheduleDto.entityToDto(vetSchedule));

                                    appointment.setAppointmentType(appointmentType.get());
                                    appointment.setAppointmentStatus(AppointmentStatus.VALID);
                                    appointment.setPet(pet.get());
                                    appointment.setVetSchedule(vetSchedule);
                                    appointment.setHour(chosenHour);
                                    appointment.setDate(chosenDate.toString());

                                    DiagnosisConclusion diagnosisConclusion = new DiagnosisConclusion();
                                    diagnosisConclusion.setVetUser(vetSchedule.getVetUser());
                                    diagnosisConclusionRepository.save(diagnosisConclusion);
                                    appointment.setDiagnosisConclusion(diagnosisConclusion);

                                    appointmentRepository.save(appointment);
                                    Notification notification = new Notification();
                                    notification.setNotificationType(NotificationType.REMINDER_APPOINTMENT);
                                    notification.setMessage("Your appointment has been created");
                                    notification.setAppointment(appointment);
                                    notificationRepository.save(notification);

                                    return AppointmentDto.entityToDto(appointment);
                                }
                            }
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
            Notification notification = new Notification();
            notification.setSeen(false);
            if(appointmentDto.getAppointmentStatus() != null){
                updatedAppointment.setAppointmentStatus(appointmentDto.getAppointmentStatus());

                if(appointmentDto.getAppointmentStatus().equals(AppointmentStatus.CANCELED)){
                    notification.setNotificationType(NotificationType.CANCELED_APPOINTMENT);
                    notification.setMessage("Your appointment on " + appointment.get().getDate() + " was successfully canceled");
                }else if(appointmentDto.getAppointmentStatus().equals(AppointmentStatus.FULFILLED)){
                    notification.setNotificationType(NotificationType.PLEASE_SEND_FEEDBACK);
                    notification.setMessage("We hope you enjoyed your appointment, don't forget to give us feedback on it");
                }else if(appointmentDto.getAppointmentStatus().equals(AppointmentStatus.VALID)){
                    notification.setNotificationType(NotificationType.REMINDER_APPOINTMENT);
                    notification.setMessage("Your appointment has been created");
                }else if(appointmentDto.getAppointmentStatus().equals(AppointmentStatus.UNFULFILLED)){
                    notification.setNotificationType(NotificationType.RESCHEDULE_APPOINTMENT);
                    notification.setMessage("Due to you unfulfilling your appointment maybe you should reschedule");
                }
                notification.setAppointment(updatedAppointment);

            }
            if(appointmentDto.getDate() != null){
                updatedAppointment.setDate(appointmentDto.getDate());
            }

            System.out.println(appointmentDto.getDiagnosisConclusionDto());
            if(appointmentDto.getDiagnosisConclusionDto() != null){
                Optional<DiagnosisConclusion> optionalDiagnosisConclusion = diagnosisConclusionRepository.findById(appointment.get().getDiagnosisConclusion().getId());
                if(optionalDiagnosisConclusion.isPresent()){
                    DiagnosisConclusion diagnosisConclusion = optionalDiagnosisConclusion.get();
                    System.out.println(diagnosisConclusion.getVetUser());
                    diagnosisConclusion.setDiagnosis(appointmentDto.getDiagnosisConclusionDto().getDiagnosis());
                    diagnosisConclusion.setRecommendations(appointmentDto.getDiagnosisConclusionDto().getRecommendations());
                    diagnosisConclusion.setVetUser(updatedAppointment.getVetSchedule().getVetUser());
                    updatedAppointment.setDiagnosisConclusion(diagnosisConclusion);
                    System.out.println(diagnosisConclusion);
                    diagnosisConclusionRepository.save(diagnosisConclusion);
                }
            }


            notificationRepository.save(notification);
            appointmentRepository.save(updatedAppointment);
            return AppointmentDto.entityToDto(updatedAppointment);
        }
        return null;
    }

    public List<AppointmentDto> getAllAppointmentsForPet(@NotNull Long petId){
        List<Appointment> appointments = appointmentRepository.findByPetId(petId);
        return appointments.stream().map(AppointmentDto::entityToDto).collect(toList());
    }

      public List<AppointmentDto> getAllAppointmentsForVet(@NotNull Long vetId){
        List<Appointment> appointments = appointmentRepository.findByVetScheduleVetUserId(vetId);
        return appointments.stream().map(AppointmentDto::entityToDto).collect(toList());
    }



}
