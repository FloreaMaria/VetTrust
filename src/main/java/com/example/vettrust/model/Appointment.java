package com.example.vettrust.model;

import com.example.vettrust.enums.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import javax.persistence.*;
@Entity
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String hour;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @ManyToOne
    private Pet pet;

    @ManyToOne
    private VetSchedule vetSchedule;

    @ManyToOne
    private AppointmentType appointmentType;

    @ManyToOne
    private DiagnosisConclusion diagnosisConclusion;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Notification> notifications;
}

