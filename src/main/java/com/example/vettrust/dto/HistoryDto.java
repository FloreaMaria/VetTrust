package com.example.vettrust.dto;

import com.example.vettrust.model.History;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
public class HistoryDto {
    private Long petId;
    private List<AppointmentDto> appointmentList;
    public static @NotNull HistoryDto entityToDto(@NotNull History history) {
        HistoryDto dto = new HistoryDto();
        dto.setPetId(history.getPet().getId());
        dto.setAppointmentList(history.getAppointments().stream().map(AppointmentDto::entityToDto).collect(toList()));
        return dto;
    }
}
