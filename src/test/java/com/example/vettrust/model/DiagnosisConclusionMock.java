package com.example.vettrust.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.vettrust.model.VetUserMock.vetUser;

public class DiagnosisConclusionMock {
    public static @NotNull DiagnosisConclusion diagnosisConclusion(){
        DiagnosisConclusion diagnosisConclusion = new DiagnosisConclusion();
        diagnosisConclusion.setId(1L);
        diagnosisConclusion.setDiagnosis("diagnosis");
        diagnosisConclusion.setRecommendations("recommendation");
        diagnosisConclusion.setVetUser(vetUser());
        diagnosisConclusion.setAppointments(new ArrayList<>());
        return diagnosisConclusion;
    }
}
