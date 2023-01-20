package com.example.vettrust.model;

import com.example.vettrust.enums.AppointmentValueType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class AppointmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AppointmentValueType appointmentValueType;
    private Double price;

    @OneToMany(mappedBy = "appointmentType", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

}
