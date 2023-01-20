package com.example.vettrust.repository;

import com.example.vettrust.enums.AppointmentValueType;
import com.example.vettrust.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPetId(Long petId);
    List<Appointment> findByVetScheduleVetUserId(Long vetId);
    List<Appointment> findByVetScheduleVetUserIdAndAppointmentTypeAppointmentValueType(Long vetId, AppointmentValueType appointmentType);
    List<Appointment> findByVetScheduleVetUserIdAndDate(Long vetId, String appointmentDate);
}
