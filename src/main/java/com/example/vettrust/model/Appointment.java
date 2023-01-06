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
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @ManyToOne
    private History history;

    @ManyToOne
    private VetUser vetUser;

    @ManyToOne
    private ClinicLocation clinicLocation;

    @OneToOne(cascade = CascadeType.ALL)
    private AppointmentType appointmentType;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<Notification> notifications;
}
