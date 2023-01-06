package com.example.vettrust.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ClinicLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean availability;
    private String city;
    private String address;
    @OneToMany(mappedBy = "clinicLocation", cascade = CascadeType.ALL)
    private List<VetUser> vetUserList;

    @OneToMany(mappedBy = "clinicLocation", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

}
