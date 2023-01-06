package com.example.vettrust.model;

import com.example.vettrust.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean seen;
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne
    private Appointment appointment;

}