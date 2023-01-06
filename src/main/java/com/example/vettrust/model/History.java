package com.example.vettrust.model;

import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "history")
    private Pet pet;

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

}
