package com.example.vettrust.dto;

import com.example.vettrust.model.Notification;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class NotificationDto {

    private Boolean seen;
    private String message;
    private String notificationType;
    private Long appointmentId;

    public static @NotNull NotificationDto entityToDto(@NotNull Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setSeen(notification.getSeen());
        dto.setNotificationType(notification.getNotificationType().toString());
        dto.setAppointmentId(notification.getAppointment().getId());
        return dto;
    }
}
