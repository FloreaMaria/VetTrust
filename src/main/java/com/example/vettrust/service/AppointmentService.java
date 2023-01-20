package com.example.vettrust.service;

import com.example.vettrust.dto.AppointmentDto;
import com.example.vettrust.dto.VetScheduleDto;
import com.example.vettrust.enums.AppointmentStatus;
import com.example.vettrust.enums.AppointmentValueType;
import com.example.vettrust.enums.NotificationType;
import com.example.vettrust.exception.NoAppointmentFoundException;
import com.example.vettrust.exception.NoAppointmentTypeFoundException;
import com.example.vettrust.exception.NoDiagnosisConclusionFoundException;
import com.example.vettrust.exception.NoPetFoundException;
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
    private final AppointmentTypeRepository appointmentTypeRepository;
    private final VetScheduleService vetScheduleService;
    private final NotificationRepository notificationRepository;
    private final DiagnosisConclusionRepository diagnosisConclusionRepository;
    private final VetUserService vetUserService;


    public AppointmentService(AppointmentRepository appointmentRepository, PetRepository petRepository, AppointmentTypeRepository appointmentTypeRepository, VetScheduleService vetScheduleService, NotificationRepository notificationRepository, DiagnosisConclusionRepository diagnosisConclusionRepository, VetUserService vetUserService) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.appointmentTypeRepository = appointmentTypeRepository;
        this.vetScheduleService = vetScheduleService;
        this.notificationRepository = notificationRepository;
        this.diagnosisConclusionRepository = diagnosisConclusionRepository;
        this.vetUserService = vetUserService;
    }


    @Transactional
    public Appointment findById(Long id){
        return appointmentRepository.findById(id).orElseThrow(() -> new NoAppointmentFoundException("Appointment with given id not found"));
    }

    public AppointmentDto saveAppointment(@NotNull AppointmentDto appointmentDto) throws ParseException {
        Appointment appointment =  new Appointment();
        Optional<Pet> pet = Optional.ofNullable(petRepository.findById(appointmentDto.getPetId()).orElseThrow(() -> new NoPetFoundException("Pet with given id not found")));
        if(pet.isPresent()){
            List<VetSchedule> vetSchedules = vetScheduleService.findAllByVetUserIdAndClinicLocationId(appointmentDto.getVetId(),
                    appointmentDto.getClinicLocationId());
            if(vetSchedules !=null){
                String chosenHour = appointmentDto.getHour();
                LocalDate chosenDate = LocalDate.parse(appointmentDto.getDate());
                for (VetSchedule vetSchedule: vetSchedules) {
                    String workingDay = vetSchedule.getWorkingDay().toUpperCase(Locale.ROOT);
                    if(chosenDate.getDayOfWeek().toString().toUpperCase(Locale.ROOT).equals(workingDay)){
                        String availableWorkingHours = vetSchedule.getAvailableWorkingHours();
                        Set<String> availableWorkingHoursSet = new HashSet<>(Arrays.asList(availableWorkingHours.split(",")));
                        for (String availableHour: availableWorkingHoursSet) {
                            if(chosenHour.equals(availableHour.trim())){
                                Optional<AppointmentType> appointmentType = Optional.ofNullable(appointmentTypeRepository.findByAppointmentValueType((appointmentDto.getAppointmentValueType())).orElseThrow(() -> new NoAppointmentTypeFoundException("Appointment type with appointment type value not found")));
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
                                    notification.setSeen(false);
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
        Appointment updatedAppointment = findById(appointmentId);

            Notification notification = new Notification();

            if(appointmentDto.getAppointmentStatus() != null){
                updatedAppointment.setAppointmentStatus(appointmentDto.getAppointmentStatus());
                if(appointmentDto.getAppointmentStatus().equals(AppointmentStatus.CANCELED)){
                    notification.setNotificationType(NotificationType.CANCELED_APPOINTMENT);
                    notification.setMessage("Your appointment on " + updatedAppointment.getDate() + " was successfully canceled");
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
            //No chosen date
            if(appointmentDto.getDate() != null){
                updatedAppointment.setDate(appointmentDto.getDate());
            }

            if(appointmentDto.getDiagnosisConclusionDto() != null){
                //no diagnosis found
                Optional<DiagnosisConclusion> optionalDiagnosisConclusion = Optional.ofNullable(diagnosisConclusionRepository.findById(updatedAppointment.getDiagnosisConclusion().getId()).orElseThrow(() -> new NoDiagnosisConclusionFoundException("No diagnosis cocnlusion with given id found")));
                if(optionalDiagnosisConclusion.isPresent()){
                    DiagnosisConclusion diagnosisConclusion = optionalDiagnosisConclusion.get();

                    diagnosisConclusion.setDiagnosis(appointmentDto.getDiagnosisConclusionDto().getDiagnosis());
                    diagnosisConclusion.setRecommendations(appointmentDto.getDiagnosisConclusionDto().getRecommendations());
                    diagnosisConclusion.setVetUser(updatedAppointment.getVetSchedule().getVetUser());
                    updatedAppointment.setDiagnosisConclusion(diagnosisConclusion);

                    diagnosisConclusionRepository.save(diagnosisConclusion);
                }
            }

            notificationRepository.save(notification);
            appointmentRepository.save(updatedAppointment);
            return AppointmentDto.entityToDto(updatedAppointment);

    }

    public List<AppointmentDto> getAllAppointmentsForPet(@NotNull Long petId){
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new NoPetFoundException("No pet with given id found"));
        List<Appointment> appointments = appointmentRepository.findByPetId(petId);
        return appointments.stream().map(AppointmentDto::entityToDto).collect(toList());
    }

      public List<AppointmentDto> getAllAppointmentsForVet(@NotNull Long vetId){
        VetUser vetUser = vetUserService.findById(vetId);
        List<Appointment> appointments = appointmentRepository.findByVetScheduleVetUserId(vetId);
        return appointments.stream().map(AppointmentDto::entityToDto).collect(toList());
    }

    @Transactional
    public List<AppointmentDto> getAllSpecificTypeAppointmentsForVet(@NotNull Long vetId,@NotNull AppointmentValueType appointmentType){
        VetUser vetUser = vetUserService.findById(vetId);
        List<Appointment> appointments = appointmentRepository.findByVetScheduleVetUserIdAndAppointmentTypeAppointmentValueType(vetId, appointmentType);
        return appointments.stream().map(AppointmentDto::entityToDto).collect(toList());

    }

    @Transactional
    public List<AppointmentDto> getAllSpecificDateAppointmentsForVet(@NotNull Long vetId,@NotNull String appointmentDate){
        VetUser vetUser = vetUserService.findById(vetId);
        List<Appointment> appointments = appointmentRepository.findByVetScheduleVetUserIdAndDate(vetId, appointmentDate);
        return appointments.stream().map(AppointmentDto::entityToDto).collect(toList());

    }

    public boolean deleteAppointment(@NotNull Long id){
        Appointment appointment = findById(id);
        if(appointment != null){
            appointmentRepository.delete(appointment);
            return true;
        }

        return false;
    }





}
