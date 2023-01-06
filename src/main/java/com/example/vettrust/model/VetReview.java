package com.example.vettrust.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class VetReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int stars;
    private String comment;

    @ManyToOne
    private PetOwner petOwner;

    @ManyToOne
    private VetUser vetUser;


}
