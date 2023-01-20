package com.example.vettrust.model;

import com.example.vettrust.dto.DiagnosisConclusionDto;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.vettrust.model.VetUserMock.vetUser;

public class DiagnosisConclusionDtoMock {
    public static @NotNull DiagnosisConclusionDto diagnosisConclusionDto(){
        DiagnosisConclusionDto diagnosisConclusionDto = new DiagnosisConclusionDto();
        diagnosisConclusionDto.setId(1L);
        diagnosisConclusionDto.setDiagnosis("diagnosis");
        diagnosisConclusionDto.setRecommendations("recommendation");
        diagnosisConclusionDto.setVetUserName("vet");

        return diagnosisConclusionDto;

    }
}
