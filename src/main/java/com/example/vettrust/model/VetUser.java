package com.example.vettrust.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import javax.persistence.*;

@Entity
@DiscriminatorValue("vet_user")
@Getter
@Setter
public class VetUser extends BaseUser{
    private Double rating;

    @ManyToOne
    private ClinicLocation clinicLocation;

    @OneToMany(mappedBy = "vetUser", cascade = CascadeType.ALL)
    private List<VetReview> vetReviews;

    @OneToMany(mappedBy = "vetUser", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
