package com.example.vettrust.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("pet_owner")
@Getter
@Setter
public class PetOwner extends BaseUser{

    @OneToMany(mappedBy = "petOwner", cascade = CascadeType.ALL)
    private List<Pet> pets;

    @OneToMany(mappedBy = "petOwner", cascade = CascadeType.ALL)
    private List<VetReview> vetReviews;
}
