package com.example.vettrust.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Setter
@Getter
@Entity
public class VetSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workingDay;

    private String workingHours;

    private String availableWorkingHours;

    @ManyToOne
    private VetUser vetUser;

    @ManyToOne
    private ClinicLocation clinicLocation;

    @OneToMany(mappedBy = "vetSchedule", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

}
