package com.example.vettrust.dto;

import com.example.vettrust.dto.user.VetUserDto;
import com.example.vettrust.model.DiagnosisConclusion;
import com.example.vettrust.model.Pet;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class DiagnosisConclusionDto {
    private String diagnosis;
    private String recommendations;
    private String vetUserName;
    private Long id;

    public static @NotNull DiagnosisConclusionDto entityToDto(@NotNull DiagnosisConclusion diagnosisConclusion) {
        DiagnosisConclusionDto dto = new DiagnosisConclusionDto();
        dto.setId(diagnosisConclusion.getId());
        dto.setDiagnosis(diagnosisConclusion.getDiagnosis());
        dto.setRecommendations(diagnosisConclusion.getRecommendations());
        dto.setVetUserName(diagnosisConclusion.getVetUser().getFirstName() + " " + diagnosisConclusion.getVetUser().getLastName());
        return dto;
    }
}
