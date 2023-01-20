package com.example.vettrust.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class DiagnosisConclusion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;
    private String recommendations;

    @ManyToOne
    private VetUser vetUser;

    @OneToMany(mappedBy = "diagnosisConclusion", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
