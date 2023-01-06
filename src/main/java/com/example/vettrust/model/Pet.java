package com.example.vettrust.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long age;
    private String name;
    private String type;

    @ManyToOne
    private PetOwner petOwner;

    @OneToOne(cascade = CascadeType.ALL)
    private History history;
}
