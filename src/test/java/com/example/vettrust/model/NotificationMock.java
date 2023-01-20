package com.example.vettrust.model;

import com.example.vettrust.enums.NotificationType;
import org.jetbrains.annotations.NotNull;

import static com.example.vettrust.model.AppointmentMock.appointment;

public class NotificationMock {
    public static @NotNull Notification notification(){
        Notification notification = new Notification();
        notification.setSeen(true);
        notification.setNotificationType(NotificationType.CANCELED_APPOINTMENT);
        notification.setMessage("Canceled");
        notification.setAppointment(appointment());
        notification.setId(1L);
        return notification;
    }
}
