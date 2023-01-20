package com.example.vettrust.repository;

import com.example.vettrust.enums.AppointmentValueType;
import com.example.vettrust.model.AppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Long> {
    Optional<AppointmentType> findByAppointmentValueType(AppointmentValueType appointmentValueType);
}
